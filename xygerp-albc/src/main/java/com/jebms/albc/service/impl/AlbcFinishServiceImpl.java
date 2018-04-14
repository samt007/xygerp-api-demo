package com.jebms.albc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jebms.albc.dao.AlbcFinishDao;
import com.jebms.albc.dao.AldUploadTempDao;
import com.jebms.albc.service.AlbcFinishService;
import com.jebms.ald.entity.AldUploadTemp;
import com.jebms.comm.core.BaseDao;
import com.jebms.comm.core.Transaction;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.security.model.AuthUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ALBC 完工入库处理的Service封装。
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings("rawtypes")
public class AlbcFinishServiceImpl implements AlbcFinishService {
    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AlbcFinishServiceImpl.class);
    
    @Autowired
    private AlbcFinishDao finishDao;
    
    @Autowired
    private AldUploadTempDao uploadTempDao;
    
	private BaseDao devDao;

	@Autowired
	AlbcFinishServiceImpl(DataSource dataSource) {
	    this.devDao = new BaseDao(dataSource);
	}
    
    //时间验证
	public ResultEntity validateFinishDate(Date finishDate,AuthUser user) throws Exception {
		Integer ret=finishDao.validateFinishDate(finishDate);
		if(ret==0){
			return ResultInfo.success();
		}else{
			return ResultInfo.error("不能产生将来的事务处理日期！");
		}
    }
	
    //库别验证
	public ResultEntity selectBySubinventoryCode(int organizationId,String subinventoryCode) throws Exception {
		Map<String,Object> ret=finishDao.selectBySubinventoryCode(organizationId,subinventoryCode);
		if(ret==null){
			return ResultInfo.error(String.format("库别 %s 不存在！",subinventoryCode));
		}else{
			return ResultInfo.success(ret);
		}
    }
	
    //库别列表
	public ResultEntity selectSubListByOrganizationId(int organizationId) throws Exception {
		List<Map<String,Object>> ret=finishDao.selectSubListByOrganizationId(organizationId);
		if(ret==null || ret.size()==0){
			return ResultInfo.error(String.format("组织 %s 没有CP库别信息！",organizationId));
		}else{
			return ResultInfo.success(ret);
		}
    }
	
    //货位验证
	public ResultEntity selectByLocatorCode(int organizationId,String subinventoryCode,String locatorCode,AuthUser user) throws Exception {
		Map<String,Object> ret=finishDao.selectByLocatorCode(organizationId,subinventoryCode,locatorCode);
		if(ret==null){
			return ResultInfo.error(String.format("货位 %s 不存在于库别 %s！",locatorCode,subinventoryCode));
		}else{
			return ResultInfo.success(ret);
		}
    }
	
    //批号(箱号)验证
	public ResultEntity selectByLotNumber(int organizationId,String lotNumber,AuthUser user) throws Exception {
		List<Map<String,Object>> ret=finishDao.selectByLotNumber(organizationId,lotNumber);
		if(ret==null || ret.size()==0){
			return ResultInfo.error(String.format("箱号（批号） %s 不存在 或者 已经入库 或者 对应的任务单状态非3/4！",lotNumber));
		}else{
			return ResultInfo.success(ret);
		}
    }
	
	//保存并上传接口
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public ResultEntity importFinish(JSONObject uploadData,AuthUser user) throws Exception {
		devDao.getDevJdbcTemplate().execute("BEGIN APPS.XYG_FND_COMMON_PKG.SET_CONTEXT('XYG_ALI_FINISH_IMPORT','XYG_ALBC'); END;",null);
		Long batchId=devDao.getJdbcTemplate().queryForObject("select APPS.XYG_ALD_UPLOAD_TEMP_BAT_S.NEXTVAL from dual", Long.class);
		Integer orgId=uploadData.getInteger("orgId");
		Integer organizationId=uploadData.getInteger("organizationId");
		String finishDate=uploadData.getString("finishDate");
		Long finishPid=uploadData.getLong("finishPid");
		Integer transactionTypeId=uploadData.getInteger("transactionTypeId");
		//String moveCompletionFlag=uploadData.getString("moveCompletionFlag");
		//String finalCompletionFlag=uploadData.getString("finalCompletionFlag");
		String description=uploadData.getString("description");
		JSONArray data=uploadData.getJSONArray("data");
		//在java中解析JSON并插入临时表
		for(int index=0;index<data.size();index++){
			JSONObject temp=data.getJSONObject(index);
			AldUploadTemp upload=new AldUploadTemp();
			upload.setBatchId(batchId);
			upload.setDocType("TEXT");
			upload.setDocCode("FINISH_IMPORT");
			upload.setDocRow(index+1);
			upload.setCol1(temp.getString("importLineNum"));
			upload.setCol2(temp.getString("lotNumber"));
			upload.setCol3(temp.getString("subinventoryCode"));
			upload.setCol4(temp.getString("locatorCode"));
			upload.setCol5(temp.getString("description"));
			upload.setCol55("XYG_ALBC");
			upload.setProcessFlag(0);
			upload.setProcessMessage("");
			upload.setWhoInsert(user);
			uploadTempDao.insertSelective(upload);
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("batchId", batchId);
		params.put("orgId", orgId);
		params.put("organizationId", organizationId);
		params.put("finishDate", finishDate);
		params.put("finishPid", finishPid);
		params.put("userId", user.getUserId());
		params.put("transactionTypeId", transactionTypeId);
		params.put("moveCompletionFlag", "N");
		params.put("finalCompletionFlag", "Y");
		params.put("description", description);
		finishDao.importFinish(params);
		ResultEntity ret=ResultInfo.fromParams(params);
		LOGGER.info(String.format("params return is: %s, ret return is: %s", JSON.toJSONString(params), JSON.toJSONString(ret)));
		devDao.getDevJdbcTemplate().execute("BEGIN APPS.XYG_FND_COMMON_PKG.SET_CONTEXT('XYG_ALI_FINISH_IMPORT',''); END;",null);
		ret.setData(uploadTempDao.selectFinishUploadByBatchId(batchId));
		if(!ret.isOk()){
			Transaction.setRollbackOnly();
		}
		return ret;
    }
}

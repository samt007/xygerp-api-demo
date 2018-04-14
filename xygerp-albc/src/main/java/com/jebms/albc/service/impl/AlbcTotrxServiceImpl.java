package com.jebms.albc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jebms.albc.dao.AlbcTotrxDao;
import com.jebms.albc.dao.AldUploadTempDao;
import com.jebms.albc.service.AlbcTotrxService;
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
 * ALBC 处理物料搬运单Service封装。
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class AlbcTotrxServiceImpl implements AlbcTotrxService {
    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AlbcTotrxServiceImpl.class);
    
    @Autowired
    private AlbcTotrxDao totrxDao;
    
    @Autowired
    private AldUploadTempDao uploadTempDao;
    
	private BaseDao devDao;

	@Autowired
	AlbcTotrxServiceImpl(DataSource dataSource) {
	    this.devDao = new BaseDao(dataSource);
	}
    
    //批号(箱号)验证
	public ResultEntity selectByDeliveryLotNumber(Long deliveryId,String lotNumber,AuthUser user) throws Exception {
		List<Map<String,Object>> ret=totrxDao.selectListByDeliveryLotNumber(deliveryId,lotNumber);
		if(ret==null || ret.size()==0){
			return ResultInfo.error(String.format("批号 %s 不存在于发货单ID %s！",lotNumber,deliveryId));
		}else{
			return ResultInfo.success(ret);
		}
    }
    
    //发货单保留信息查询
	public ResultEntity selectResListByDeliveryId(Long deliveryId,AuthUser user) throws Exception {
		List<Map<String,Object>> ret=totrxDao.selectResListByDeliveryId(deliveryId);
		return ResultInfo.success(ret);
    }
    
    //发货单号验证接口
	public ResultEntity selectByDeliveryName(String deliveryName,Integer organizationId,AuthUser user) throws Exception {
		Map<String,Object> ret=totrxDao.selectByDeliveryName(deliveryName,organizationId);
		if(ret==null){
			return ResultInfo.error(String.format("发货单号 %s （对应组织ID %s）不存在！",deliveryName,organizationId));
		}else{
			return ResultInfo.success(ret);
		}
    }
	
	//保存并上传接口
	@Transactional(readOnly = false)
	public ResultEntity importTotrx(JSONObject uploadData,AuthUser user) throws Exception {
		devDao.getDevJdbcTemplate().execute("BEGIN APPS.XYG_FND_COMMON_PKG.SET_CONTEXT('XYG_ALI_TOTRX_IMPORT_TOTRX','XYG_ALBC'); END;",null);
		Long batchId=devDao.getJdbcTemplate().queryForObject("select APPS.XYG_ALD_UPLOAD_TEMP_BAT_S.NEXTVAL from dual", Long.class);
		Integer organizationId=uploadData.getInteger("organizationId");
		String transactionDate=uploadData.getString("transactionDate");
		String defaultQtyFlag=uploadData.getString("defaultQtyFlag");
		JSONArray data=uploadData.getJSONArray("data");
		//在java中解析JSON并插入临时表
		for(int index=0;index<data.size();index++){
			JSONObject temp=data.getJSONObject(index);
			AldUploadTemp upload=new AldUploadTemp();
			upload.setBatchId(batchId);
			upload.setDocType("TEXT");
			upload.setDocCode("IMPORT_TOTRX");
			upload.setDocRow(index+1);
			upload.setCol1(temp.getString("importLineNum"));
			upload.setCol2(temp.getString("deliveryName"));
			upload.setCol3(temp.getString("requestNumber"));
			upload.setCol4(temp.getString("requestLineNumber"));
			upload.setCol5(temp.getString("lotNumber"));
			upload.setCol55("XYG_ALBC");
			upload.setProcessFlag(0);
			upload.setProcessMessage("");
			upload.setWhoInsert(user);
			uploadTempDao.insertSelective(upload);
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("batchId", batchId);
		params.put("organizationId", organizationId);
		params.put("transactionDate", transactionDate);
		params.put("defaultQtyFlag", defaultQtyFlag);
		params.put("userId", user.getUserId());
		totrxDao.importTotrx(params);
		ResultEntity ret=ResultInfo.fromParams(params);
		LOGGER.info(String.format("params return is: %s, ret return is: %s", JSON.toJSONString(params), JSON.toJSONString(ret)));
		devDao.getDevJdbcTemplate().execute("BEGIN APPS.XYG_FND_COMMON_PKG.SET_CONTEXT('XYG_ALI_TOTRX_IMPORT_TOTRX',''); END;",null);
		ret.setData(uploadTempDao.selectTotrxUploadByBatchId(batchId));
		if(!ret.isOk()){
			Transaction.setRollbackOnly();
		}
		return ret;
    }
	
}

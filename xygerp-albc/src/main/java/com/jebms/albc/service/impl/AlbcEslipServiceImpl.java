package com.jebms.albc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jebms.albc.dao.AlbcEslipDao;
import com.jebms.albc.dao.PubDataUploadTmpDao;
import com.jebms.ald.entity.PubDataUploadTmp;
import com.jebms.comm.core.BaseDao;
import com.jebms.comm.core.Transaction;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.entity.SearchInfo;
import com.jebms.comm.security.model.AuthUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jebms.albc.service.AlbcEslipService;

/**
 * ALBC 电子单据处理的Service封装。
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings("rawtypes")
public class AlbcEslipServiceImpl implements AlbcEslipService  {
    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AlbcEslipServiceImpl.class);
    
    @Autowired
    private AlbcEslipDao eslipDao;
    
    @Autowired
    private PubDataUploadTmpDao uploadTempDao;
    
	private BaseDao devDao;

	@Autowired
	AlbcEslipServiceImpl(DataSource dataSource) {
	    this.devDao = new BaseDao(dataSource);
	}
    
    //单号验证
	public ResultEntity selectByEslipNumber(int organizationId,String eslipNumber,AuthUser user) throws Exception {
		Map<String,Object> ret=eslipDao.selectByEslipNumber(organizationId,eslipNumber);
		if(ret==null){
			return ResultInfo.error(String.format("单号 %s 不存在！",eslipNumber));
		}else{
			return ResultInfo.success(ret);
		}
    }
	
    //根据库别获取货位分页
	public ResultEntity selectForPageLocator(SearchInfo searchInfo) throws Exception {
        PageHelper.startPage(searchInfo.getPageNum(), searchInfo.getPageSize() ,searchInfo.isCount());
        List<Map<String,Object>> pageList = eslipDao.selectForPageLocator(searchInfo);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(pageList);
        return ResultInfo.success(pageInfo);
    }
	
    //批号验证
	public ResultEntity selectByLotNumber(int organizationId,String subinventoryCode,String lotNumber,AuthUser user) throws Exception {
		List<Map<String,Object>> ret=eslipDao.selectByLotNumber(organizationId,subinventoryCode,lotNumber);
		if(ret==null || ret.size()==0){
			return ResultInfo.error(String.format("批号 %s 不存在于库别 %s！",lotNumber,subinventoryCode));
		}else{
			return ResultInfo.success(ret);
		}
    }

	//保存并上传接口
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public ResultEntity importEslip(JSONObject uploadData,AuthUser user) throws Exception {
		devDao.getDevJdbcTemplate().execute("BEGIN APPS.XYG_FND_COMMON_PKG.SET_CONTEXT('XYG_ALI_ESLIP_LINE_IMPORT','XYG_ALBC'); END;",null);
		Long batchId=devDao.getJdbcTemplate().queryForObject("select APPS.XYG_ALD_UPLOAD_TEMP_BAT_S.NEXTVAL from dual", Long.class);
		Long headerId=uploadData.getLong("headerId");
		JSONArray data=uploadData.getJSONArray("data");
		//在java中解析JSON并插入临时表
		for(int index=0;index<data.size();index++){
			JSONObject temp=data.getJSONObject(index);
			PubDataUploadTmp upload=new PubDataUploadTmp();
			upload.setBatchId(batchId);
			upload.setAttributeCategory("ESLIP_LINE_IMPORT");
			upload.setRowNum(index+1);
			upload.setAttribute1(temp.getString("importLineNum"));
			upload.setAttribute2(temp.getString("eslipNumber"));
			upload.setAttribute3(temp.getString("eslipLineNum"));
			upload.setAttribute4(temp.getString("locatorCode"));
			upload.setAttribute5(temp.getString("transferLocatorCode"));
			upload.setAttribute6(temp.getString("itemNumber"));
			upload.setAttribute7(temp.getString("lotNumber"));
			upload.setAttribute8(temp.getString("transactionUom"));
			upload.setAttribute9(temp.getString("transactionQuantity"));
			upload.setAttribute10(temp.getString("transactionCost"));
			upload.setAttribute11(temp.getString("lineReasonName"));
			upload.setAttribute12(temp.getString("lineDescription"));
			upload.setAttribute30("XYG_ALBC");
			upload.setProcessFlag(0);
			upload.setProcessMessage("");
			upload.setWhoInsert(user);
			uploadTempDao.insertSelective(upload);
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("batchId", batchId);
		params.put("headerId", headerId);
		params.put("userId", user.getUserId());
		eslipDao.importEslipLine(params);
		ResultEntity ret=ResultInfo.fromParams(params);
		LOGGER.info(String.format("params return is: %s, ret return is: %s", JSON.toJSONString(params), JSON.toJSONString(ret)));
		devDao.getDevJdbcTemplate().execute("BEGIN APPS.XYG_FND_COMMON_PKG.SET_CONTEXT('XYG_ALI_ESLIP_LINE_IMPORT',''); END;",null);
		ret.setData(uploadTempDao.selectEslipUploadByBatchId(batchId));
		if(!ret.isOk()){
			Transaction.setRollbackOnly();
			return ret;
		}else{
			// 先将状态改为3.5。NTD:这个必须要确认！！因为要自动过账必须得这样子处理。-->2018.2.11已经和匡，蔡，谢确认！
			Map<String,Object> updateParams=new HashMap<String,Object>();
			updateParams.put("headerId", headerId);
			eslipDao.eslipSubinvConfirm(updateParams);
			ResultEntity retSubCon=ResultInfo.fromParams(updateParams);
			if(!retSubCon.isOk()){
				Transaction.setRollbackOnly();
				return retSubCon;
			}
			//devDao.getDevJdbcTemplate().update("UPDATE INV_ESLIP_HEADERS SET STATUS_FLAG='3.5' WHERE HEADER_ID=:headerId", updateParams);
			//调用自动过账程序
			Map<String,Object> postParams=new HashMap<String,Object>();
			postParams.put("headerId", headerId);
			postParams.put("online", "Y");
			postParams.put("result", "");
			eslipDao.postEslip(postParams);
			if(postParams.get("result").equals("Y")){
				return ResultInfo.success(String.format("单据过账成功！本次处理结果： %s", ret.getMessage())); 
			}else{
				Transaction.setRollbackOnly();
				return ResultInfo.error(String.format("单据过账失败，信息： %s", postParams.get("message")));
			}
		}
    }
}

package com.jebms.albc.service;

import com.alibaba.fastjson.JSONObject;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.SearchInfo;
import com.jebms.comm.security.model.AuthUser;

/**
 * ALBC 电子单据处理的Service封装。
 *
 * @author samt007@qq.com
 */

@SuppressWarnings("rawtypes")
public interface AlbcEslipService {
    
    //单号验证
	public ResultEntity selectByEslipNumber(int organizationId,String eslipNumber,AuthUser user) throws Exception ;
	
    //根据库别获取货位分页
	public ResultEntity selectForPageLocator(SearchInfo searchInfo) throws Exception ;
	
    //批号验证
	public ResultEntity selectByLotNumber(int organizationId,String subinventoryCode,String lotNumber,AuthUser user) throws Exception ;

	//保存并上传接口
	public ResultEntity importEslip(JSONObject uploadData,AuthUser user) throws Exception ;
}

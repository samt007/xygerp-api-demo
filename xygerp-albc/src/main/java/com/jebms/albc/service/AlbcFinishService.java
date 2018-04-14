package com.jebms.albc.service;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.security.model.AuthUser;

/**
 * ALBC 完工入库处理的Service封装。
 *
 * @author samt007@qq.com
 */

@SuppressWarnings("rawtypes")
public interface AlbcFinishService {
    
    //时间验证
	public ResultEntity validateFinishDate(Date finishDate,AuthUser user) throws Exception ;
	
    //库别验证
	public ResultEntity selectBySubinventoryCode(int organizationId,String subinventoryCode) throws Exception ;
	
    //库别列表
	public ResultEntity selectSubListByOrganizationId(int organizationId) throws Exception ;
	
    //货位验证
	public ResultEntity selectByLocatorCode(int organizationId,String subinventoryCode,String locatorCode,AuthUser user) throws Exception ;
	
    //批号(箱号)验证
	public ResultEntity selectByLotNumber(int organizationId,String lotNumber,AuthUser user) throws Exception ;
	
	//保存并上传接口
	public ResultEntity importFinish(JSONObject uploadData,AuthUser user) throws Exception ;
}

package com.jebms.albc.service;

import com.alibaba.fastjson.JSONObject;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.security.model.AuthUser;


/**
 * ALBC 处理物料搬运单Service封装。
 *
 * @author samt007@qq.com
 */

@SuppressWarnings({"rawtypes"})
public interface AlbcTotrxService {
    
    //批号(箱号)验证
	public ResultEntity selectByDeliveryLotNumber(Long deliveryId,String lotNumber,AuthUser user) throws Exception ;
    
    //发货单保留信息查询
	public ResultEntity selectResListByDeliveryId(Long deliveryId,AuthUser user) throws Exception ;
    
    //发货单号验证接口
	public ResultEntity selectByDeliveryName(String deliveryName,Integer organizationId,AuthUser user) throws Exception ;
	
	//保存并上传接口
	public ResultEntity importTotrx(JSONObject uploadData,AuthUser user) throws Exception ;
	
}

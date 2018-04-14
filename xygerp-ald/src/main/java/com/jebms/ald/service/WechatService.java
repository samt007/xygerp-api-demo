package com.jebms.ald.service;

import com.jebms.comm.entity.ResultEntity;


/**
 * Wechat微信处理的Service封装。
 *
 * @author samt007@qq.com
 */

@SuppressWarnings("rawtypes")
public interface WechatService {
	
	public ResultEntity getWechatSession(String wxLoginCode) throws Exception ;
    
    //解密微信绑定电话接口
	public ResultEntity getWechatPhoneNumber(String sessionKey,String iv,String encryptedData) ;
    
}

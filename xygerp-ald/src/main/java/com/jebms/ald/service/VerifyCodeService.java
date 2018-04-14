package com.jebms.ald.service;

import java.util.Map;

import com.jebms.comm.entity.ResultEntity;

/**
 * VerifyCode验证码处理的Service封装。
 *
 * @author samt007@qq.com
 */

@SuppressWarnings("rawtypes")
public interface VerifyCodeService {

    public Map<String,Object> getVerifyCodeDefine(String userName,String getType,String verifyType) ;
    
    // 新增短信认证记录到EBS
    public ResultEntity insertVerifyCode(String userName,Long userId,Long personId,String verifyCodeFlag,String workTelephone,String terminalIpAddress) throws Exception ;
    
    //认证是否允许登录
	public ResultEntity checkVerifyCode(Long verifyCodeId,String userName,String workTelephone) throws Exception ;
    
    //验证绑定电话接口
	public ResultEntity getVerifyCode(String userName,String verifyType,String terminalIpAddress) throws Exception ;
	
    //验证绑定电话接口
	public ResultEntity verifyPhoneNumber(Long verifyCodeId,String phoneNumber) throws Exception ;

}

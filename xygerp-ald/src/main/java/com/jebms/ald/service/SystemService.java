package com.jebms.ald.service;

import com.jebms.comm.entity.ResultEntity;
import com.jebms.ald.entity.FndUserVO;

/**
 * XYG ERP 系统管理，安全相关实体的管理类,包括用户、职责、菜单、功能，以及用户登录记录
 *
 * @author samt007@qq.com
 */

@SuppressWarnings("rawtypes")
public interface SystemService {
	
    /**
     * 根据登录名获取用户
     *
     * @param username 用户名
     * @return FndUser user by user name
     */
    public FndUserVO selectByUsername(String username,String lang) ;
    
    // 验证XYG ERP系统的用户名和密码是否正确。
    public boolean xygErpValidateLogin(String userName,String password) ;
    
    //新增登录信息。这里是调用erp的api产生登录信息
    public Long toLogin(Long userId) ;
    
    //退出登录。这里是调用erp的api产生登录信息
	public ResultEntity toLogout(Long loginId) ;
	
}

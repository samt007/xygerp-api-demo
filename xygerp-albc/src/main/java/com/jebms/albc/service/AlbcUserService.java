package com.jebms.albc.service;

import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.security.model.AuthUser;

/**
 * ALBC USER的Service封装。
 *
 * @author samt007@qq.com
 */

@SuppressWarnings("rawtypes")
public interface AlbcUserService {
    
    //用户切换组织：用户组织列表
	public ResultEntity selectForUserOrganization(Long userId,AuthUser user) throws Exception ;
}

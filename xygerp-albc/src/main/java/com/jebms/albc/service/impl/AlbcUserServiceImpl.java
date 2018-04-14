package com.jebms.albc.service.impl;

import com.jebms.albc.dao.AlbcUserDao;
import com.jebms.albc.service.AlbcUserService;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.security.model.AuthUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ALBC USER的Service封装。
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings("rawtypes")
public class AlbcUserServiceImpl implements AlbcUserService {
    
    @Autowired
    private AlbcUserDao userDao;
    
    //用户切换组织：用户组织列表
	public ResultEntity selectForUserOrganization(Long userId,AuthUser user) throws Exception {
        return ResultInfo.success(userDao.selectForUserOrganization(userId));
    }
}

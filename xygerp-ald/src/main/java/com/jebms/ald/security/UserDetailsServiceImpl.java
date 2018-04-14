package com.jebms.ald.security;


import com.jebms.ald.security.AuthUserFactory;
import com.jebms.ald.entity.FndUserVO;
import com.jebms.ald.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security User Detail Service
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 系统服务
     */
    @Autowired
    private SystemService systemService;

    @Override
    public UserDetails loadUserByUsername(String username) {
    	FndUserVO user = systemService.selectByUsername(username,"ZHS");//默认以ZHS语言。这里主要是获取权限信息，而不是具体menu信息
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
        	//System.out.println("-->认证登录用户:"+user.getId()+"-"+user.getNicename());
        	return AuthUserFactory.create(user);
        }
    }
}

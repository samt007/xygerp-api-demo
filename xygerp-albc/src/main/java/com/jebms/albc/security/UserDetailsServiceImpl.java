package com.jebms.albc.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Security User Detail Service
 * 这里写这个类是为了让编译的时候不报错。但是又用到Spring Security框架！
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
    	return null;
    	//throw new Exception("per can not login username!");
    }
}

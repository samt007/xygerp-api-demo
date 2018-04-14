package com.jebms.ald.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jebms.ald.service.SystemService;
import com.jebms.comm.security.model.AuthUser;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private UserDetailsServiceImpl userService;
	
	@Autowired
	private SystemService sysService;
    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
	
    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        AuthUser user = (AuthUser) userService.loadUserByUsername(username);
        System.out.println("username:"+username+",password:"+password);
        if(user == null){
            throw new BadCredentialsException("Username not found.");
        }

        //加密过程在这里体现
        if (!sysService.xygErpValidateLogin(username, password)) {
            throw new BadCredentialsException("Wrong password.");
        }
        
        user.setPassword(passwordEncoder.encode(password));

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}

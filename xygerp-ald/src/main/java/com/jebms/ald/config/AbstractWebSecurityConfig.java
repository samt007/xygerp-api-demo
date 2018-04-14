package com.jebms.ald.config;

import com.jebms.ald.security.MyAuthenticationProvider;
import com.jebms.comm.security.AuthenticationTokenFilter;
import com.jebms.comm.security.MyAuthenticationEntryPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring-security配置
 *
 * @author samt007@qq.com
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private MyAuthenticationProvider provider;//自定义验证

    /**
     * Password encoder password encoder.
     * 2018.1.18 该系统是ERP的接口管理系统，其用户名密码的安全性在ERP中有管控，所以，这里可以设置为明文密码。
     * 不过为了密码的保密性，还是加密比较合理。
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);//NoOpPasswordEncoder.getInstance();// 
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .userDetailsService(this.userDetailsService)
            .passwordEncoder(this.passwordEncoder())
        ;
        //将验证过程交给自定义验证工具
        auth.authenticationProvider(provider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Authentication token filter bean authentication token filter.
     *
     * @return the authentication token filter
     */
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() {
        return new AuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/token").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/**").permitAll()
        		.antMatchers(HttpMethod.POST, "/ald/verifyCode/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        security
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint()).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .anyRequest().authenticated();

        // Custom JWT based security filter
        security
            .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}

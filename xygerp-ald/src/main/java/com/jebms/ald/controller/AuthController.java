package com.jebms.ald.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.security.AuthenticationTokenFilter;
import com.jebms.comm.security.model.AuthUser;
import com.jebms.comm.security.util.TokenUtil;
import com.jebms.comm.security.util.UserUtil;
import com.jebms.comm.utils.IPUtils;
import com.jebms.comm.utils.TypeConverter;
import com.jebms.ald.service.SystemService;
import com.jebms.ald.service.WechatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Authentication controller.
 *
 * @author samt007@qq.com
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@Api(value = "登录接口",description="登录接口，获取或者更新Token调用")
@SuppressWarnings("rawtypes")
public class AuthController {
	
    @Autowired
    private SystemService systemService;
    /**
     * 权限管理
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private WechatService wechatService;
    /**
     * Token工具
     */
    @Autowired
    private TokenUtil jwtTokenUtil;

    /**
     * Create authentication token bearer auth token.
     *
     * @param user the fnd user
     * @return the bearer auth token
     */
    //POST用Body传参的参数说明
	protected static class AuthParam{
		@ApiModelProperty(value = "用户名称", example = "GUEST",required = true,position = 1)
		public String userName;
		@ApiModelProperty(value = "密码", example = "ORACLE",required = true,position = 2)
		public String password;
		@ApiModelProperty(value = "语言(ZHS/US)", example = "ZHS",required = true,position = 3)
		public String language;
		@ApiModelProperty(value = "微信CODE", example = "" ,position = 4)
		public String wxLoginCode;
	}
	@PostMapping(value = "/token")
    @ApiOperation(value="获取认证token接口")
    public ResultEntity createAuthenticationToken(HttpServletRequest request,
    	@RequestBody @ApiParam(value = "登录参数",required = true) AuthParam auth) {
		String userName = auth.userName;
		String password = auth.password;
		String language = auth.language;
		String wxLoginCode = auth.wxLoginCode;
		System.out.println("userName:"+userName+",password:"+password+",language:"+language+",wxLoginCode:"+wxLoginCode);
		// Perform the security
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userName, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //产生登录信息
        AuthUser authUser=(AuthUser) userDetails;//认证的用户
        Long loginId = systemService.toLogin(authUser.getUserId());
        if(loginId>0){
    		authUser.setLoginId(loginId);
    		authUser.setLanguage(language==null?"ZHS":language);
    		authUser.setIpAddress(IPUtils.getIpAddr(request));
        }else{
        	throw new RuntimeException(String.format("产生登录信息失败！"));
        }
        if(TypeConverter.isNotEmpty(wxLoginCode)){// 产生并保存微信小程序的登录信息
        	ResultEntity wxResule;
        	try {
        		wxResule=wechatService.getWechatSession(wxLoginCode);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(String.format("产生微信登录信息失败！错误信息: %s",e.getMessage()));
			}
        	if(wxResule.isOk()){
        		JSONObject wxJson=(JSONObject) wxResule.getData();
        		authUser.setWxSessionKey(wxJson.getString("session_key"));
        		authUser.setWxOpenid(wxJson.getString("openid"));
        	}else{
        		throw new RuntimeException(String.format("调用微信登录处理失败！错误信息: %s",wxResule.getMessage()));
        	}
        }
        System.out.println(new Gson().toJson(userDetails));
        final String token = jwtTokenUtil.generateToken(request,userDetails);
        // Return the token
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", token);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);
        tokenMap.put("authUser", authUser);
        return ResultInfo.success(tokenMap);
    }
	
    /**
     * Refresh and get authentication token bearer auth token.
     *
     * @param request the request
     * @return the bearer auth token
     */
    @GetMapping(value = "/refresh")
    @ApiOperation(value="更新认证token接口")
    public ResultEntity refreshAndGetAuthenticationToken(HttpServletRequest request) {
        //注意：刷新不改变登录id信息！
        AuthUser user=UserUtil.getCurrentUser();
        //FndLogin login=systemService.selectLoginById(user.getLoginId());
        //处理更新认证token
        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        ((AuthUser)userDetails).setLoginId(user.getLoginId());
        ((AuthUser)userDetails).setLanguage(user.getLanguage());
        ((AuthUser)userDetails).setIpAddress(user.getIpAddress());
        ((AuthUser)userDetails).setPassword(user.getPassword());
        ((AuthUser)userDetails).setWxSessionKey(user.getWxSessionKey());
        ((AuthUser)userDetails).setWxOpenid(user.getWxOpenid());
        final String refreshedToken = jwtTokenUtil.generateToken(request,userDetails);
        System.out.println("refresh user:"+new Gson().toJson(userDetails));
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", refreshedToken);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);
        tokenMap.put("authUser", (AuthUser)userDetails);
        return ResultInfo.success(tokenMap);
    }

    /**
     * Delete authentication token response entity.
     *
     * @param request the request
     * @return the response entity
     */
	@DeleteMapping(value = "/token")
    @ApiOperation(value="删除认证token接口")
    public ResultEntity deleteAuthenticationToken(HttpServletRequest request) {
        // 更新登录信息
        AuthUser user=UserUtil.getCurrentUser();
        ResultEntity retLogout=systemService.toLogout(user.getLoginId());
        if(!retLogout.isOk()){
        	return ResultInfo.error("toLogout调用异常，信息："+retLogout.getMessage());
        }
        // 处理删除认证
        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];
        jwtTokenUtil.removeToken(request,token);
        return ResultInfo.success();//new ResponseEntity(HttpStatus.OK);
    }

}

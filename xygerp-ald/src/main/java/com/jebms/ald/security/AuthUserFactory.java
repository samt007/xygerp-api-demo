package com.jebms.ald.security;


import com.jebms.comm.security.model.AuthUser;
//import com.jebms.comm.utils.StringHelper;

//import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.jebms.ald.entity.FndUserVO;

//import java.util.List;
//import java.util.stream.Collectors;

/**
 * The type Auth user factory.
 *
 * @author samt007@qq.com
 */
public final class AuthUserFactory {

    private AuthUserFactory() {
    }

    /**
     * Create auth user.
     * 用户登录逻辑;所有的模块的用户统一由ald模块登录并产生token信息，别的模块都是直接用。
     * 注意：这里只是获取并转换认证的用户，接下来Spring Security框架再统一用认证用户判断用户名和密码是否正确。
     * @param user the user
     * @return the auth user
     */
    public static AuthUser create(FndUserVO user) {
        AuthUser authUser = new AuthUser(user.getUserId());
        authUser.setUserName(user.getUserName());
        authUser.setPassword(user.getPassword());
        authUser.setEmailAddress(user.getEmailAddress());
        authUser.setPasswordDate(user.getPasswordDate());
        authUser.setStartDate(user.getStartDate());
        authUser.setEndDate(user.getEndDate());
        authUser.setEmployeeId(user.getEmployeeId());
        authUser.setEmployeeNumber(user.getEmployeeNumber());
        authUser.setFullName(user.getFullName());
        //authUser.setLastActiveDate(new Date());
        //authUser.setAuthorities(mapToGrantedAuthorities(user.getRespVOs(), user.getMenuVOs()));
        return authUser;
    }

    /**
     * 权限转换
     *
     * @param fndResp 职责列表
     * @param sysMenus 菜单列表
     * @return 权限列表
     */
    /*
    private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(List<FndRespVO> fndResps, List<FndMenuVO> fndMenus) {

        List<SimpleGrantedAuthority> authorities =
        		fndResps.stream().filter(FndRespVO::isEnabled)
                .map(fndResp -> new SimpleGrantedAuthority(fndResp.getRespName())).collect(Collectors.toList());

        // 添加基于Permission的权限信息
        fndMenus.stream().filter(menu -> StringHelper.isNotBlank(menu.getPermission())).forEach(menu -> {
            // 添加基于Permission的权限信息
            for (String permission : StringHelper.split(menu.getPermission(), ",")) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        });

        return authorities;
    }
*/
}

package com.jebms.albc.controller;

import java.util.List;

import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;
import  com.jebms.albc.service.AlbcUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The albc user controller.
 *
 * @author samt07@qq.com
 */
@Validated
@RestController
@RequestMapping("/user")
@Api(value = "用户信息获取API", description = "用户信息获取API")
@SuppressWarnings("unchecked")
public class AlbcUserController extends BaseController{
	
    @Autowired
    private AlbcUserService userService;


	protected static class UserOrganizationRE{
		@ApiModelProperty(value = "业务实体ID")
		public int orgId;
		@ApiModelProperty(value = "组织ID")
		public int organizationId;
		@ApiModelProperty(value = "组织代码")
		public String organizationCode;
		@ApiModelProperty(value = "组织名称")
		public String organizationName;
		@ApiModelProperty(value = "用户描述")
		public String userDesc;
		@ApiModelProperty(value = "用户名称")
		public String userName;
		@ApiModelProperty(value = "用户ID")
		public Long userId;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/organization/{userId}")
    @ApiOperation(value = "切换组织接口(获取用户组织列表)")
    public ResultEntity<List<UserOrganizationRE>> getUserOrganization(
    		@ApiParam(name = "userId", value = "用户ID",required = true)@PathVariable("userId") Long userId) throws Exception {
        return userService.selectForUserOrganization(userId,this.authUser);
    }

}

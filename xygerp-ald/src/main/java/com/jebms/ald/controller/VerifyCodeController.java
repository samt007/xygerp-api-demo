package com.jebms.ald.controller;

import com.jebms.ald.service.VerifyCodeService;
import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The  Wechat controller.
 *
 * @author samt07@qq.com
 */
@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/verifyCode")
@Api(value = "验证码功能管理", description = "验证码功能管理")
@SuppressWarnings("rawtypes")
public class VerifyCodeController extends BaseController{
    /**
     * 系统数据字典
     */
    @Autowired
    private VerifyCodeService verifyCodeService;

	protected static class VefifyPNParam{
		@ApiModelProperty(value = "验证序号ID", dataType = "Long",example = "",required = true,position = 1)
		public Long verifyCodeId;
		@ApiModelProperty(value = "电话号码", dataType = "String", example = "",required = true,position = 2)
		public String phoneNumber;
	}
    //@PreAuthorize("hasAuthority('fnd:worklog:view')")
    @PostMapping(value = "/verifyPhoneNumber")
    @ApiOperation(value = "验证电话号码API")
    public ResultEntity verifyWechatPhoneNumber(@RequestBody VefifyPNParam param) throws Exception {
    	return verifyCodeService.verifyPhoneNumber(param.verifyCodeId,param.phoneNumber);
    }

	protected static class GetVefifyCodeParam{
		@ApiModelProperty(value = "用户名称", dataType = "String", example = "",required = true)
		public String userName;
		@ApiModelProperty(value = "验证方式", dataType = "String", example = "",required = true)
		public String verifyType;
		@ApiModelProperty(value = "IP地址", dataType = "String", example = "",required = true)
		public String terminalIpAddress;
	}
    //@PreAuthorize("hasAuthority('fnd:worklog:view')")
    @PostMapping(value = "/getVerifyCode")
    @ApiOperation(value = "获取验证码API")
    public ResultEntity getQRVerifyCode(@RequestBody GetVefifyCodeParam param) throws Exception {
    	return verifyCodeService.getVerifyCode(param.userName,param.verifyType,param.terminalIpAddress);
    }

	protected static class CheckVefifyCodeParam{
		@ApiModelProperty(value = "验证序号ID", dataType = "Long",example = "",required = true)
		public Long verifyCodeId;
		@ApiModelProperty(value = "用户名称", dataType = "String", example = "",required = true)
		public String userName;
		@ApiModelProperty(value = "电话号码", dataType = "String", example = "",required = true)
		public String workTelephone;
	}
    //@PreAuthorize("hasAuthority('fnd:worklog:view')")
    @PostMapping(value = "/checkVerifyCode")
    @ApiOperation(value = "验证是否可以登录API")
    public ResultEntity checkVerifyCode(@RequestBody CheckVefifyCodeParam param) throws Exception {
    	return verifyCodeService.checkVerifyCode(param.verifyCodeId,param.userName,param.workTelephone);
    }

}

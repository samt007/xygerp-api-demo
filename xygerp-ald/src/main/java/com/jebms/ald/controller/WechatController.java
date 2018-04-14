package com.jebms.ald.controller;

import com.alibaba.fastjson.JSONObject;
import com.jebms.ald.service.WechatService;
import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;

import io.swagger.annotations.Api;
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
@RequestMapping("/wechat")
@Api(value = "微信功能管理", description = "微信功能管理")
@SuppressWarnings("rawtypes")
public class WechatController extends BaseController{
    /**
     * 系统数据字典
     */
    @Autowired
    private WechatService wechatService;

    //@PreAuthorize("hasAuthority('fnd:worklog:view')")
    @PostMapping(value = "/getWechatPhoneNumber")
    @ApiOperation(value = "解密微信电话号码API")
    public ResultEntity getWechatPhoneNumber(@RequestBody JSONObject requestJson) throws Exception {
        return wechatService.getWechatPhoneNumber(this.authUser.getWxSessionKey(),requestJson.getString("iv"),requestJson.getString("encryptedData"));
    }

}

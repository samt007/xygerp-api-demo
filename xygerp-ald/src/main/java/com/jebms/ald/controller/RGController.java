package com.jebms.ald.controller;


import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.SearchInfo;
import com.jebms.ald.service.RGService;

/**
 * Author: Sam.T
 * Email: samt007@qq.com
 * Date: 2017/6/23
 * Describe: LOV/RG功能处理
 */
@Controller
@RequestMapping(value = "/")
@SuppressWarnings("rawtypes")
@Api(value = "Record Group",description="记录组接口，通常是被Lov和List所用！")
public class RGController  extends BaseController  {
    @Autowired
    private RGService rgService;
    
    /**
     * 查询所有看板信息 并使用PageHelp分页
     * @throws Exception 
     */
	@ResponseBody
    @GetMapping(value = "/lov/getPageCustomer")
	@ApiOperation(value="获取客户名称接口", notes="获取客户名称的LOV的数据接口")
    public ResultEntity getPageCustomer(@RequestParam Map<String, Object> query,SearchInfo searchInfo) throws Exception {
		//SearchInfo searchInfo = new SearchInfo(requestJson,this.authUser);
		searchInfo.init(this.authUser,query);
		searchInfo.andSqlCondition("customer_name","customerName");
		searchInfo.andSqlCondition("customer_code","customerCode");
        return rgService.getPageCustomer(searchInfo);
    }
    
}

package com.jebms.albc.controller;


import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.albc.service.AlwContainerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The alw container controller.
 *
 * @author samt07@qq.com
 */
@Validated
@RestController
@RequestMapping("/container")
@Api(value = "图型容器模块API", description = "图型容器模块API")
@CrossOrigin("*")
@SuppressWarnings({"rawtypes"})
public class AlwContainerController extends BaseController{
	
    @Autowired
    private AlwContainerService containerService;
    
	@GetMapping(value = "/header/{batchId}")
    @ApiOperation(value = "图型容器头接口")
    public ResultEntity getHeader(
    		@ApiParam(value = "批ID",required = true)@PathVariable("batchId") Long batchId
    		) throws Exception {
        return containerService.selectHeaderByBatchId(batchId);
    }
    
	@GetMapping(value = "/line/{headerId}")
    @ApiOperation(value = "图型容器行接口")
    public ResultEntity getLine(
    		@ApiParam(value = "容器头ID",required = true)@PathVariable("headerId") Long headerId
    		) throws Exception {
        return containerService.selectLineByHeaderId(headerId);
    }

	@GetMapping(value = "/graph/{headerId}")
    @ApiOperation(value = "容器图型组合明细接口")
    public ResultEntity getGraph(
    		@ApiParam(value = "容器头ID",required = true)@PathVariable("headerId") Long headerId
    		) throws Exception {
        return containerService.selectGraphByHeaderId(headerId);
    }
    
}

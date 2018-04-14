package com.jebms.albc.controller;


import java.util.List;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.albc.service.AlbcTotrxService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The albc totrx controller.
 *
 * @author samt07@qq.com
 */
@Validated
@RestController
@RequestMapping("/totrx")
@Api(value = "销售出库模块API", description = "销售出库模块API（物料搬运单）")
@SuppressWarnings("unchecked")
public class AlbcTotrxController extends BaseController{
	
    @Autowired
    private AlbcTotrxService totrxService;

	protected static class TotrxLotNumberRE{
		@ApiModelProperty(value = "发货单ID")
		public Long deliveryId;
		@ApiModelProperty(value = "批次号码(箱号)")
		public String lotNumber;
		@ApiModelProperty(value = "库存组织ID")
		public int organizationId;
		@ApiModelProperty(value = "物料ID")
		public Long inventoryItemId;
		@ApiModelProperty(value = "物料编码")
		public String itemNumber;
		@ApiModelProperty(value = "搬运单号")
		public String requestNumber;
		@ApiModelProperty(value = "搬运单行号")
		public int requestLineNumber;
		@ApiModelProperty(value = "物料描述")
		public String itemDesc;
		@ApiModelProperty(value = "订单号")
		public String sourceHeaderNumber;
		@ApiModelProperty(value = "订单行号")
		public String sourceLineNumber;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/lotNumber/{deliveryId}/{lotNumber}")
    @ApiOperation(value = "发货单批号(箱号)验证接口")
    public ResultEntity<List<TotrxLotNumberRE>> getDeliveryLotNumber(
    		@ApiParam(value = "发货单ID",required = true)@PathVariable("deliveryId") Long deliveryId,
    		@ApiParam(value = "批次(箱号)",required = true)@PathVariable("lotNumber") String lotNumber
    		) throws Exception {
        return totrxService.selectByDeliveryLotNumber(deliveryId,lotNumber,this.authUser);
    }

	protected static class TotrxReservationRE{
		@ApiModelProperty(value = "订单号")
		public String orderNumber;
		@ApiModelProperty(value = "订单行号")
		public int lineNumber;
		@ApiModelProperty(value = "物料编码")
		public String itemNumber;
		@ApiModelProperty(value = "物料描述")
		public String itemDesc;
		@ApiModelProperty(value = "保留箱数")
		public Float reservationBoxQty;
		@ApiModelProperty(value = "保留单位")
		public String reservationUomCode;
		@ApiModelProperty(value = "保留数")
		public Float reservationQuantity;
		@ApiModelProperty(value = "批次号码(箱号)")
		public String lotNumber;
		@ApiModelProperty(value = "库别")
		public String subinventoryCode;
		@ApiModelProperty(value = "货位")
		public String locatorCode;
		@ApiModelProperty(value = "货位ID")
		public Long locatorId;
		@ApiModelProperty(value = "保留ID")
		public Long reservationId;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/reservation/{deliveryId}")
    @ApiOperation(value = "发货单保留信息查询接口")
    public ResultEntity<List<TotrxReservationRE>> getDeliveryReservation(
    		@ApiParam(value = "发货单ID",required = true)@PathVariable("deliveryId") Long deliveryId
    		) throws Exception {
        return totrxService.selectResListByDeliveryId(deliveryId,this.authUser);
    }

	protected static class TotrxDeliveryRE{
		@ApiModelProperty(value = "发货单ID")
		public Long deliveryId;
		@ApiModelProperty(value = "发货单号")
		public String deliveryName;
		@ApiModelProperty(value = "发货单状态")
		public String statusName;
		@ApiModelProperty(value = "库存组织ID")
		public Integer organizationId;
		@ApiModelProperty(value = "发货单请求箱数")
		public Float requestedQuantityBox;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/delivery/{organizationId}/{deliveryName}")
    @ApiOperation(value = "发货单号验证接口")
    public ResultEntity<TotrxDeliveryRE> getDeliveryByName(
    		@ApiParam(value = "库存组织ID",required = true)@PathVariable("organizationId") Integer organizationId,
    		@ApiParam(value = "发货单名称",required = true)@PathVariable("deliveryName") String deliveryName
    		) throws Exception {
        return totrxService.selectByDeliveryName(deliveryName,organizationId,this.authUser);
    }


	protected static class TotrxLotParam{
		@ApiModelProperty(value = "导入行", example = "1",required = true,position = 1)
		public Integer importLineNum;
		@ApiModelProperty(value = "发运号", example = "",required = true,position = 2)
		public String deliveryName;
		@ApiModelProperty(value = "搬运单号", example = "",required = true,position = 3)
		public String requestNumber;
		@ApiModelProperty(value = "搬运行", example = "1",required = true,position = 4)
		public Integer requestLineNumber;
		@ApiModelProperty(value = "批次（箱号）", example = "" ,position = 5)
		public String lotNumber;
	}
	protected static class TotrxImportParam{
		@ApiModelProperty(value = "组织ID", example = "103",required = true,position = 1)
		public Integer organizationId;
		@ApiModelProperty(value = "交易日期(YYYY-MM-DD HH24:MI:SS)", example = "2018-02-09 08:00:00",required = true,position = 2)
		public String transactionDate;
		@ApiModelProperty(value = "默认数量标识", example = "Y",required = true,position = 3)
		public String defaultQtyFlag;
		@ApiModelProperty(value = "导入批次(箱号)列表", example = "",required = true,position = 4)
		public List<TotrxLotParam> data;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/importTotrx")
    @ApiOperation(value = "保存并上传接口")
    public ResultEntity importTotrx(
    		@ApiParam(value = "保存并上传参数",required = true)@Valid @RequestBody TotrxImportParam importParam
    		) throws Exception {
		JSONObject uploadData=(JSONObject) JSON.toJSON(importParam);
		System.out.println(JSON.toJSONString(uploadData));
        return totrxService.importTotrx(uploadData,this.authUser);
    }
}

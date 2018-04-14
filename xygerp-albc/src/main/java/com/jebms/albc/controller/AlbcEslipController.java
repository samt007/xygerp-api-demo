package com.jebms.albc.controller;


import java.util.List;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.SearchInfo;
import com.jebms.albc.service.AlbcEslipService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The albc eslip controller.
 *
 * @author samt07@qq.com
 */
@Validated
@RestController
@RequestMapping("/eslip")
@Api(value = "库存转移模块API", description = "库存转移模块API（电子单据）")
@SuppressWarnings("unchecked")
public class AlbcEslipController extends BaseController{
	
    @Autowired
    private AlbcEslipService eslipService;

	protected static class EslipHeaderRE{
		@ApiModelProperty(value = "库存组织ID")
		public int organizationId;
		@ApiModelProperty(value = "单据号码")
		public String eslipNumber;
		@ApiModelProperty(value = "交易类型ID")
		public int transactionTypeId;
		@ApiModelProperty(value = "交易类型")
		public String transactionTypeName;
		@ApiModelProperty(value = "来源仓库")
		public String subinventoryCode;
		@ApiModelProperty(value = "目标仓库")
		public String transferSubinventory;
		@ApiModelProperty(value = "单据状态")
		public String statusFlagDesc;
		@ApiModelProperty(value = "单据状态代码")
		public String statusFlag;
		@ApiModelProperty(value = "单据ID")
		public Long headerId;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/number/{organizationId}/{eslipNumber}")
    @ApiOperation(value = "单号验证接口")
    public ResultEntity<EslipHeaderRE> getUserOrganization(
    		@ApiParam(value = "库存组织ID",required = true)@PathVariable("organizationId") int organizationId,
    		@ApiParam(value = "单据号码",required = true)@PathVariable("eslipNumber") String eslipNumber
    		) throws Exception {
        return eslipService.selectByEslipNumber(organizationId,eslipNumber,this.authUser);
    }
	

	protected static class EslipLocatorRE{
		@ApiModelProperty(value = "库存组织ID")
		public int organizationId;
		@ApiModelProperty(value = "货位ID")
		public Long locatorId;
		@ApiModelProperty(value = "货位代码")
		public String locatorCode;
		@ApiModelProperty(value = "货位描述")
		public String locatorDesc;
		@ApiModelProperty(value = "库别")
		public String subinventoryCode;
	}
	//@PreAuthorize("hasAuthority('fnd:resp:view')")
    @GetMapping(value = "/getPageLocator")
    @ApiOperation(value = "货位分页列表接口")
    public ResultEntity<PageInfo<EslipLocatorRE>> getPageLocator(
    		@ApiParam(value = "库存组织ID",required = true) @RequestParam(required = true) int organizationId,
    		@ApiParam(value = "库别代码",required = true) @RequestParam(required = true) String subinventoryCode,
    		@ApiParam(value = "货位代码",required = false) @RequestParam(required = false) String locatorCode,
    		SearchInfo searchInfo) throws Exception {
    	searchInfo.getConditionMap().put("organizationId", organizationId);
    	searchInfo.getConditionMap().put("subinventoryCode", subinventoryCode);
    	searchInfo.getConditionMap().put("locatorCode", locatorCode);
    	searchInfo.setAuthUser(this.authUser);
    	searchInfo.initSqlCondition();
    	searchInfo.andSqlCondition("MIL.ORGANIZATION_ID","organizationId");
    	searchInfo.andSqlCondition("MIL.SUBINVENTORY_CODE","subinventoryCode");
    	searchInfo.andSqlCondition("MIL.SEGMENT1","locatorCode");
        return eslipService.selectForPageLocator(searchInfo);
    }
    

	protected static class EslipLotNumberRE{
		@ApiModelProperty(value = "物料编码")
		public String itemNumber;
		@ApiModelProperty(value = "物料描述")
		public String itemDesc;
		@ApiModelProperty(value = "批次号码")
		public String lotNumber;
		@ApiModelProperty(value = "仓库")
		public String subinventoryCode;
		@ApiModelProperty(value = "库存组织ID")
		public int organizationId;
		@ApiModelProperty(value = "物料ID")
		public Long inventoryItemId;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/lotNumber/{organizationId}/{subinventoryCode}/{lotNumber}")
    @ApiOperation(value = "批号(箱号)验证接口")
    public ResultEntity<List<EslipLotNumberRE>> getLotNumber(
    		@ApiParam(value = "库存组织ID",required = true)@PathVariable("organizationId") int organizationId,
    		@ApiParam(value = "库别代码",required = true)@PathVariable("subinventoryCode") String subinventoryCode,
    		@ApiParam(value = "批次(箱号)",required = true)@PathVariable("lotNumber") String lotNumber
    		) throws Exception {
        return eslipService.selectByLotNumber(organizationId,subinventoryCode,lotNumber,this.authUser);
    }
	
	//行批量导入的子类
	protected static class lineParamEslip{
		@ApiModelProperty(value = "导入行", example = "1",required = true,position = 1)
		public Integer importLineNum;
		@ApiModelProperty(value = "单据号码", example = "",required = true,position = 2)
		public String eslipNumber;
		@ApiModelProperty(value = "单据行号", example = "1",required = true,position = 3)
		public Integer eslipLineNum;
		@ApiModelProperty(value = "货位", example = "",required = true,position = 4)
		public String locatorCode;
		@ApiModelProperty(value = "移转货位", example = "",required = true,position = 5)
		public String transferLocatorCode;
		@ApiModelProperty(value = "型号", example = "",required = true,position = 6)
		public String itemNumber;
		@ApiModelProperty(value = "批次（箱号）", example = "" ,position = 7)
		public String lotNumber;
		@ApiModelProperty(value = "交易单位", example = "BOX" ,position = 8)
		public String transactionUom;
		@ApiModelProperty(value = "交易数量", example = "1" ,position = 9)
		public Double transactionQuantity;
		@ApiModelProperty(value = "成本", example = "" ,position = 10)
		public Double transactionCost;
		@ApiModelProperty(value = "行原因", example = "" ,position = 11)
		public String lineReasonName;
		@ApiModelProperty(value = "行单据描述", example = "API导入" ,position = 12)
		public String lineDescription;
	}
	protected static class ImportParamEslipLine{
		@ApiModelProperty(value = "单据头ID", example = "",required = true,position = 1)
		public Long headerId;
		@ApiModelProperty(value = "导入电子单据行列表", example = "",required = true,position = 2)
		public List<lineParamEslip> data;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/importEslip")
    @ApiOperation(value = "保存并上传接口")
    public ResultEntity importEslip(
    		@ApiParam(value = "保存并上传参数",required = true)@Valid @RequestBody ImportParamEslipLine importParam
    		) throws Exception {
		JSONObject uploadData=(JSONObject) JSON.toJSON(importParam);
		System.out.println(JSON.toJSONString(uploadData));
        return eslipService.importEslip(uploadData,this.authUser);
    }
}

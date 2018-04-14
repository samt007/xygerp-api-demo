package com.jebms.albc.controller;

import java.util.List;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.utils.TypeConverter;
import com.jebms.albc.service.AlbcFinishService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The albc finish controller.
 *
 * @author samt07@qq.com
 */
@Validated
@RestController
@RequestMapping("/finish")
@Api(value = "完工入库模块API", description = "完工入库模块API")
@SuppressWarnings({"unchecked","rawtypes"})
public class AlbcFinishController extends BaseController{
	
    @Autowired
    private AlbcFinishService finishService;

    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/validateFinishDate/{finishDate}")
    @ApiOperation(value = "时间验证接口")
    public ResultEntity validateFinishDate(
    		@ApiParam(value = "完工入库日期(YYYY-MM-DD HH24:MI:SS)",required = true)@PathVariable("finishDate") String finishDate
    		) throws Exception {
        return finishService.validateFinishDate(TypeConverter.str2uDate(finishDate),this.authUser);
    }

	protected static class FinishSubinventoryRE{
		@ApiModelProperty(value = "库存组织ID")
		public int organizationId;
		@ApiModelProperty(value = "仓库代码")
		public String subinventoryCode;
		@ApiModelProperty(value = "仓库名称")
		public String subinventoryDesc;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/subinventory/{organizationId}/{subinventoryCode}")
    @ApiOperation(value = "验证仓库(仓库字典)接口")
    public ResultEntity<FinishSubinventoryRE> getSubinventory(
    		@ApiParam(value = "库存组织ID",required = true)@PathVariable("organizationId") int organizationId,
    		@ApiParam(value = "库别代码",required = true)@PathVariable("subinventoryCode") String subinventoryCode
    		) throws Exception {
        return finishService.selectBySubinventoryCode(organizationId,subinventoryCode);
    }
	
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/subinventory/{organizationId}")
    @ApiOperation(value = "获取仓库(仓库字典)接口")
    public ResultEntity<List<FinishSubinventoryRE>> getSubinventoryList(
    		@ApiParam(value = "库存组织ID",required = true)@PathVariable("organizationId") int organizationId
    		) throws Exception {
        return finishService.selectSubListByOrganizationId(organizationId);
    }

	protected static class LocatorRE{
		@ApiModelProperty(value = "库存组织ID")
		public int organizationId;
		@ApiModelProperty(value = "仓库代码")
		public String subinventoryCode;
		@ApiModelProperty(value = "货位ID")
		public Long locatorId;
		@ApiModelProperty(value = "货位描述")
		public String locatorDesc;
		@ApiModelProperty(value = "货位代码")
		public String locatorCode;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/locator/{organizationId}/{subinventoryCode}/{locatorCode}")
    @ApiOperation(value = "验证货位接口")
    public ResultEntity<LocatorRE> getLocator(
    		@ApiParam(value = "库存组织ID",required = true)@PathVariable("organizationId") int organizationId,
    		@ApiParam(value = "库别代码",required = true)@PathVariable("subinventoryCode") String subinventoryCode,
    		@ApiParam(value = "货位代码",required = true)@PathVariable("locatorCode") String locatorCode
    		) throws Exception {
        return finishService.selectByLocatorCode(organizationId,subinventoryCode,locatorCode,this.authUser);
    }
	

	protected static class FinishLotNumberRE{
		@ApiModelProperty(value = "库存组织ID")
		public int organizationId;
		@ApiModelProperty(value = "物料ID")
		public Long inventoryItemId;
		@ApiModelProperty(value = "任务单号码")
		public String wipEntityName;
		@ApiModelProperty(value = "物料编码")
		public String itemNumber;
		@ApiModelProperty(value = "任务单ID")
		public Long wipEntityId;
		@ApiModelProperty(value = "批次号码")
		public String lotNumber;
		@ApiModelProperty(value = "物料描述")
		public String itemDesc;
		@ApiModelProperty(value = "批次（箱号）ID")
		public Long lotHeaderId;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@GetMapping(value = "/lotNumber/{organizationId}/{lotNumber}")
    @ApiOperation(value = "批号(箱号)验证接口")
    public ResultEntity<List<FinishLotNumberRE>> getLotNumber(
    		@ApiParam(value = "库存组织ID",required = true)@PathVariable("organizationId") int organizationId,
    		@ApiParam(value = "批次(箱号)",required = true)@PathVariable("lotNumber") String lotNumber
    		) throws Exception {
        return finishService.selectByLotNumber(organizationId,lotNumber,this.authUser);
    }

	protected static class LotParamFinish{
		@ApiModelProperty(value = "导入行", example = "1",required = true,position = 1)
		public Integer importLineNum;
		@ApiModelProperty(value = "批次（箱号）", example = "" ,position = 2)
		public String lotNumber;
		@ApiModelProperty(value = "入库仓库", example = "",required = true,position = 3)
		public String subinventoryCode;
		@ApiModelProperty(value = "货位", example = "",required = true,position = 4)
		public String locatorCode;
		@ApiModelProperty(value = "备注", example = "",required = true,position = 5)
		public String description;
	}
	protected static class ImportParamFinish{
		@ApiModelProperty(value = "业务实体ID", example = "81",required = true,position = 1)
		public Integer orgId;
		@ApiModelProperty(value = "组织ID", example = "103",required = true,position = 2)
		public Integer organizationId;
		@ApiModelProperty(value = "完工日期(YYYY-MM-DD HH24:MI:SS)", example = "2018-02-09 08:00:00",required = true,position = 3)
		public String finishDate;
		@ApiModelProperty(value = "接收人ID", example = "109924",required = true,position = 4)
		public Long finishPid;
		@ApiModelProperty(value = "交易类型ID", example = "44",required = true,position = 5)
		public Integer transactionTypeId;
		@ApiModelProperty(value = "备注", example = "API导入",required = true,position = 6)
		public String description;
		@ApiModelProperty(value = "导入批次(箱号)列表", example = "",required = true,position = 7)
		public List<LotParamFinish> data;
	}
    //@PreAuthorize("hasAuthority('per:workgroup:view')")
	@PostMapping(value = "/importFinish")
    @ApiOperation(value = "保存并上传接口")
    public ResultEntity importFinish(
    		@ApiParam(value = "保存并上传参数",required = true)@Valid @RequestBody ImportParamFinish importParam
    		) throws Exception {
		JSONObject uploadData=(JSONObject) JSON.toJSON(importParam);
		System.out.println(JSON.toJSONString(uploadData));
        return finishService.importFinish(uploadData,this.authUser);
    }
}

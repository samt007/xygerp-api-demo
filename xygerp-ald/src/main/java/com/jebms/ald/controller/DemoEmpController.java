package com.jebms.ald.controller;


import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jebms.comm.core.BaseController;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.entity.SearchInfo;
import com.jebms.ald.entity.DemoEmp;
import com.jebms.ald.entity.DemoEmpVO;
import  com.jebms.ald.service.DemoEmpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The demo emp controller. 
 *
 * @author samt07@qq.com
 */
@Validated
@RestController
@RequestMapping("/demoEmp")
@Api(value = "演示员工管理", description = "演示员工管理")
@SuppressWarnings({"unchecked"})
public class DemoEmpController extends BaseController{
    /**
     * 系统菜单服务
     */
    @Autowired
    private DemoEmpService empService;

    /**
     * Gets emp page.
     *{ "conditionMap": {"id":"2"},"orderBy":"fml.menu_sequence"}
     * @return the resp page
     * @throws Exception 
     */
	//@PreAuthorize("hasAuthority('fnd:resp:view')")
    @GetMapping(value = "/getPage")
    @ApiOperation(value = "获取员工分页列表")
    @ApiImplicitParam(name = "query", value = "查询条件(由&参数给)", paramType = "query", dataType = "Map",allowableValues="")
    public ResultEntity<PageInfo<DemoEmpVO>> getPage(@RequestParam Map<String, Object> query,SearchInfo searchInfo) throws Exception {
    	//SearchInfo searchInfo = new SearchInfo(requestJson,this.authUser);
    	searchInfo.init(this.authUser,query);
    	System.out.println(JSON.toJSONString(query));
    	//System.out.println(JSON.toJSONString(searchInfo));
        return empService.selectForPage(searchInfo);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{empno}")
    @ApiOperation(value = "获取单个员工")
    public ResultEntity<DemoEmp> select(@ApiParam(value="员工工号",required = true)@PathVariable("empno") Long empno) {
    	return ResultInfo.success(empService.selectVOByPK(empno));
    }
    
    //@PreAuthorize("hasAuthority('fnd:resp:edit')")
    @PostMapping(value = "")
    @ApiOperation(value = "新增员工")
    public ResultEntity<DemoEmp> insert(@ApiParam(value="员工信息",required = true)@Valid @RequestBody DemoEmpVO record) {
        return empService.insert(record);
    }

	//@PreAuthorize("hasAuthority('fnd:resp:edit')")
    @PutMapping(value = "")
    @ApiOperation(value = "更新员工")
    public ResultEntity<DemoEmp> update(@ApiParam(value="员工信息",required = true)@Valid @RequestBody DemoEmpVO record) {
        return empService.update(record);
    }

	//@PreAuthorize("hasAuthority('fnd:resp:edit')")
	@DeleteMapping(value = "/{empno}")
    @ApiOperation(value = "删除员工")
    public ResultEntity<DemoEmp> delete(@ApiParam(value="员工工号",required = true)@PathVariable("empno") Long empno) {
		DemoEmpVO record=new DemoEmpVO();
    	record.setEmpno(empno);
        return empService.delete(record);
    }
}

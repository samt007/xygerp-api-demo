package com.jebms.ald.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jebms.ald.dao.DemoEmpDao;
import com.jebms.ald.entity.DemoEmp;
import com.jebms.ald.entity.DemoEmpVO;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.entity.SearchInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jebms.ald.service.DemoEmpService;


/**
 * Demo emp业务方法
 * 需要注意的是，这里没继承封装的BaseService。主要是因为这个表格的没有5who栏位，导致无法标准化操作！
 *
 * @author samt007@qq.com
 * @version 1.0
 * @date 2018年2月4日
 */
@Service
@SuppressWarnings("rawtypes")
public class DemoEmpServiceImpl implements DemoEmpService {
	
	@Autowired
	private DemoEmpDao mapper;
    /**
     * 查询分页信息
     * @param searchInfo 传入当前分页数 和 搜索信息
     * @return
     * @throws Exception 
     */
	public ResultEntity selectForPage(SearchInfo searchInfo) throws Exception {
        PageHelper.startPage(searchInfo.getPageNum(), searchInfo.getPageSize() ,searchInfo.isCount());
        List<DemoEmpVO> pageList = mapper.selectForPage(searchInfo);
        //System.out.println("pageList Size is:"+pageList.size());
        PageInfo<DemoEmpVO> pageInfo = new PageInfo<>(pageList);
        return ResultInfo.success(pageInfo);
    }

    public DemoEmpVO selectVOByPK(Long empno) {
    	DemoEmpVO ret=mapper.selectVOByPK(empno);
        return ret;
    }
    
    public ResultEntity insert(DemoEmp record) {
    	//record.setWhoInsert(authUser); 这个动作必须在新增之前要做。
    	if(mapper.insert(record)==1){
    		return ResultInfo.success(record);
    	}else{
    		return ResultInfo.error("新增数据失败！");
    	}
    }
    

	public ResultEntity update(DemoEmp record) {
    	//Update之前必须要先检查数据是否和数据库一致。逻辑是：
    	//输入的参数record是没更新之前的数据，所以，理论上应该要和数据库的一样才对！
    	//所以，这里要先根据ID来获取数据库的记录，然后再所有属性和record做对比。一样，则Lock成功。不一样则Lock失败。
    	/*if(!record.equalValueUUID(mapper)){
    		return ResultInfo.error("数据已经被修改！请重新查询再更新数据！");
    	}*/
    	//record.setWhoUpdate(authUser);这个动作必须在更新之前要做。
		if(mapper.updateByPrimaryKey(record)==1){
			return ResultInfo.success(mapper.selectByPrimaryKey(record));
		}else{
			return ResultInfo.error("未更新数据！请检查条件！");
		}
    }

    @SuppressWarnings("unchecked")
	public ResultEntity<DemoEmp> delete(DemoEmp record) {
    	if(mapper.deleteByPrimaryKey(record)==1){
    		return ResultInfo.success(record);
    	}else{
    		return ResultInfo.error("未删除数据！请检查条件！");
    	}
    }
    
}

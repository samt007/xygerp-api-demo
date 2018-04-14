package com.jebms.ald.service;

import com.jebms.ald.entity.DemoEmp;
import com.jebms.ald.entity.DemoEmpVO;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.SearchInfo;


/**
 * Demo emp业务方法
 * 需要注意的是，这里没继承封装的BaseService。主要是因为这个表格的没有5who栏位，导致无法标准化操作！
 *
 * @author samt007@qq.com
 * @version 1.0
 * @date 2018年2月4日
 */
@SuppressWarnings("rawtypes")
public interface DemoEmpService {
	
    /**
     * 查询分页信息
     * @param searchInfo 传入当前分页数 和 搜索信息
     * @return
     * @throws Exception 
     */
	public ResultEntity selectForPage(SearchInfo searchInfo) throws Exception ;

    public DemoEmpVO selectVOByPK(Long empno) ;
    
    public ResultEntity insert(DemoEmp record) ;
    
	public ResultEntity update(DemoEmp record) ;

	public ResultEntity<DemoEmp> delete(DemoEmp record) ;
    
}

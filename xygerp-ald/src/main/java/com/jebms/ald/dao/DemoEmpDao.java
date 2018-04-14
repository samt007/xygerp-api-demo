package com.jebms.ald.dao;


/**
 * Demo Emp Dao
 *
 * @author samt007@qq.com
 * @version 1.0
 * @date 2018年2月4日
 */


import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.jebms.comm.entity.SearchInfo;
import com.jebms.ald.entity.DemoEmp;
import com.jebms.ald.entity.DemoEmpVO;

public interface DemoEmpDao  extends Mapper<DemoEmp> {

    List<DemoEmpVO> selectForPage(SearchInfo searchInfo);

    DemoEmpVO selectVOByPK(Long empno);
    
}
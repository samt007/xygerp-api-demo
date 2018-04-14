package com.jebms.albc.service.impl;

import com.jebms.albc.dao.AlwContainerDao;
import com.jebms.albc.service.AlwContainerService;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ALW 图型容器处理的Service封装。
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings("rawtypes")
public class AlwContainerServiceImpl implements AlwContainerService {
    
    @Autowired
    private AlwContainerDao containerDao;
    
    //获取某个批号的所有容器
	public ResultEntity selectHeaderByBatchId(Long batchId) throws Exception {
		return ResultInfo.success(containerDao.selectHeaderByBatchId(batchId));
    }
    
    //获取某个头的图型行
	public ResultEntity selectLineByHeaderId(Long headerId) throws Exception {
		return ResultInfo.success(containerDao.selectLineByHeaderId(headerId));
    }
    
    //获取某个头的图型明细
	public ResultEntity selectGraphByHeaderId(Long headerId) throws Exception {
		return ResultInfo.success(containerDao.selectGraphByHeaderId(headerId));
    }
}

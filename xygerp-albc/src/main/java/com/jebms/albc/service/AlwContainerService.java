package com.jebms.albc.service;

import com.jebms.comm.entity.ResultEntity;

/**
 * ALW 图型容器处理的Service封装。
 *
 * @author samt007@qq.com
 */

@SuppressWarnings("rawtypes")
public interface AlwContainerService {
    
    //获取某个批号的所有容器
	public ResultEntity selectHeaderByBatchId(Long batchId) throws Exception ;
    
    //获取某个头的图型行
	public ResultEntity selectLineByHeaderId(Long headerId) throws Exception ;
    
    //获取某个头的图型明细
	public ResultEntity selectGraphByHeaderId(Long headerId) throws Exception ;
}

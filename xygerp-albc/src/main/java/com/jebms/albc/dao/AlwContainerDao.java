package com.jebms.albc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AlwContainerDao {
 
    List<Map<String,Object>> selectHeaderByBatchId(@Param(value="batchId") Long batchId);
 
    List<Map<String,Object>> selectLineByHeaderId(@Param(value="headerId") Long headerId);
 
    List<Map<String,Object>> selectGraphByHeaderId(@Param(value="headerId") Long headerId);
    
}
package com.jebms.albc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.jebms.ald.entity.AldUploadTemp;


public interface AldUploadTempDao extends Mapper<AldUploadTemp> {

    List<Map<String,Object>> selectTotrxUploadByBatchId(
    		@Param(value="batchId") Long batchId);

    List<Map<String,Object>> selectFinishUploadByBatchId(
    		@Param(value="batchId") Long batchId);
    
}
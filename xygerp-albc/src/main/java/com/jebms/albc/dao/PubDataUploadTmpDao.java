package com.jebms.albc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.jebms.ald.entity.PubDataUploadTmp;


public interface PubDataUploadTmpDao extends Mapper<PubDataUploadTmp> {
	
    List<Map<String,Object>> selectEslipUploadByBatchId(
    		@Param(value="batchId") Long batchId);
}
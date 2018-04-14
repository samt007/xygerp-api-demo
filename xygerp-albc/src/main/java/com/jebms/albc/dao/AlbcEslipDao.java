package com.jebms.albc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jebms.comm.entity.SearchInfo;

public interface AlbcEslipDao {

    Map<String,Object> selectByEslipNumber(
    		@Param(value="organizationId") int organizationId,
    		@Param(value="eslipNumber") String eslipNumber);
    
    List<Map<String,Object>> selectForPageLocator(SearchInfo searchInfo);
    
    List<Map<String,Object>> selectByLotNumber(
    		@Param(value="organizationId") int organizationId,
    		@Param(value="subinventoryCode") String subinventoryCode,
    		@Param(value="lotNumber") String lotNumber);
    
    void importEslipLine(Map<String,Object> params);
    
    void postEslip(Map<String,Object> params);
    
    void eslipSubinvConfirm(Map<String,Object> params);
}
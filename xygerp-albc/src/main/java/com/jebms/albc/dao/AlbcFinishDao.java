package com.jebms.albc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AlbcFinishDao {

    Integer validateFinishDate(
    		@Param(value="finishDate") Date finishDate);
    
    Map<String,Object> selectBySubinventoryCode(
    		@Param(value="organizationId") int organizationId,
    		@Param(value="subinventoryCode") String subinventoryCode);
    
    List<Map<String,Object>> selectSubListByOrganizationId(
    		@Param(value="organizationId") int organizationId);
    
    Map<String,Object> selectByLocatorCode(
    		@Param(value="organizationId") int organizationId,
    		@Param(value="subinventoryCode") String subinventoryCode,
    		@Param(value="locatorCode") String locatorCode);
    
    List<Map<String,Object>> selectByLotNumber(
    		@Param(value="organizationId") int organizationId,
    		@Param(value="lotNumber") String lotNumber);

    void importFinish(Map<String,Object> params);
}
package com.jebms.albc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AlbcTotrxDao {
    
    List<Map<String,Object>> selectListByDeliveryLotNumber(
    		@Param(value="deliveryId") Long deliveryId,
    		@Param(value="lotNumber") String lotNumber);

    List<Map<String,Object>> selectResListByDeliveryId(
    		@Param(value="deliveryId") Long deliveryId);
    
    Map<String,Object> selectByDeliveryName(
    		@Param(value="deliveryName") String deliveryName,
    		@Param(value="organizationId") Integer organizationId);
    
    void importTotrx(Map<String,Object> params);
    
}
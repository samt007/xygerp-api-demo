package com.jebms.albc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AlbcUserDao {

    /**
     * 查询列表数据
     * @param searchInfo 传入查询参数
     * @return
     */
    List<Map<String,Object>> selectForUserOrganization(@Param(value="userId") Long userId);
    
}
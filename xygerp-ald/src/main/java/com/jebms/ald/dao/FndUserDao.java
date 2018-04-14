package com.jebms.ald.dao;


/**
 * 用户Dao
 *
 * @author samt007@qq.com
 * @version 1.0
 * @date 2017年8月30日
 */


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.jebms.comm.entity.SearchInfo;
import com.jebms.ald.entity.FndUser;
import com.jebms.ald.entity.FndUserVO;

public interface FndUserDao  extends Mapper<FndUser> {

    FndUserVO selectByUserName(@Param(value = "userName") String userName);

    List<FndUserVO> selectForPage(SearchInfo searchInfo);

    FndUserVO selectVOByPK(Long userId);
    
    void validateLogin(Map<String,Object> params);
    
    void newAoljSession(Map<String,Object> params);
    
    void auditEnd(Map<String,Object> params);
}
package com.jebms.ald.dao;


/**
 * Verify Code Dao
 *
 * @author samt007@qq.com
 * @version 1.0
 * @date 2018年1月30日
 */


import java.util.Map;

public interface VerifyCodeDao {
    
    void updateVerifyCode(Map<String,Object> params);
}
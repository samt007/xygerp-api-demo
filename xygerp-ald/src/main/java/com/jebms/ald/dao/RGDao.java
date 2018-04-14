package com.jebms.ald.dao;

import java.util.List;
import java.util.Map;

import com.jebms.comm.entity.SearchInfo;

/**
 * 查询的Dao
 *
 * @author samt007@qq.com
 * @version 1.0
 * @date 2017年6月22日
 */
public interface RGDao {
	
    /**
     * RG记录组查询Dao
     * @param searchInfo 传入查询参数
     * @return
     */
    List<Map<String, Object>> selectForCustomer(SearchInfo searchInfo);
    

}
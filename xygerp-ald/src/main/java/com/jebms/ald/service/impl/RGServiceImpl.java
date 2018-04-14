package com.jebms.ald.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.entity.SearchInfo;
import com.jebms.comm.springjdbc.DevJdbcDaoSupport;
import com.jebms.ald.dao.RGDao;
import com.jebms.ald.service.RGService;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 记录组的业务方法
 * 提供所有的LOV和List所需要的查询！
 *
 * @author samt007@qq.com
 * @version 1.0
 * @date 2017年6月25日
 */

@Service
@SuppressWarnings("rawtypes")
public class RGServiceImpl extends DevJdbcDaoSupport implements RGService {

    @Autowired
    private RGDao rgDao;
    
    /*
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }*/
    
	@Autowired
	RGServiceImpl(DataSource dataSource) {
	    setDataSource(dataSource);
	}
    
	public ResultEntity getPageCustomer(SearchInfo searchInfo) throws Exception {
        PageHelper.startPage(searchInfo.getPageNum(), searchInfo.getPageSize() ,searchInfo.isCount());
        List<Map<String, Object>> list = rgDao.selectForCustomer(searchInfo);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(pageInfo);
    }
    
    public List<Map<String,Object>> getLookups(String lookupType,String lang) throws Exception{
		String sql = "SELECT MEANING display,LOOKUP_CODE value"
				+ " FROM fnd_lookup_values  "
				+ " WHERE LOOKUP_TYPE = :1 "
				+ "   AND LANGUAGE = :2 "
				+ " ORDER BY 1 ";
		Map<String,Object> paramMap=new  HashMap<String,Object>();
		paramMap.put("1", lookupType);
		paramMap.put("2", lang);
    	return this.getDevJdbcTemplate().queryForList(sql,paramMap);
    }
    
}

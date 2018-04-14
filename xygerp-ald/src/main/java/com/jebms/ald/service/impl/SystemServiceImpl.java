package com.jebms.ald.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.ald.dao.FndUserDao;
import com.jebms.ald.entity.FndUserVO;
import com.jebms.ald.service.SystemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * XYG ERP 系统管理，安全相关实体的管理类,包括用户、职责、菜单、功能，以及用户登录记录
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings("rawtypes")
public class SystemServiceImpl implements SystemService {
    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private FndUserDao userDao;

    /*private BaseDao devDao;

	@Autowired
	SystemService(DataSource dataSource) {
	    this.devDao = new BaseDao(dataSource);
	}*/
    
    //如果存在多语言的处理，则直接创建该成员变量即可
    //private Language lang=new Language();

    /**
     * 根据登录名获取用户
     *
     * @param username 用户名
     * @return FndUser user by user name
     */
    public FndUserVO selectByUsername(String username,String lang){
    	FndUserVO user = null;
    	try{
    		user = userDao.selectByUserName(username.toUpperCase());
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
        //user.setRespVOs(respDao.selectListByUserId(user.getId(),lang));
        //user.setMenuVOs(getMenuListByUserId(user.getId(),lang));
        return user;
    }
    
    // 验证XYG ERP系统的用户名和密码是否正确。
    public boolean xygErpValidateLogin(String userName,String password) {
    	String result = "";
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("userName", userName);
    	params.put("password", password);
    	params.put("result", "");
    	userDao.validateLogin(params);
    	result=(String) params.get("result");
    	LOGGER.info(String.format("VALIDATE_LOGIN return is: %s", result));
    	if(result!=null && result.equals("Y")){
    		return true;
    	}
    	return false;
    	/*Map<String,Object> inParamMap=new HashMap<String,Object>();
    	inParamMap.put("P_USER_NAME", userName);
    	inParamMap.put("P_PASSWORD", password);
		Map<String,Integer> outParamMap=new HashMap<String,Integer>();//定义输出参数
		outParamMap.put("3", Types.VARCHAR);
		String sql ="Declare "
				+ "     L_RET VARCHAR2(10); "
				+ "  begin "
				+ "  L_RET:= APPS.FND_WEB_SEC.VALIDATE_LOGIN(:P_USER_NAME, :P_PASSWORD); "
				+ "  :3 := L_RET; "
				+ " end; ";
		Map<String, Object> outValueMap=new HashMap<String,Object>();
		try {
			outValueMap = devDao.getDevJdbcTemplate().execute(sql, inParamMap, outParamMap);
			LOGGER.info(String.format("VALIDATE_LOGIN return is: %s", outValueMap.get("3")));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(outValueMap.get("3")!=null && outValueMap.get("3").equals("Y")){
    		return true;
    	}
    	return false;*/
    }
    
    //新增登录信息。这里是调用erp的api产生登录信息
    public Long toLogin(Long userId){
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("userId", userId);
    	userDao.newAoljSession(params);
    	LOGGER.info(String.format("FND_SIGNON return login id is: %s", params.get("loginId")));
    	return (Long) params.get("loginId");
    	/*Map<String,Object> inParamMap=new HashMap<String,Object>();
    	inParamMap.put("P_USER_ID", userId);
		Map<String,Integer> outParamMap=new HashMap<String,Integer>();//定义输出参数
		outParamMap.put("X_LOGIN_ID", Types.BIGINT);
		outParamMap.put("X_EXPIRED", Types.VARCHAR);
		String sql ="  begin "
				+ " APPS.FND_SIGNON.new_aolj_session(:P_USER_ID, :X_LOGIN_ID,:X_EXPIRED); "
				+ " end; ";
		Map<String, Object> outValueMap=new HashMap<String,Object>();
		try {
			outValueMap = devDao.getDevJdbcTemplate().execute(sql, inParamMap, outParamMap);
			LOGGER.info(String.format("FND_SIGNON return login id is: %s", outValueMap.get("X_LOGIN_ID")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Long) outValueMap.get("X_LOGIN_ID");*/
    }
    
    //退出登录。这里是调用erp的api产生登录信息
	public ResultEntity toLogout(Long loginId){
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("loginId", loginId);
    	userDao.auditEnd(params);
    	return ResultInfo.success();
    	/*
    	Map<String,Object> inParamMap=new HashMap<String,Object>();
    	inParamMap.put("P_LOGIN_ID", loginId);
		String sql ="  begin "
				+ " APPS.FND_SIGNON.AUDIT_END(:P_LOGIN_ID); "
				+ " end; ";
		try {
			devDao.getDevJdbcTemplate().execute(sql, inParamMap);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultInfo.error(e.getMessage());
		}
		return ResultInfo.success();*/
    }
	
}

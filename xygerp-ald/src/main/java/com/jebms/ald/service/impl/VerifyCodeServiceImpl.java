package com.jebms.ald.service.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.jebms.ald.dao.VerifyCodeDao;
import com.jebms.ald.service.VerifyCodeService;
import com.jebms.comm.core.BaseDao;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * VerifyCode验证码处理的Service封装。
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings("rawtypes")
public class VerifyCodeServiceImpl implements VerifyCodeService {
    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);
    
    private BaseDao devDao;

	@Autowired
	VerifyCodeServiceImpl(DataSource dataSource) {
	    this.devDao = new BaseDao(dataSource);
	}
	
	@Autowired
	private VerifyCodeDao verifyCodeDao;

    public Map<String,Object> getVerifyCodeDefine(String userName,String getType,String verifyType) {
    	Map<String,Object> inParamMap=new HashMap<String,Object>();
    	inParamMap.put("1", userName.toUpperCase());
    	inParamMap.put("2", getType);
    	inParamMap.put("3", verifyType);
		Map<String,Integer> outParamMap=new HashMap<String,Integer>();//定义输出参数
		outParamMap.put("4", Types.VARCHAR);
		outParamMap.put("5", Types.VARCHAR);
		outParamMap.put("6", Types.BIGINT);
		outParamMap.put("7", Types.BIGINT);
		outParamMap.put("8", Types.BIGINT);
		outParamMap.put("9", Types.VARCHAR);
        String sql = "DECLARE "
                + " l_retcode number; "
                + " l_errbuf varchar2(2000); "
                + "BEGIN "
                + " APPS.XYG_ALD_LOGIN_PKG.GET_VERIFY_CODE_DEFINE(:1,:2,:3,:4,:5,:6,:7,l_retcode,l_errbuf); "
                + " :8 := l_retcode; "
                + " :9 := l_errbuf; "
                + "END;";
		Map<String, Object> outValueMap=new HashMap<String,Object>();
		Map<String,Object> ret=new HashMap<String,Object>();
		try {
			outValueMap = devDao.getDevJdbcTemplate().execute(sql, inParamMap, outParamMap);
			LOGGER.info(String.format("GET_VERIFY_CODE_DEFINE return is: %s", outValueMap.get("8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
        ret.put("verifyCodeFlag",outValueMap.get("4"));
        ret.put("workTelephone",outValueMap.get("5"));
        ret.put("userId",outValueMap.get("6"));
        ret.put("personId",outValueMap.get("7"));
        ret.put("retcode",outValueMap.get("8"));
        ret.put("errbuf",outValueMap.get("9"));
        
    	return ret;
    }
    
    // 新增短信认证记录到EBS
    public ResultEntity insertVerifyCode(String userName,Long userId,Long personId,String verifyCodeFlag,String workTelephone,String terminalIpAddress) throws Exception{
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("1",userName.toUpperCase());
        params.put("2",userId);
        params.put("3",personId);
        params.put("4",verifyCodeFlag);
        params.put("5",workTelephone);
        params.put("6",new Timestamp((new Date()).getTime()));
        params.put("7", terminalIpAddress);
        String sql ="DECLARE "
                  + " l_verify_code number; "
                  + " BEGIN "
                  + " APPS.XYG_ALD_LOGIN_PKG.INSERT_VERIFY_CODE(:1,:2,:3,:4,:5,:6,:7"
    			  + " ,l_verify_code"
  				  + " ,:"+ResultEntity.CODE
				  + " ,:"+ResultEntity.MESSAGE
				  + "  ); "
				  + " :"+ResultEntity.PARAM1+" := l_verify_code; "
				  + " :"+ResultEntity.PARAM2+" := APPS.XYG_ALD_LOGIN_PKG.GET_LOGIN_QRCODE_URL(l_verify_code); "
                  + "END;";
        return this.devDao.getDevJdbcTemplate().executeForResultEntity(sql, params);
    }
    

    //认证是否允许登录
	public ResultEntity checkVerifyCode(Long verifyCodeId,String userName,String workTelephone) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
    	params.put("verifyCodeId", verifyCodeId);
    	params.put("userName", userName.toUpperCase());
    	params.put("workTelephone", workTelephone);
    	params.put("validate", "Y");//仅仅验证
    	verifyCodeDao.updateVerifyCode(params);
    	return ResultInfo.fromParams(params);
    	/*Map<String,Object> params=new HashMap<String,Object>();
    	params.put("P_VERIFY_CODE_ID", verifyCodeId);
    	params.put("P_USER_NAME", userName.toUpperCase());
    	params.put("P_WORK_TELEPHONE", workTelephone);
		String sql ="  begin "
				+ "  APPS.XYG_ALD_LOGIN_PKG.UPDATE_VERIFY_CODE( "
				+ "  :P_VERIFY_CODE_ID "
				+ " ,:P_USER_NAME "
				+ " ,:P_WORK_TELEPHONE "
				+ " ,'Y' "//仅仅验证
				+ " ,:"+ResultEntity.CODE
				+ " ,:"+ResultEntity.MESSAGE
				+ " ); "
				+ " end; ";
        return this.devDao.getDevJdbcTemplate().executeForResultEntity(sql, params);*/
    }
    
    //验证绑定电话接口
	public ResultEntity getVerifyCode(String userName,String verifyType,String terminalIpAddress) throws Exception {
		Map<String,Object> define=this.getVerifyCodeDefine(userName,"GET_CODE",verifyType);
        String workTelephone=(String)define.get("workTelephone");
        String verifyCodeFlag=(String)define.get("verifyCodeFlag");
        Long userId=(Long)define.get("userId");
        Long personId=(Long)define.get("personId");
        Long retcode=(Long)define.get("retcode");
        String errbuf=(String)define.get("errbuf");
        if(retcode!=null&&retcode==0){
            if(verifyCodeFlag.equals("Y")){
            	return this.insertVerifyCode(userName,userId,personId,verifyCodeFlag,workTelephone,terminalIpAddress);
            }else{
            	return ResultInfo.error("3","XYG_ALOAF_VC_NOT_ENABLED");
            }
        }else{
        	return ResultInfo.error(errbuf);
        }
        
    }
	
    //验证绑定电话接口
	public ResultEntity verifyPhoneNumber(Long verifyCodeId,String phoneNumber) throws Exception {
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("P_VERIFY_CODE_ID", verifyCodeId);
    	params.put("P_WORK_TELEPHONE", phoneNumber);
    	params.put("P_VERIFY_CODE_DATE", new Timestamp((new Date()).getTime()));
		String sql ="  begin "
				+ "  APPS.XYG_ALD_LOGIN_PKG.CHECK_VERIFY_CODE( "
				+ "  :P_VERIFY_CODE_ID "
				+ " ,:P_WORK_TELEPHONE "
				+ " ,:P_VERIFY_CODE_DATE "//:P_VERIFY_CODE_DATE
				+ " ,:"+ResultEntity.CODE
				+ " ,:"+ResultEntity.MESSAGE
				+ " ); "
				+ " end; ";
        return this.devDao.getDevJdbcTemplate().executeForResultEntity(sql, params);
    }

}

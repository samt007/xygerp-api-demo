package com.jebms.ald.service.impl;


import org.apache.commons.codec.binary.Base64; 

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jebms.comm.entity.ResultEntity;
import com.jebms.comm.entity.ResultInfo;
import com.jebms.comm.utils.AES;
import com.jebms.comm.utils.HttpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Wechat微信处理的Service封装。
 *
 * @author samt007@qq.com
 */

@Service
@SuppressWarnings("rawtypes")
public class WechatService {
    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(WechatService.class);
    
    //private BaseDao devDao;

	// @Autowired
	/*WechatService(DataSource dataSource) {
	    this.devDao = new BaseDao(dataSource);
	}*/
	
	public ResultEntity getWechatSession(String wxLoginCode) throws Exception{
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx0a1dcd5c0b1c0908&secret=ae65b822f4bdf2be2242b6757eb4598c&js_code=" + wxLoginCode + "&grant_type=authorization_code";  
		ResultEntity getWXRes =HttpUtils.sendRequest("HTTPS", "GET", url, null, null);
		if(getWXRes.isOk()){
			JSONObject wxJson = (JSONObject) getWXRes.getData();
			String session_key = wxJson.getString("session_key");
			System.out.println("session_key:"+session_key);
			if(null != session_key && session_key.length() > 0){ 
				return ResultInfo.success(wxJson);
			}else{
            	return ResultInfo.error("获取微信信息解密失败！session_key结果为空！");
            }
		}else{
			return ResultInfo.error("获取微信服务器的session_key信息失败，错误信息："+getWXRes.getMessage());
		}	
	}
    
    //解密微信绑定电话接口
	public ResultEntity getWechatPhoneNumber(String sessionKey,String iv,String encryptedData) {
        try {  
            AES aes = new AES();  
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));  
            if(null != resultByte && resultByte.length > 0){  
                String phoneInfo = new String(WxPKCS7Encoder.decode(resultByte));
                System.out.println(phoneInfo);
                return ResultInfo.success(JSON.parseObject(phoneInfo));
            }else{
            	return ResultInfo.error("解密失败！resultByte结果为空！");
            }
        } catch (Exception e) {  
        	e.printStackTrace();  
        	return ResultInfo.error("解密内容失败，错误信息："+e.getMessage());
        }
    }
    

}

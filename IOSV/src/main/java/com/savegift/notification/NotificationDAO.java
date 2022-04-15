package com.savegift.notification;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.savegift.login.LoginVO;

@Repository
public class NotificationDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationDAO.class);
	
	@Autowired
    SqlSession mybatis;
	
	
	public LoginVO status(String user_id) {
		LoginVO loginVO = mybatis.selectOne("NotificationMapper.status", user_id);
		if(loginVO != null) {
			logger.info("DAO status... " + loginVO.getEmail_yn());
			logger.info("DAO status... " + loginVO.getSms_yn());
			logger.info("DAO status... " + loginVO.getName());
		}
		
		return loginVO;
	}
	
	public boolean notiSetting(HashMap<String, Object> requestMap) {
		boolean resultBool = false;
		
		String user_id = (String) requestMap.get("user_id"); // 아이디
		int push_yn = (Integer) requestMap.get("push_yn"); // 푸시 
		int email_yn = (Integer) requestMap.get("email_yn"); // 이메일 
		int sms_yn = (Integer) requestMap.get("sms_yn"); // 문자
		
		logger.info("/notiSetting.. "+ user_id + " push_yn : " + push_yn + ", email_yn : " + email_yn + ", sms_yn : " + sms_yn);
		
		int result = mybatis.update("NotificationMapper.notiSetting", requestMap);
		if(result == 1) {
			resultBool = true;
		}
		
		return resultBool;
	}
	
	public String version() {
		String result = mybatis.selectOne("NotificationMapper.version");
		
		return result;
	}
	
}
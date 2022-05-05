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
	
	public NotificationVO status2(String device_id) {
		NotificationVO notificationVO = mybatis.selectOne("NotificationMapper.status2", device_id);
		if(notificationVO != null) {
			logger.info("DAO status... " + notificationVO.getPush1());
//			logger.info("DAO status... " + notificationVO.getPush_token());
		}
		
		return notificationVO;
	}
	
	public boolean notiSetting(HashMap<String, Object> requestMap) {
		boolean resultBool = false;
		
		int index = (Integer) requestMap.get("index"); // 문자
		
		if(index ==0) { // user_device UPDATE --> 푸시 푸시30 푸시7 푸시1
			String device_id = (String) requestMap.get("device_id"); // 아이디
			int push_yn = (Integer) requestMap.get("push_yn"); // 푸시	
			int push30 = (Integer) requestMap.get("push30"); // 푸시	
			int push7 = (Integer) requestMap.get("push7"); // 푸시	
			int push1 = (Integer) requestMap.get("push1"); // 푸시
			
			logger.info("/notiSetting1.. "+ device_id + ", push_yn : " + push_yn + ", push30 : " + push30 + ", push7 : " + push7 + ", push1 : " + push1);
			
			int result = mybatis.update("NotificationMapper.notiSetting1", requestMap);
			if(result == 1) {
				resultBool = true;
			}
			
		}else { // user 문자 이메일
			String user_id = (String) requestMap.get("user_id"); // 아이디
			int email_yn = (Integer) requestMap.get("email_yn"); // 이메일 
			int sms_yn = (Integer) requestMap.get("sms_yn"); // 문자
			
			logger.info("/notiSetting2.. "+ user_id + ", email_yn : " + email_yn + ", sms_yn : " + sms_yn);
			
			int result = mybatis.update("NotificationMapper.notiSetting2", requestMap);
			if(result == 1) {
				resultBool = true;
			}
		}
		
		return resultBool;
	}
	
	public String version() {
		String result = mybatis.selectOne("NotificationMapper.version");
		
		return result;
	}
	
	public boolean deviceInsert(HashMap<String, Object> requestMap) {
		boolean resultBool = false;
		String device_model = (String) requestMap.get("device_model");
		String device_id = (String) requestMap.get("device_id");
		String push_token = (String) requestMap.get("push_token");
		int push_yn = (Integer) requestMap.get("push_yn");
		int push30 = (Integer) requestMap.get("push30");
		int push7 = (Integer) requestMap.get("push7");
		int push1 = (Integer)requestMap.get("push1");
		
		
		NotificationVO notificationVO = mybatis.selectOne("NotificationMapper.userDevice", device_id);
		if(notificationVO == null) {
			logger.info("DB insert {"+device_id+"} --> token["+push_token+"]");
			int result = mybatis.insert("NotificationMapper.userDeviceInsert", requestMap);
			
			if(result == 1) {
				resultBool = true;	
			}else {
				resultBool = false;
			}
		}
		
		return resultBool;
	}
	
}

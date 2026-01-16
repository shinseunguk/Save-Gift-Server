package com.savegift.controller;

import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.savegift.service.MailService;
import com.savegift.service.UserService;

@Controller
public class EmailController {
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/noticeMail", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public boolean sendEmail(@RequestBody HashMap<String, Object> requestMap) throws Exception {
		boolean result = false;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		String email = (String) requestMap.get("user_id");
		
		Random rand = new Random();
		String numStr = "";
		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr += ran;
		}
		
		result = userService.checkEmailInfo(requestMap);
		
		if(result) {
			logger.info(email + "님에게 메일 전송 시도.. ");

			String addr = "krdut1@gmail.com";
			
			String subject = "[기프티콘 저장소] 이메일 인증을 위한 인증번호가 발급되었습니다.";
			
			String body = "안녕하세요.\n\n기프티콘 저장소 인증을 위한 인증번호가 발급되었습니다.\n아래의 인증번호 복사하거나 직접 입력하여 이메일 인증을 완료해주세요.\n\n"+ numStr;
			
			mailService.sendEmail(email, addr, subject, body);
			userService.certNumberDB(email, numStr);
			
			return true;	
		}else {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/check/email", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean checkEmail(@RequestBody HashMap<String, Object> requestMap) throws Exception {
		boolean result = false;
		result = userService.checkEmail(requestMap);
		
		return result;
	}
}

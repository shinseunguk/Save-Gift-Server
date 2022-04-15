package com.savegift.giftcon;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Handles requests for the application home page.
 */
@Controller
public class GiftController {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftController.class);
	
	@Autowired
	GiftService giftService;
	
	
	@RequestMapping(value = "/asdfasdf", method = RequestMethod.GET)
	@ResponseBody
	public void duplicationid(){
		logger.info("asdfasdf");
		giftService.duplicationid();
	}
	
	@RequestMapping(value = "/register/gift", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean registerGift(@RequestBody HashMap<String, Object> requestMap){
		boolean resultBool = false;
        logger.info("/register/gift -------> " + requestMap.toString());
        
        resultBool = giftService.registerGift(requestMap);
        
        return resultBool;
	}
	
	@RequestMapping(value = "/overlap/photo", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public int overlapPhoto(@RequestBody HashMap<String, Object> requestMap){
        logger.info("/overlap/photo -------> " + requestMap.toString());
        
        int result = giftService.overlapPhoto(requestMap);
        
        return result;
	}
	
//	
//	@RequestMapping(value = "/login", method = RequestMethod.POST , produces = "application/json")
//	@ResponseBody
//	public boolean login(@RequestBody HashMap<String, Object> requestMap){
//		boolean result = false;
//		
//		String user_id = (String) requestMap.get("user_id"); // 아이디   
//		String index = (String) requestMap.get("index"); // 아이디
//		
//		if(index != null) {
//			logger.info("회원정보수정 비밀번호 확인 ... user_id : " + user_id);
//		}else {
//			logger.info("로그인 ... user_id : " + user_id);
//		}
//        
//        
//        //SHA256으로 암호화된 비밀번호
//		try {
//			String cryptogram = sha256.encrypt((String) requestMap.get("user_password"));
//			requestMap.put("user_password", cryptogram);
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			logger.info("error ", e);
//			e.printStackTrace();
//		}
//		
//        result = loginService.login(requestMap);
//		
//		return result;
//	}
	
}

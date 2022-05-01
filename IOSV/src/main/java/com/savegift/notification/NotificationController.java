package com.savegift.notification;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.savegift.login.LoginDAO;
import com.savegift.login.LoginVO; 

@Controller
public class NotificationController {

	
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	@ResponseBody
	public LoginVO status(HttpServletRequest request){
		logger.info("notification/status..");
		String user_id = request.getParameter("user_id");
		logger.info("userid... " + user_id);
		
		LoginVO loginvo = notificationService.status(user_id);
		
		return loginvo;
	}
	
	@RequestMapping(value = "/notisetting", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean notiSetting(@RequestBody HashMap<String, Object> requestMap){
		boolean result = false;
		
		result = notificationService.notiSetting(requestMap);
		
        
		return result;
	}
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ResponseBody
	public String version(){
		logger.info("/version..");
		String result = notificationService.version();
		
		return result;
	}
	
	
	@RequestMapping(value = "/device/insert", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean deviceInsert(@RequestBody HashMap<String, Object> requestMap){
		boolean result = false;
		logger.info("notification/device/insert..");
//		String user_id = request.getParameter("device_model");
//		String user_id = request.getParameter("device_id");
//		String user_id = request.getParameter("push_token");
//		String user_id = request.getParameter("push_yn");
//		String user_id = request.getParameter("push30");
//		String user_id = request.getParameter("push7");
//		String user_id = request.getParameter("push1");
		
		result = notificationService.deviceInsert(requestMap);
		
		return true;
	}
	
//	@RequestMapping(value = "notification/status", method = RequestMethod.GET)
//	@ResponseBody
//	public List<LoginDAO> status(@RequestBody HashMap<String, Object> requestMap){
//		logger.info("notification/status..");
//		List<LoginDAO> list = null;
//		
//		String user_id = (String) requestMap.get("user_id"); // 아이디   
//		String index = (String) requestMap.get("index"); // 아이디
//		
//		return list;
//	}
	


}

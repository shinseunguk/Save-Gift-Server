package com.savegift.notification;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;


import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.savegift.giftcon.GiftService;
import com.savegift.giftcon.GiftUserDeviceVO;
import com.savegift.giftcon.GiftVO;
import com.savegift.login.LoginDAO;
import com.savegift.login.LoginVO; 


@Controller
public class NotificationController {

	
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	GiftService giftService;
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	@ResponseBody
	public LoginVO status(HttpServletRequest request){
		logger.info("notification/status..");
		String user_id = request.getParameter("user_id");
		logger.info("userid... " + user_id);
		
		LoginVO loginvo = notificationService.status(user_id);
		
		return loginvo;
	}
	
	@RequestMapping(value = "/status2", method = RequestMethod.GET)
	@ResponseBody
	public NotificationVO status2(HttpServletRequest request){
		logger.info("notification/status..");
		String device_id = request.getParameter("device_id");
		logger.info("device_id... " + device_id);
		
		NotificationVO notificationVO = notificationService.status2(device_id);
		
		return notificationVO;
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
	
	@RequestMapping(value="/fcmTest", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public boolean pushSend(HttpServletRequest request) throws Exception {
		return notificationService.sendPush();
	}
	
	@RequestMapping(value = "/push/list", method = RequestMethod.GET)
	@ResponseBody
	public List<GiftUserDeviceVO> getPushList(){
		Date date = new Date();
		List<GiftUserDeviceVO> list = new ArrayList<GiftUserDeviceVO>();
		List<GiftUserDeviceVO> list30 = new ArrayList<GiftUserDeviceVO>();
		List<GiftUserDeviceVO> list7 = new ArrayList<GiftUserDeviceVO>();
		List<GiftUserDeviceVO> list1 = new ArrayList<GiftUserDeviceVO>();
		
		Date[] arr30 = null;
		Date[] arr7 = null;
		Date[] arr1 = null;
		
		list = giftService.getPushList(date);
		
		for(int i = 0 ; i < list.size() ; i++) {
			if(list.get(i).getPush30() == 1) {
				list30.add(list.get(i));
			}else if (list.get(i).getPush7() == 1) {
				list7.add(list.get(i));
			}else {
				list1.add(list.get(i));
			}
		}
		
		System.out.println("!!!!");
		for(int i = 0 ; i < list30.size(); i++) {
			System.out.print(list30.get(i).getSeq()+" ");
		}
		System.out.println("@@@@@");
		for(int i = 0 ; i < list7.size(); i++) {
			System.out.print(list7.get(i).getSeq()+" ");
		}
		System.out.println("########");
		for(int i = 0 ; i < list1.size(); i++) {
			System.out.print(list1.get(i).getSeq()+" ");
		}
		
		if(list30.size() != 0) {
			arr30 = new Date[list30.size()];
			for(int i = 0 ; i < list30.size(); i++) {
				arr30[i] = list30.get(i).getExpiration_period();
			}
			System.out.println("");
			for(int i = 0 ; i < arr30.length ; i++) {
				System.out.print(arr30[i]+" ");
			}
		}
		
		if(list7.size() != 0) {
			arr7 = new Date[list7.size()];
			for(int i = 0 ; i < list7.size(); i++) {
				arr7[i] = list7.get(i).getExpiration_period();
			}
			System.out.println("");
			for(int i = 0 ; i < arr7.length ; i++) {
				System.out.print(arr7[i]+" ");
			}
		}
		
		if(list1.size() != 0) {
			arr1 = new Date[list1.size()];
			for(int i = 0 ; i < list1.size(); i++) {
				arr1[i] = list1.get(i).getExpiration_period();
			}
			System.out.println("");
			for(int i = 0 ; i < arr1.length ; i++) {
				System.out.print(arr1[i]+" ");
			}
		}
		
		return list;
	}
	

}

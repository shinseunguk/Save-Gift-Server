package com.savegift.notification;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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
import org.springframework.scheduling.annotation.Scheduled;
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
	public void fcmTest(){
		notificationService.sendPush();
	}
	
//	@RequestMapping(value="/fcmTest", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
//	@ResponseBody
	@Scheduled(cron = "0 0 9 * * *")
	public void pushSend(){
		notificationService.sendPush();
	}
	
//	@RequestMapping(value = "/push/list", method = RequestMethod.GET)
//	@ResponseBody
//	public List<GiftUserDeviceVO> getPushList(){
//		Date date = new Date();
//		List<GiftUserDeviceVO> list = new ArrayList<GiftUserDeviceVO>();
//		List<GiftUserDeviceVO> list30 = new ArrayList<GiftUserDeviceVO>();
//		List<GiftUserDeviceVO> list7 = new ArrayList<GiftUserDeviceVO>();
//		List<GiftUserDeviceVO> list1 = new ArrayList<GiftUserDeviceVO>();
//		List<GiftUserDeviceVO> resultList = new ArrayList<GiftUserDeviceVO>();
//		
//		Date[] arr30 = null;
//		Date[] arr7 = null;
//		Date[] arr1 = null;
//		
//	      Calendar cal = Calendar.getInstance();
//	      Calendar cal2 = Calendar.getInstance();
//	      Calendar cal3 = Calendar.getInstance();
//	      
//	      cal.setTime(new Date());
//	      cal2.setTime(new Date());
//	      cal3.setTime(new Date());
//	      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//	      System.out.println("current: " + df.format(cal.getTime()));
//	
//	      cal.add(Calendar.MONTH, 1);
//	      cal2.add(Calendar.DATE, 7);
//	      cal3.add(Calendar.DATE, 1);
//	      
//	      Date mDate1 = cal.getTime();
//	      Date mDate2 = cal2.getTime();
//	      Date mDate3 = cal3.getTime();
//	      
//	      System.out.println("after: " + df.format(cal.getTime()));
//	      System.out.println("after: " + df.format(cal2.getTime()));
//	      System.out.println("after: " + df.format(cal3.getTime()));
//		
//		list = giftService.getPushList(date);
//		
//		for(int i = 0 ; i < list.size() ; i++) {
//			if(list.get(i).getPush30() == 1) {
//				list30.add(list.get(i));
//			}else if (list.get(i).getPush7() == 1) {
//				list7.add(list.get(i));
//			}else {
//				list1.add(list.get(i));
//			}
//		}
//		
////		System.out.println("!!!!");
////		for(int i = 0 ; i < list30.size(); i++) {
////			System.out.print(list30.get(i).getSeq()+" ");
////		}
////		System.out.println("@@@@@");
////		for(int i = 0 ; i < list7.size(); i++) {
////			System.out.print(list7.get(i).getSeq()+" ");
////		}
////		System.out.println("########");
////		for(int i = 0 ; i < list1.size(); i++) {
////			System.out.print(list1.get(i).getSeq()+" ");
////		}
//		
//		//https://www.delftstack.com/ko/howto/java/how-to-compare-two-dates-in-java/#java%25EC%2597%2590%25EC%2584%259C-%25EB%2591%2590-%25EB%2582%25A0%25EC%25A7%259C%25EB%25A5%25BC-%25EB%25B9%2584%25EA%25B5%2590%25ED%2595%2598%25EB%258A%2594before%25EB%25A9%2594%25EC%2586%258C%25EB%2593%259C
//		//date.before(date) 함수 이용해서 list 뽑아내기
//		if(list30.size() != 0) {
//			arr30 = new Date[list30.size()];
//			for(int i = 0 ; i < list30.size(); i++) {
//				arr30[i] = list30.get(i).getExpiration_period();
//				if(arr30[i].equals(mDate1)) {
////					logger.info("eqaul1 "+ list30.get(i).getExpiration_period());
//					resultList.add(list30.get(i));
//				}else if(arr30[i].before(mDate1)) {
////					logger.info("before1 "+ list30.get(i).getExpiration_period());
//					resultList.add(list30.get(i));
//				}
//			}
////			System.out.println("");
////			for(int i = 0 ; i < arr30.length ; i++) {
////				System.out.print(arr30[i]+" ");
////			}
//		}
//		
//		if(list7.size() != 0) {
//			arr7 = new Date[list7.size()];
//			for(int i = 0 ; i < list7.size(); i++) {
//				arr7[i] = list7.get(i).getExpiration_period();
//				if(arr7[i].equals(mDate2)) {
////					logger.info("eqaul2 "+ list7.get(i).getExpiration_period());
//					resultList.add(list7.get(i));
//				}else if(arr7[i].before(mDate2)) {
////					logger.info("before2 "+ list7.get(i).getExpiration_period());
//					resultList.add(list7.get(i));
//				}
//			}
////			System.out.println("");
////			for(int i = 0 ; i < arr7.length ; i++) {
////				System.out.print(arr7[i]+" ");
////			}
//		}
//		
//		if(list1.size() != 0) {
//			arr1 = new Date[list1.size()];
//			for(int i = 0 ; i < list1.size(); i++) {
//				arr1[i] = list1.get(i).getExpiration_period();
//				if(arr1[i].equals(mDate3)) {
////					logger.info("eqaul3 "+ list1.get(i).getExpiration_period());
//					resultList.add(list1.get(i));
//				}else if(arr1[i].before(mDate3)) {
////					logger.info("before3 "+ list1.get(i).getExpiration_period());
//					resultList.add(list1.get(i));
//				}
//			}
////			System.out.println("");
////			for(int i = 0 ; i < arr1.length ; i++) {
////				System.out.print(arr1[i]+" ");
////			}
//		}
//		
//		System.out.println("");
//		for(int i = 0 ; i < resultList.size() ; i++) {
//			System.out.print(resultList.get(i).getSeq()+"\n");
//		}
//		
//		return resultList;
//	}
	

}

package com.savegift.notification;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Repository;

import com.savegift.giftcon.GiftDAO;
import com.savegift.giftcon.GiftUserDeviceVO;
import com.savegift.login.LoginVO;

@Repository
@PropertySource("classpath:datasource.properties")
public class NotificationDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationDAO.class);
	
	@Autowired
    SqlSession mybatis;
	
	@Autowired
	GiftDAO giftDAO;
	
	@Value("${fcm.keyValue}")
	private String authKey;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	public boolean sendPush() {
		List<GiftUserDeviceVO> resultList = new ArrayList<GiftUserDeviceVO>();
		
		String[] registerIdsArr = null; 
		
		//FCM 발송 URL
		String FMCurl = "https://fcm.googleapis.com/fcm/send";
		
		String body = null;
		
		// 현재 날짜 구하기        
		Calendar cal = Calendar.getInstance();
		
		Calendar getToday = Calendar.getInstance();
		getToday.setTime(new Date()); //금일 날짜
		

		try{
			resultList = pushList();
			for(int i = 0 ; i < resultList.size() ; i ++) {
				//알림 발송
				URL url = new URL(FMCurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Authorization","key="+authKey);
				conn.setRequestProperty("Content-Type","application/json");
				
				
				//알림 발송용 Parameter 설정
				JSONObject json = new JSONObject();
				JSONArray clientArr = new JSONArray();
				
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				registerIdsArr = new String[resultList.size()];
			
				registerIdsArr[i] = resultList.get(i).getPush_token();
				
				String brand = resultList.get(i).getBrand();
				String productName = resultList.get(i).getProduct_name();
				
				String a_date = format.format(resultList.get(i).getExpiration_period());
				String b_date = format.format(getToday.getTime());
				
				Date a_parseDate = format.parse(a_date);
		        Date b_parseDate = format.parse(b_date);
		        
		        long resultTime = a_parseDate.getTime() - b_parseDate.getTime();
		        
//		        System.out.println("초 : "+resultTime / 1000);
//		        System.out.println("분 : "+resultTime / (60*1000));
//		        System.out.println("시 : "+resultTime / (60*60*1000));
//		        System.out.println("일 : "+resultTime / (24*60*60*1000));
				
				long dateDiff = resultTime / (24*60*60*1000);
				
				if(dateDiff != 0) {
					body = brand + " '"+productName + "'의 유효기간이 " + dateDiff + "일 남았습니다.";
				}else {
					body = brand + " '"+productName + "'의 유효기간이 오늘까지입니다.";
				}
			

				//단건의 경우 to, 다건인 경우 registration_ids		
				json.put("to", registerIdsArr[i]);
	//			json.put("registration_ids", registerIdsArr);
				JSONObject info = new JSONObject();
				info.put("title", "기프티콘 수첩");
				info.put("body", body);
				info.put("sound", "default");
				json.put("notification", info);
				
				
				//메시지 발송 처리
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(json.toString());
				wr.flush();
			
			
			
				//발송 결과
				OutputStream rostream = conn.getOutputStream();
			    InputStream ristream = conn.getInputStream();
	
			    final int length = 50000;
			    byte[] bytes = new byte[length];
			    int bytesRead = 0;
			    
			        
			    while ((bytesRead = ristream.read(bytes, 0, length)) > 0) {
			        
			    	String tmp = new String(bytes, "utf-8");
	
				//	out.print("{\"multicast_id\":5185012636408180766,\"success\":1,\"failure\":0,\"canonical_ids\":0,\"results\":[{\"message_id\":\"0:1484811869998813%b347b3eab347b3ea\"}]}");
					if(tmp.trim().length()>0){
						int beforeLen = tmp.length();
						int afterLen = 0;
						rostream.write(bytes, 0, bytesRead);
					}
			    }	
	
			    rostream.flush();        
			        
			    
			    if(wr != null){
			   		wr.close();	
			    }
			        
			    if(ristream != null){
			    	ristream.close();
			    }
			        
			    if(rostream != null){
			    	rostream.close();        	
			    }
			}
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("FCM SEND error \n" + e);
			return false;
		}
	}
	
	public boolean friendRequestPush(HashMap<String, Object> requestMap) {
		List<UserDeviceVO> list = null;
		List<UserDeviceVO> list1 = null;
		String[] registerIdsArr = null; 
		
		//FCM 발송 URL
		String FMCurl = "https://fcm.googleapis.com/fcm/send";
		String body = null;
		
		String user_id = (String) requestMap.get("user_id");
		String friend = (String) requestMap.get("friend");
		String index = (String) requestMap.get("index");
		
		if(index.equals("friendRequest")) {
			list = mybatis.selectList("NotificationMapper.friendRequestPush", friend);
			body = user_id+"님이 친구를 요청했습니다.\n앱내 친구 TAB에서 확인해주세요.";
		}else if(index.equals("friendPresent")) {
			list = mybatis.selectList("NotificationMapper.presentPush", requestMap);
			body = "선물이 도착했습니다. 선물함 TAB에서 선물을 확인해주세요.";
		}
		
		
		try{
			for(int i = 0 ; i < list.size() ; i ++) {
				//알림 발송
				URL url = new URL(FMCurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Authorization","key="+authKey);
				conn.setRequestProperty("Content-Type","application/json");
				
				
				//알림 발송용 Parameter 설정
				JSONObject json = new JSONObject();
				
//				body = user_id+"님이 친구를 요청했습니다.\n앱내 친구TAB에서 확인해주세요.";
				
				//단건의 경우 to, 다건인 경우 registration_ids		
				json.put("to", list.get(i).getPush_token());
				JSONObject info = new JSONObject();
				info.put("title", "기프티콘 수첩");
				info.put("body", body);
				info.put("sound", "default");
				json.put("notification", info);
				
				
				//메시지 발송 처리
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(json.toString());
				wr.flush();
				
				
				
				//발송 결과
				OutputStream rostream = conn.getOutputStream();
				InputStream ristream = conn.getInputStream();
				
				final int length = 50000;
				byte[] bytes = new byte[length];
				int bytesRead = 0;
				
				
				while ((bytesRead = ristream.read(bytes, 0, length)) > 0) {
					
					String tmp = new String(bytes, "utf-8");
					
					//	out.print("{\"multicast_id\":5185012636408180766,\"success\":1,\"failure\":0,\"canonical_ids\":0,\"results\":[{\"message_id\":\"0:1484811869998813%b347b3eab347b3ea\"}]}");
					if(tmp.trim().length()>0){
						int beforeLen = tmp.length();
						int afterLen = 0;
						rostream.write(bytes, 0, bytesRead);
					}
				}	
				
				rostream.flush();        
				
				
				if(wr != null){
					wr.close();	
				}
				
				if(ristream != null){
					ristream.close();
				}
				
				if(rostream != null){
					rostream.close();        	
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("FCM SEND error \n" + e);
			return false;
		}
	}
	
	public List<GiftUserDeviceVO> pushList(){

		Date date = new Date();
		List<GiftUserDeviceVO> list = new ArrayList<GiftUserDeviceVO>();
		List<GiftUserDeviceVO> list30 = new ArrayList<GiftUserDeviceVO>();
		List<GiftUserDeviceVO> list7 = new ArrayList<GiftUserDeviceVO>();
		List<GiftUserDeviceVO> list1 = new ArrayList<GiftUserDeviceVO>();
		List<GiftUserDeviceVO> resultList = new ArrayList<GiftUserDeviceVO>();
		
		Date[] arr30 = null;
		Date[] arr7 = null;
		Date[] arr1 = null;
		
	      Calendar cal = Calendar.getInstance();
	      Calendar cal2 = Calendar.getInstance();
	      Calendar cal3 = Calendar.getInstance();
	      
	      cal.setTime(new Date());
	      cal2.setTime(new Date());
	      cal3.setTime(new Date());
	      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	      cal.add(Calendar.MONTH, 1);
	      cal2.add(Calendar.DATE, 7);
	      cal3.add(Calendar.DATE, 1);
	      
	      Date mDate1 = cal.getTime();
	      Date mDate2 = cal2.getTime();
	      Date mDate3 = cal3.getTime();
	      
		list = giftDAO.getPushList(date);
		
		for(int i = 0 ; i < list.size() ; i++) {
			if(list.get(i).getPush30() == 1) {
				list30.add(list.get(i));
			}else if (list.get(i).getPush7() == 1) {
				list7.add(list.get(i));
			}else {
				list1.add(list.get(i));
			}
		}
		
		
		//https://www.delftstack.com/ko/howto/java/how-to-compare-two-dates-in-java/#java%25EC%2597%2590%25EC%2584%259C-%25EB%2591%2590-%25EB%2582%25A0%25EC%25A7%259C%25EB%25A5%25BC-%25EB%25B9%2584%25EA%25B5%2590%25ED%2595%2598%25EB%258A%2594before%25EB%25A9%2594%25EC%2586%258C%25EB%2593%259C
		//date.before(date) 함수 이용해서 list 뽑아내기
		if(list30.size() != 0) {
			arr30 = new Date[list30.size()];
			for(int i = 0 ; i < list30.size(); i++) {
				arr30[i] = list30.get(i).getExpiration_period();
				if(arr30[i].equals(mDate1)) {
					resultList.add(list30.get(i));
				}else if(arr30[i].before(mDate1)) {
					resultList.add(list30.get(i));
				}
			}
		}
		
		if(list7.size() != 0) {
			arr7 = new Date[list7.size()];
			for(int i = 0 ; i < list7.size(); i++) {
				arr7[i] = list7.get(i).getExpiration_period();
				if(arr7[i].equals(mDate2)) {
					resultList.add(list7.get(i));
				}else if(arr7[i].before(mDate2)) {
					resultList.add(list7.get(i));
				}
			}
		}
		
		if(list1.size() != 0) {
			arr1 = new Date[list1.size()];
			for(int i = 0 ; i < list1.size(); i++) {
				arr1[i] = list1.get(i).getExpiration_period();
				if(arr1[i].equals(mDate3)) {
					resultList.add(list1.get(i));
				}else if(arr1[i].before(mDate3)) {
					resultList.add(list1.get(i));
				}
			}
		}
		
		return resultList;
	}
	
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
			logger.info("DB INSERT ["+device_id+"] --> token["+push_token+"]");
			int result = mybatis.insert("NotificationMapper.userDeviceInsert", requestMap);
			
			if(result == 1) {
				resultBool = true;	
			}else {
				resultBool = false;
			}
		}else {
			logger.info("DB UPDATE ["+device_id+"] --> token["+push_token+"]");
			int result = mybatis.update("NotificationMapper.userDeviceUpdate", requestMap);
			
			if(result == 1) {
				resultBool = true;	
			}else {
				resultBool = false;
			}
		}
		
		return resultBool;
	}
	
}

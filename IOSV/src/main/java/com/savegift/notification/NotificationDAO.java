package com.savegift.notification;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
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
	
	public boolean sendPush() {
		String[] registerIdsArr = new String[1000]; 
		//FCM apikey authkey 은닉
		String authKey = "AAAAmjgVFQY:APA91bHnP115Fl_CR0RcJPpW4l2x7lnluAhlAzr-_c-eFySEwuA2-jYIEFdQThmgReRGP9P-4Ek-0mysGT1NEcQgK5T9bGuQcMn3qqskvLgVqkEVr3WdXqECjQGrjmoZM9c0WZyz4utf";
		
		//FCM 발송 URL
		String FMCurl = "https://fcm.googleapis.com/fcm/send";

		try{
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
			
//			//다건 발송을 위해 Client 목록 작성
//			StringTokenizer stDevice = new StringTokenizer(deviceId.trim(), ";");
//            // 넘겨온 핸드폰 번호로 DB에서 Token값 뽑아오는 작업
//			String deviceArr = "";
//			while(stDevice.hasMoreElements()){
//            	// 단말기 Token값 넣어주기
//				clientArr.add((String)stDevice.nextElement());
//			}
			
			registerIdsArr[0] = "cGcbj2iVd0QtksHx7p2kfl:APA91bF6RdHQImUxkOCunrbAUjdiZIkvKLtiklgIhM6dnRCpcQlJKo_EAWhxaozXcagpaElQXslZj3DjIFL0Bc8CbyyogyoYeE5fwk6eIi6WSmk99QPHbETRMKBFQzpZ7xdlFQKmDe6j";
			registerIdsArr[1] = "cGcbj2iVd0QtksHx7p2kfl:APA91bF6RdHQImUxkOCunrbAUjdiZIkvKLtiklgIhM6dnRCpcQlJKo_EAWhxaozXcagpaElQXslZj3DjIFL0Bc8CbyyogyoYeE5fwk6eIi6WSmk99QPHbETRMKBFQzpZ7xdlFQKmDe6j";
			

			//단건의 경우 to, 다건인 경우 registration_ids		
			json.put("to", "cGcbj2iVd0QtksHx7p2kfl:APA91bF6RdHQImUxkOCunrbAUjdiZIkvKLtiklgIhM6dnRCpcQlJKo_EAWhxaozXcagpaElQXslZj3DjIFL0Bc8CbyyogyoYeE5fwk6eIi6WSmk99QPHbETRMKBFQzpZ7xdlFQKmDe6j");
//			json.put("registration_ids", registerIdsArr);
			JSONObject info = new JSONObject();
			info.put("title", "기프티콘 수첩");
			info.put("body", "content");
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
			
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("error ..... \n" + e);
			return false;
		}
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

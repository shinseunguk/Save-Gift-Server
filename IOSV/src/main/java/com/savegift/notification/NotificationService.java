package com.savegift.notification;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.savegift.giftcon.GiftService;
import com.savegift.giftcon.GiftUserDeviceVO;
import com.savegift.login.LoginVO;

@Service
public class NotificationService {
	
	@Autowired
	NotificationDAO notificationDAO;
	
	@Autowired
	GiftService giftService;

	public LoginVO status(String user_id){
		return notificationDAO.status(user_id);
	}
	public NotificationVO status2(String device_id){
		return notificationDAO.status2(device_id);
	}
	public boolean notiSetting(HashMap<String, Object> requestMap){
		return notificationDAO.notiSetting(requestMap);
	}
	public String version() {
		return notificationDAO.version();
	}
	public boolean deviceInsert(HashMap<String, Object> requestMap){
		return notificationDAO.deviceInsert(requestMap);
	}
	public boolean sendPush(){
		return notificationDAO.sendPush();
	}
	public boolean friendRequestPush(HashMap<String, Object> requestMap){
		return notificationDAO.friendRequestPush(requestMap);
	}
	
}

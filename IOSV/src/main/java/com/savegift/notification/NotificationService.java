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
	NotificationDAO notificaitionDAO;
	
	@Autowired
	GiftService giftService;

	public LoginVO status(String user_id){
		return notificaitionDAO.status(user_id);
	}
	
	public NotificationVO status2(String device_id){
		return notificaitionDAO.status2(device_id);
	}
	
	public boolean notiSetting(HashMap<String, Object> requestMap){
		return notificaitionDAO.notiSetting(requestMap);
	}
	
	public String version() {
		return notificaitionDAO.version();
	}
	
	public boolean deviceInsert(HashMap<String, Object> requestMap){
		return notificaitionDAO.deviceInsert(requestMap);
	}
	public boolean sendPush(){
		return notificaitionDAO.sendPush();
	}
}

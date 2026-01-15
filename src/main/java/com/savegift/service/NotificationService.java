package com.savegift.service;


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

import com.savegift.repository.NotificationRepository;
import com.savegift.domain.GiftUserDevice;
import com.savegift.domain.Notification;
import com.savegift.domain.User;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	GiftService giftService;

	public User status(String user_id){
		return notificationRepository.status(user_id);
	}
	public Notification status2(String device_id){
		return notificationRepository.status2(device_id);
	}
	public boolean notiSetting(HashMap<String, Object> requestMap){
		return notificationRepository.notiSetting(requestMap);
	}
	public String version() {
		return notificationRepository.version();
	}
	public boolean deviceInsert(HashMap<String, Object> requestMap){
		return notificationRepository.deviceInsert(requestMap);
	}
	public boolean sendPush(){
		return notificationRepository.sendPush();
	}
	public boolean friendRequestPush(HashMap<String, Object> requestMap){
		return notificationRepository.friendRequestPush(requestMap);
	}
	
}

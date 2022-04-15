package com.savegift.notification;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.savegift.login.LoginVO;

@Service
public class NotificationService {
	
	@Autowired
	NotificationDAO notificaitionDAO;

	public LoginVO status(String user_id){
		return notificaitionDAO.status(user_id);
	}
	
	public boolean notiSetting(HashMap<String, Object> requestMap){
		return notificaitionDAO.notiSetting(requestMap);
	}
	
	public String version() {
		return notificaitionDAO.version();
	}
}

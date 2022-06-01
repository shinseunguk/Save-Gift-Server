package com.savegift.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LoginService")
public class LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	LoginDAO loginDAO;
	
	//회원가입 
	public int register(HashMap<String, Object> requestMap) {
		return loginDAO.register(requestMap);
	}
	public boolean duplicationid(String user_id) {
		return loginDAO.duplicationid(user_id);
	}
	public boolean login(HashMap<String, Object> requestMap) {
		return loginDAO.login(requestMap);
	}
	public LoginVO findEmail(String user_id) {
		return loginDAO.findEmail(user_id);
	}
	public LoginVO findPhone(String phone_number) {
		return loginDAO.findPhone(phone_number);
	}
	public int addFriend(String user_id, String friend, String name) {
		return loginDAO.addFriend(user_id, friend, name);
	}
	public int deleteFriendWait(String user_id, String friend, String index) {
		return loginDAO.deleteFriendWait(user_id, friend, index);
	}
	public int deleteFriend(String user_id, String friend) {
		return loginDAO.deleteFriend(user_id, friend);
	}
	public int waitFriend(String user_id, String friend, String name) {
		return loginDAO.waitFriend(user_id, friend, name);
	}
	public String statusFriend(String user_id, String friend, String name) {
		return loginDAO.statusFriend(user_id, friend, name);
	}
	public List<FriendVO> getRequestFriend(String user_id) {
		return loginDAO.getRequestFriend(user_id);
	}
	public List<FriendVO> getRequestedFriend(String user_id) {
		return loginDAO.getRequestedFriend(user_id);
	}
	public FriendVO getFriend(String user_id) {
		return loginDAO.getFriend(user_id);
	}
	public boolean secession(String user_id) {
		return loginDAO.secession(user_id);
	}
	public LoginVO userInfo(String user_id) {
		return loginDAO.userInfo(user_id);
	}
	public boolean userinfoName(HashMap<String, Object> requestMap) {
		return loginDAO.userinfoName(requestMap);
	}
	public boolean userinfoPassword(HashMap<String, Object> requestMap) {
		return loginDAO.userinfoPassword(requestMap);
	}
}
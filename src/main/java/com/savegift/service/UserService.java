package com.savegift.service;

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

@Service("UserService")
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	//회원가입 
	public int register(HashMap<String, Object> requestMap) {
		return userRepository.register(requestMap);
	}
	public boolean duplicationid(String user_id) {
		return userRepository.duplicationid(user_id);
	}
	public boolean checkSocial(String user_id) {
		return userRepository.checkSocial(user_id);
	}
	public boolean login(HashMap<String, Object> requestMap) {
		return userRepository.login(requestMap);
	}
	public boolean socialLogin(HashMap<String, Object> requestMap) {
		return userRepository.socialLogin(requestMap);
	}
	public User findEmail(String user_id) {
		return userRepository.findEmail(user_id);
	}
	public User findPhone(String phone_number) {
		return userRepository.findPhone(phone_number);
	}
	public int addFriend(String user_id, String friend, String name) {
		return userRepository.addFriend(user_id, friend, name);
	}
	public int deleteFriendWait(String user_id, String friend, String index) {
		return userRepository.deleteFriendWait(user_id, friend, index);
	}
	public int deleteFriend(String user_id, String friend) {
		return userRepository.deleteFriend(user_id, friend);
	}
	public int waitFriend(String user_id, String friend, String name) {
		return userRepository.waitFriend(user_id, friend, name);
	}
	public String statusFriend(String user_id, String friend, String name) {
		return userRepository.statusFriend(user_id, friend, name);
	}
	public List<Friend> getRequestFriend(String user_id) {
		return userRepository.getRequestFriend(user_id);
	}
	public List<Friend> getRequestedFriend(String user_id) {
		return userRepository.getRequestedFriend(user_id);
	}
	public Friend getFriend(String user_id) {
		return userRepository.getFriend(user_id);
	}
	public boolean secession(String user_id) {
		return userRepository.secession(user_id);
	}
	public User userInfo(String user_id) {
		return userRepository.userInfo(user_id);
	}
	public boolean userinfoName(HashMap<String, Object> requestMap) {
		return userRepository.userinfoName(requestMap);
	}
	public boolean userinfoPassword(HashMap<String, Object> requestMap) {
		return userRepository.userinfoPassword(requestMap);
	}
	public boolean checkEmailInfo(HashMap<String, Object> requestMap) {
		return userRepository.checkEmailInfo(requestMap);
	}
	public boolean checkEmail(HashMap<String, Object> requestMap) {
		return userRepository.checkEmail(requestMap);
	}
	public boolean checkNamePhone(HashMap<String, Object> requestMap) {
		return userRepository.checkNamePhone(requestMap);
	}
	public String findId(HashMap<String, Object> requestMap) {
		return userRepository.findId(requestMap);
	}
	public boolean certNumberDB(String user_id, String cert_number) {
		return userRepository.certNumberDB(user_id, cert_number);
	}
	public boolean deviceDelete(HashMap<String, Object> requestMap) {
		return userRepository.deviceDelete(requestMap);
	}
}
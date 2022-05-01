package com.savegift.login;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Handles requests for the application home page. git test 
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
    SHA256 sha256 = new SHA256();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is!!!!!!!!!!!!!! {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	@RequestMapping(value = "/duplicationid", method = RequestMethod.GET)
	@ResponseBody
	public boolean duplicationid(HttpServletRequest request){
		boolean result;
		String user_id = request.getParameter("user_id");
		logger.info("/duplicationid .. user_id : " + user_id);
		result = loginService.duplicationid(user_id);
		
		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean register(@RequestBody HashMap<String, Object> requestMap){
		boolean resultBool = false;
		
		logger.info("/post....");
		
		String userInfo = requestMap.toString();
		
//		개발완료후 로그 지울것
		logger.info("userInfo >>>>" + userInfo);
		
		String user_id = (String) requestMap.get("user_id"); // 아이디   
        String password = (String) requestMap.get("user_password"); // 패스워드
        String name = (String) requestMap.get("name"); // 이름
        String phone_number =(String)  requestMap.get("phone_number"); // 휴대폰 번호
        
        //SHA256으로 암호화된 비밀번호
		try {
			String cryptogram = sha256.encrypt((String) requestMap.get("user_password"));
			requestMap.put("user_password", cryptogram);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			logger.info("error ", e);
			e.printStackTrace();
		}
        
        logger.info("userInfo2 ####" + requestMap.toString());
        int result = loginService.register(requestMap);
        
        if (result == 1){ //insert 성공(회원가입 성공)
        	logger.info("회원가입 성공");
        	resultBool = true;
        } else {
        	logger.info("회원가입 실패");
        }
        return resultBool;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean login(@RequestBody HashMap<String, Object> requestMap){
		boolean result = false;
		
		String user_id = (String) requestMap.get("user_id"); // 아이디   
		String index = (String) requestMap.get("index"); // 아이디
		
		if(index != null) {
			logger.info("회원정보수정 비밀번호 확인 ... user_id : " + user_id);
		}else {
			logger.info("로그인 ... user_id : " + user_id);
		}
        
        
        //SHA256으로 암호화된 비밀번호
		try {
			String cryptogram = sha256.encrypt((String) requestMap.get("user_password"));
			requestMap.put("user_password", cryptogram);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			logger.info("error ", e);
			e.printStackTrace();
		}
		
        result = loginService.login(requestMap);
		
		return result;
	}
	
	@RequestMapping(value = "/findemail", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public LoginVO findEmail(@RequestBody HashMap<String, Object> requestMap){
		logger.info("user_id .. "+ (String) requestMap.get("user_id"));
		
		String user_id = (String) requestMap.get("user_id"); // 아이디
		
		if(user_id != null) {
			logger.info("친구추가 아이디 ... user_id : " + user_id);
		}
		LoginVO loginvo = loginService.findEmail(user_id);
		
		return loginvo;
	}
	
	@RequestMapping(value = "/findphone", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public LoginVO findPhone(@RequestBody HashMap<String, Object> requestMap){
		logger.info("phone_number .. "+ (String) requestMap.get("phone_number"));
		
		String phone_number_origin = (String) requestMap.get("phone_number"); // 아이디
		String phone_number = null;
		if(phone_number_origin != null) {
			phone_number = phone_number_origin.replace("-", "");
		}
		
		
		if(phone_number != null) {
			logger.info("친구추가 핸드폰 번호 ... phone_number : " + phone_number);
		}
		LoginVO loginvo = loginService.findPhone(phone_number);
		
		return loginvo;
	}
	
	@RequestMapping(value = "/addFriend", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public int addFriend(@RequestBody HashMap<String, Object> requestMap){
		logger.info("/addFriend user_id .. "+ (String) requestMap.get("user_id"));
		logger.info("/addFriend friend and name .. "+ (String) requestMap.get("userIdName"));
		
		String userIdName = (String) requestMap.get("userIdName");
		String friend = null;
		String name = null;
		String nameOrigin = null;
		
		if(userIdName != null) {
			int idx = userIdName.indexOf("("); 
			nameOrigin = userIdName.substring(idx+1);
			friend = userIdName.substring(0, idx);
			name = nameOrigin.substring(0, nameOrigin.length()-1);
		}
		
        
        String user_id = (String) requestMap.get("user_id");
        friend = (String) requestMap.get("friend");
        
        logger.info("user_id ... "+ user_id);
        logger.info("friend ... "+ friend);
        logger.info("name ... "+ name);

        int result = loginService.addFriend(user_id, friend, name);
        logger.info("/addFriend... "+ result);
        
        return result;
	}
	
	@RequestMapping(value = "/deleteFriendWait", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public int deleteFriendWait(@RequestBody HashMap<String, Object> requestMap){
		logger.info("/deleteFriendWait user_id .. "+ (String) requestMap.get("user_id"));
		logger.info("/deleteFriendWait friend .. "+ (String) requestMap.get("friend"));
		logger.info("/deleteFriendWait index .. "+ (String) requestMap.get("index"));
		
        
        String user_id = (String) requestMap.get("user_id");
        String friend = (String) requestMap.get("friend");
        String index = null;
        index = (String) requestMap.get("index");
        
        logger.info("user_id ... "+ user_id);
        logger.info("friend ... "+ friend);

        int result = loginService.deleteFriendWait(user_id, friend, index);
        logger.info("/deleteFriendWait... "+ result);
        
        return result;
	}
	
	@RequestMapping(value = "/waitFriend", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public int waitFriend(@RequestBody HashMap<String, Object> requestMap){
		logger.info("/waitFriend user_id .. "+ (String) requestMap.get("user_id"));
		logger.info("/waitFriend friend and name .. "+ (String) requestMap.get("userIdName"));
		
		String userIdName = (String) requestMap.get("userIdName");
		
        int idx = userIdName.indexOf("("); 
        
        String nameOrigin = userIdName.substring(idx+1);
        
        String user_id = (String) requestMap.get("user_id");
        String friend = userIdName.substring(0, idx);
        String name = nameOrigin.substring(0, nameOrigin.length()-1);
        
        logger.info("user_id ... "+ user_id);
        logger.info("friend ... "+ friend);
        logger.info("name ... "+ name);

        int result = loginService.waitFriend(user_id, friend, name);
        
        logger.info("/waitFriend result -------> " + result);
        
        return result;
	}
	
	@RequestMapping(value = "/statusFriend", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public String statusFriend(@RequestBody HashMap<String, Object> requestMap){
		logger.info("/statusFriend user_id .. "+ (String) requestMap.get("user_id"));
		logger.info("/statusFriend friend and name .. "+ (String) requestMap.get("userIdName"));
		
		String userIdName = (String) requestMap.get("userIdName");
		
		int idx = userIdName.indexOf("("); 
		
		String nameOrigin = userIdName.substring(idx+1);
		
		String user_id = (String) requestMap.get("user_id");
		String friend = userIdName.substring(0, idx);
		String name = nameOrigin.substring(0, nameOrigin.length()-1);
		
		logger.info("user_id ... "+ user_id);
		logger.info("friend ... "+ friend);
		logger.info("name ... "+ name);
		
		String result = loginService.statusFriend(user_id, friend, name);
		
		logger.info("/statusFriend "+ result);
		return result;
	}
	
	@RequestMapping(value = "/getRequestFriend", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public String getRequestFriend(@RequestBody HashMap<String, Object> requestMap){
		List<FriendVO> list = null;
		List<FriendVO> list2 = null;
		String returnString = "";
		logger.info("/getRequestFriend user_id .. "+ (String) requestMap.get("user_id"));
		
		String user_id = (String) requestMap.get("user_id");
		
		//내가 신청한 친구
		list = loginService.getRequestFriend(user_id);
		//내가 신청받은 친구
		list2 = loginService.getRequestedFriend(user_id);
		logger.info("list "+ list);
		logger.info("list2 "+ list2);
		
		if(list.size() == 0 && list2.size() == 0){
			logger.info("/getRequestFriend return ---> null");
			return null;
		} else{
			for(int i = 0 ; i < list.size() ; i++) {
				if(i != list.size()-1) {
					logger.info("0");
					returnString += list.get(i).getFriend()+"&";	
				} else{
					if(list2.size() != 0) {
						logger.info("1");
						returnString += list.get(i).getFriend()+"&";
					}else {
						logger.info("2");
						returnString += list.get(i).getFriend()+"#";
					}
				}
			}
			
			for(int i = 0 ; i < list2.size() ; i++) {
				if(i != list2.size()-1) {
					logger.info("3");
					returnString += list2.get(i).getUser_id()+"&";
				} else{
					logger.info("4");
					returnString += list2.get(i).getUser_id()+"#";
				}
			}
			
			
			for(int i = 0 ; i < list.size() ; i++) {
				if(i != list.size()-1) {
					logger.info("5");
					returnString += list.get(i).getStatus()+"&";	
				} else{
					if(list2.size() != 0) {
						logger.info("6");
						returnString += list.get(i).getStatus()+"&";
					}else {
						logger.info("7");
						returnString += list.get(i).getStatus();
					}
				}
			}
			
			for(int i = 0 ; i < list2.size() ; i++) {
				if(i != list2.size()-1) {
					returnString += "P&";	
				} else{
					returnString += "P";
				}
			}
			
			logger.info("returnString.. " + returnString);
		}
		
		
		return returnString;
	}
	
	@RequestMapping(value = "/getFriend", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public FriendVO getFriend(@RequestBody HashMap<String, Object> requestMap){
		FriendVO friendVO = null;
		logger.info("/getFriend user_id .. "+ (String) requestMap.get("user_id"));
		
		String user_id = (String) requestMap.get("user_id");
		
		friendVO = loginService.getFriend(user_id);
		
		if(friendVO != null){
			logger.info("/getFriend return ---> " + friendVO.getFriend());	
		}
		return friendVO;
	}
	
	
	@RequestMapping(value = "/secession", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean secession(@RequestBody HashMap<String, Object> requestMap){
		boolean result = false;
		
		String user_id = (String) requestMap.get("user_id");
		result = loginService.secession(user_id);
		
		return true;
	}
}

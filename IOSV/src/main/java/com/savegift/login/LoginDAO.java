package com.savegift.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.savegift.giftcon.GiftVO;
import com.savegift.notification.NotificationVO;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import spring.service.EmailVO;
import spring.service.SmsVO;

@Repository
public class LoginDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginDAO.class);
	
	@Autowired
    SqlSession mybatis;
	
	public int register(HashMap<String, Object> requestMap) {
		int result = mybatis.insert("LoginMapper.register", requestMap);
		
		return result;
	}
	
	public boolean login(HashMap<String, Object> requestMap) {
		boolean result = false;
		
		String userPasswordInput = (String) requestMap.get("user_password");
		
		//where절 id가 널이 아닐때 -> 그 id로 비밀번호 불러옴 -> input 비밀번호랑 db 비밀번호랑 비교
		LoginVO loginVO = mybatis.selectOne("LoginMapper.duplicationid", requestMap);
		if(loginVO != null) { // 아이디는 있음
			String userPasswordDB = mybatis.selectOne("LoginMapper.passwordCrossCheck", requestMap); //DB 비밀번호 불러옴
			if(userPasswordDB != null) {// DB와 사용자의 입력 값을 비교함 
				if(userPasswordDB.equals(userPasswordInput)) {
					result = true;
				}else {
					result = false;
				}
			}
		}
		
		return result;
	}
	
	public boolean duplicationid(String user_id) {
		boolean result = false;
		LoginVO loginVO = mybatis.selectOne("LoginMapper.duplicationid", user_id);
		
		if(loginVO != null) {
			result = true;
		}
		return result;
	}
	
	public LoginVO findEmail(String user_id) {
		//where절 id가 널이 아닐때 -> 그 id로 비밀번호 불러옴 -> input 비밀번호랑 db 비밀번호랑 비교
		LoginVO loginVO = mybatis.selectOne("LoginMapper.findemail", user_id);
				
		if(loginVO != null) {
			// 아이디는 있음
			logger.info("findEmail ... " + loginVO.getUser_id());
			logger.info("findEmail ... " + loginVO.getPhone_number());
		}
		
		return loginVO;
	}
	
	public LoginVO findPhone(String phone_number) {
		//where절 id가 널이 아닐때 -> 그 id로 비밀번호 불러옴 -> input 비밀번호랑 db 비밀번호랑 비교
		LoginVO loginVO = mybatis.selectOne("LoginMapper.findphone", phone_number);
		
		if(loginVO != null) {
			// 아이디는 있음
			logger.info("findPhone ... " + loginVO.getUser_id());
			logger.info("findPhone ... " + loginVO.getPhone_number());
		}
		
		return loginVO;
	}
	
	public int addFriend(String user_id, String friend, String name) {
		int result;
		
		HashMap<String, String> map = new HashMap<String, String>();
		String userIdOrigin = user_id;
		String friendOrigin = friend;
		map.put("user_id", user_id); // krdut1@gmail.com
		map.put("friend", friend); // samdori96@nate.com
		
		result = mybatis.delete("LoginMapper.deleteFriendWait", map);
		
		if(result == 1) {
			// friend 테이블에 user_id 값이 있는지 확인 없으면 insert 있으면 update
			FriendVO friendVO = mybatis.selectOne("LoginMapper.addFriend", user_id); // 본인이 이미 추가한 사용자
			
			if(friendVO != null) {
				String getFriend = friendVO.getFriend();
				friend = getFriend+"&"+friend;
				
				logger.info("friend..1 " + friend);
				map.put("friend", friend);
				
				mybatis.update("LoginMapper.addFriendUpdate", map);
			}else {
				int insertResult = mybatis.insert("LoginMapper.addFriendInsert", map);
				if(insertResult == 1) {
					logger.info("친구 등록완료.. "+ map.toString());
				}else {
					logger.info("친구 등록실패.. "+ map.toString());
				}
			}
			
			FriendVO friendVO2 = mybatis.selectOne("LoginMapper.addFriendOpponent", friendOrigin);// 상대방 update 및 insert
			if(friendVO2 != null) {
				//update
				String getFriend = friendVO2.getFriend();
				friend = getFriend+"&"+user_id;
				
				logger.info("friend..2 " + friend);
				map.put("user_id", friendOrigin);
				map.put("friend", friend);
				
				mybatis.update("LoginMapper.addFriendUpdate", map);
			}else {
				//insert
				map.put("user_id", friendOrigin);
				map.put("friend", userIdOrigin);
				
				int insertResult = mybatis.insert("LoginMapper.addFriendInsert", map);
				if(insertResult == 1) {
					logger.info("친구 등록완료.. "+ map.toString());
				}else {
					logger.info("친구 등록실패.. "+ map.toString());
				}
			}
		}

		return result;
	}
	
	public int deleteFriendWait(String user_id, String friend, String index) {
		int result;
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("friend", friend);
		
		if(index == null) {
			result = mybatis.delete("LoginMapper.deleteFriendWait", map);
		}else {
			result = mybatis.delete("LoginMapper.deleteFriendWaitMe", map);
		}
		
		return result;
	}	

	public int deleteFriend(String user_id, String friend) {
		int result = 0;
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id); // 로그인 사용자 
		map.put("friend", friend); // 친구삭제되는 사용자
		
		// 로그인 사용자에서 해당 친구 삭제
		FriendVO friendVO = mybatis.selectOne("LoginMapper.addFriend", map);
		logger.info("deleteFriend friendVO " + friendVO.getFriend());
		String deleteId = friendVO.getFriend();
		
		deleteId = deleteId.replace(friend, "");
		logger.info("deleteId First => "+ deleteId);
		if(deleteId.length() != 0) {
			if(deleteId.charAt(0) == '&') {
				deleteId = deleteId.substring(1,deleteId.length());
			}else if (deleteId.charAt(deleteId.length()-1) == '&') {
				deleteId = deleteId.substring(0,deleteId.length()-1);
			}else {
				deleteId = deleteId.replace("&&", "&");
			}
			
			map.put("friend", deleteId);
			result = mybatis.update("LoginMapper.addFriendUpdate", map);
		}else {
			mybatis.delete("LoginMapper.secessionFriend", user_id); // 친구 삭제
			result = 1;
		}
		
		map.put("user_id", friend);
		map.put("friend", user_id); // 친구삭제되는 사용자
		
		List<FriendVO> list = mybatis.selectList("LoginMapper.addFriend", map);
			for(int i = 0; i < list.size() ; i++) {
				deleteId = list.get(i).getFriend(); // samdori96@nate.com&krdut1@gmail.com
				
				deleteId = deleteId.replace(user_id, "");
				logger.info("deleteId Second => "+ deleteId);
				if(deleteId.length() != 0) {
					if(deleteId.charAt(0) == '&') {
						deleteId = deleteId.substring(1,deleteId.length());
					}else if (deleteId.charAt(deleteId.length()-1) == '&') {
						deleteId = deleteId.substring(0,deleteId.length()-1);
					}else {
						deleteId = deleteId.replace("&&", "&");
					}
					
					map.put("friend", deleteId);
					result = mybatis.update("LoginMapper.addFriendUpdate", map);
				}else {
					mybatis.delete("LoginMapper.secessionFriend2", friend); // 친구 삭제
					result = 1;
				}
			}
		
		return result;
	}	
	
	// 사용자가 친구추가 버튼을 눌렀을때 혹은 상태 확인값
	public int waitFriend(String user_id, String friend, String name) {
		int result = 0;
		FriendVO friendVO = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("friend", friend);
		map.put("name", name);
		// user_id와 friend로 select 없으면 친구신청 가능 있으면 이미 친구요청 보냈음.
		friendVO = mybatis.selectOne("LoginMapper.addFriendWait", map);
		if(friendVO != null) { // 이미 친구요청 보냈거나 상태값 확인해야함
			String status = friendVO.getStatus();
			if(status.equals("W")) {
				
			}
		}else { // 친구신청 보낼수 있음.
			result = mybatis.insert("LoginMapper.addFriendInsertWait", map);
		}

		logger.info("waitFriend result .. " + result);
		return result;
	}
	
	public String statusFriend(String user_id, String friend, String name) {
		String result = "add friend";
		FriendVO friendVO1 = null;
		FriendVO friendVO2 = null;
		FriendVO friendVO3 = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("name", name);
		map.put("friend", friend);
		
		// 친구추가 거부한 사용자(추가예정)
		friendVO1 = mybatis.selectOne("LoginMapper.addFriend", user_id); // 본인이 이미 추가한 사용자
		friendVO2 = mybatis.selectOne("LoginMapper.addFriendWait", map); // 친구수락 대기중인 사용자
		friendVO3 = mybatis.selectOne("LoginMapper.addFriendWait2", map); // 친구수락 대기중인 사용자
		
		if(friendVO1 != null) {
			logger.info("friendVO1 " + friendVO1.getFriend());
			if(friendVO1.getFriend().contains(friend)) {
				return "already friend";
			} 
		}
		
		if(friendVO2 != null) {
			logger.info("friendVO2 " + friendVO2.toString());
			if(friendVO2.getStatus().equals("W")) {
				return "wait friend";
			}
		}
		
		if(friendVO3 != null) {
			logger.info("friendVO3 " + friendVO3.toString());
			if(friendVO3.getStatus().equals("W")) {
				return "wait friend";
			}
		}
		
		
		
		return result;
	}
	
	public List<FriendVO> getRequestFriend(String user_id) {
		List<FriendVO> list = null;
		list = mybatis.selectList("LoginMapper.getRequestFriend", user_id);
		
		if(list != null) {
			for(int i = 0 ; i < list.size() ; i++) {
				logger.info("getFriend " + list.get(i).getFriend());
			}
		}
		
		return list;
	}
	
	public List<FriendVO> getRequestedFriend(String user_id) {
		List<FriendVO> list = null;
		list = mybatis.selectList("LoginMapper.getRequestedFriend", user_id);
		
		if(list != null) {
			for(int i = 0 ; i < list.size() ; i++) {
				logger.info("getUser_id " + list.get(i).getUser_id());
			}
		}
		
		return list;
	}
	
	public FriendVO getFriend(String user_id) {
		FriendVO friendVO = null;
		friendVO = mybatis.selectOne("LoginMapper.addFriend", user_id);
		
		if(friendVO != null) {
			return friendVO;
		}
		
		return friendVO;
	}
	
	public boolean secession(String user_id) { // samdori96@nate.com
		boolean result = true;
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		List<FriendVO> list = mybatis.selectList("LoginMapper.FriendMySelect", user_id);
		for(int i = 0 ; i < list.size() ; i++) {
			String user_idTemp = list.get(i).getUser_id(); // krdut1@gmail.com
			String deleteId = list.get(i).getFriend();  // samdori96@nate.com&dhkdnxodzm@naver.com
			
			deleteId = deleteId.replace(user_id, "");
			logger.info("deleteId => "+ deleteId);
			if(deleteId.length() != 0) {
				if(deleteId.charAt(0) == '&') {
					deleteId = deleteId.substring(1,deleteId.length());
				}else if (deleteId.charAt(deleteId.length()-1) == '&') {
					deleteId = deleteId.substring(0,deleteId.length()-1);
				}else {
					deleteId = deleteId.replace("&&", "&");
				}
				
				map.put("user_id", user_idTemp);
				map.put("friend", deleteId);
				if(mybatis.update("LoginMapper.addFriendUpdate", map) == 1) {
					result = true;
				}else {
					result = false;
				}
			}else {
				mybatis.delete("LoginMapper.secessionFriend", map); // 친구 삭제
				result = true;
			}
			
		}
		
		//friend
			mybatis.delete("LoginMapper.secessionFriend", user_id);
		//friend_wait
			mybatis.delete("LoginMapper.secessionFriendWait", user_id);
		//giftcon
			mybatis.delete("LoginMapper.secessionGiftcon", user_id);
		//user
			mybatis.delete("LoginMapper.secessionUser", user_id);
		//user_device
			mybatis.delete("LoginMapper.secessionUserDevice", user_id);
		
		return result;
	}
	
	public LoginVO userInfo(String user_id) {
		LoginVO loginVO = mybatis.selectOne("LoginMapper.userInfo", user_id);
		return loginVO;
	}
	
	public boolean userinfoName(HashMap<String, Object> requestMap) {
		int resultInt = mybatis.update("LoginMapper.userinfoName", requestMap);
		String registrant = requestMap.get("user_id")+"("+requestMap.get("name")+")";
		requestMap.put("registrant", registrant);
		logger.info("변경될 등록아이디 ==> " + registrant);
		mybatis.update("GiftMapper.updateGiftcontRegistrant", requestMap);
		
		
		if(resultInt == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean userinfoPassword(HashMap<String, Object> requestMap) {
		int resultInt = mybatis.update("LoginMapper.userinfoPassword", requestMap);
		
		if(resultInt == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkEmailInfo(HashMap<String, Object> requestMap) {
		LoginVO loginVO = mybatis.selectOne("LoginMapper.checkEmailInfo", requestMap);
		
		if(loginVO != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkEmail(HashMap<String, Object> requestMap) {
		EmailVO emailVO = mybatis.selectOne("LoginMapper.checkEmail", requestMap);
		
		if(emailVO != null) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public boolean certNumberDB(String user_id, String cert_number) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("cert_number", cert_number);
		EmailVO emailVO = mybatis.selectOne("LoginMapper.emailCheckSelect", map);
		
		if(emailVO != null) {
			mybatis.update("LoginMapper.emailCheckUpdate", map);
			return true;
		}else {
			mybatis.insert("LoginMapper.emailCheckInsert", map);
			return false;
		}
	}
	
	public String sendSMS(String phone_number, String certNum, String device_id, HashMap<String, String> params) {
		String api_key = "NCSDSJDNIALLOOF0";
        String api_secret = "2TOIAXY6SXFHDHF5637A4OCBZAJXNIPQ";
        Message coolsms = new Message(api_key, api_secret);
        
		int result = 0;
		int count;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("phone_number", phone_number);
		map.put("cert_number", certNum);
		map.put("device_id", device_id);
		
		SmsVO smsVO = mybatis.selectOne("LoginMapper.smsSelect", map);
		
		if(smsVO != null) { // 당일에 2번 이상 시도
			count = smsVO.getCount();
			if(count == 3) { // 3번째 시도
				logger.info(count+"번째 시도");
				return "false";
			}else {
		        try {
		        	JSONObject obj = (JSONObject) coolsms.send(params);
		        	logger.info(obj.toString());
		        	
					map.put("count", count + 1);
					result = mybatis.update("LoginMapper.smsUpdate", map);
					logger.info(count+"번째 시도 Update");
		        } catch (CoolsmsException e) {
		        	logger.error(e.getMessage());
		        	logger.error("오류코드 : "+e.getCode());
		            return e.getMessage();
		        }
			}
		}else { // 당일에 첫 시도
	        try {
	        	JSONObject obj = (JSONObject) coolsms.send(params);
	        	logger.info(obj.toString());
	        	
				mybatis.insert("LoginMapper.smsInsert", map);
				logger.info("당일 첫 시도");
	        } catch (CoolsmsException e) {
	        	logger.error(e.getMessage());
	        	logger.error("오류코드 : "+e.getCode());
	            return e.getMessage();
	        }
		}
		return "";
	}
	
	public boolean smsCheck(HashMap<String, Object> requestMap) {
		SmsVO smsVO = mybatis.selectOne("LoginMapper.smsCheck", requestMap);
		
		if(smsVO != null) {
			return true;
		}else {
			return false;
		}
	}
}
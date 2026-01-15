package spring.service;

import java.util.HashMap;
import java.util.Random;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savegift.login.LoginDAO;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
	
	@Autowired
	LoginDAO loginDAO;
	
	public String certifiedPhoneNumber(String phoneNumber, String cerNum, String device_id) {
        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "010-8831-1502");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "[기프티콘 저장소] 기프티콘 저장소 회원 인증번호는 "+cerNum+" 입니다");
        params.put("app_version", "기프티콘 저장소 ver 1.0.0"); // application name and version

        return loginDAO.sendSMS(phoneNumber, cerNum, device_id, params);
    }
	
	public boolean smsCheck(HashMap<String,Object> requestMap) {
        return loginDAO.smsCheck(requestMap);
    }
	
}

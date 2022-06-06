package spring.service;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SmsController {
	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	SmsService smsService;

	@RequestMapping(value = "/sendsms", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public String sendSMS(@RequestBody HashMap<String, Object> requestMap) {
		String result;
		String phone_number = (String) requestMap.get("phoneNumber");
		String device_id = (String) requestMap.get("device_id");
		
		Random rand = new Random();
		String numStr = "";
		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr += ran;
		}

		System.out.println("수신자 번호 : " + phone_number);
		System.out.println("인증번호 : " + numStr);
		result = smsService.certifiedPhoneNumber(phone_number, numStr, device_id);
		return result;
	}
}

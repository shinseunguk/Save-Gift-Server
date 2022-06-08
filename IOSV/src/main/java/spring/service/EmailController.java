package spring.service;

import java.util.HashMap;
import java.util.Properties;
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
import org.springframework.web.servlet.ModelAndView;

import com.savegift.login.LoginService;

@Controller
public class EmailController {
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private LoginService loginService;
	
	Properties props = new Properties();

	//DB작업이 필요한 만큼 DAO들 선언해야함
	
	//아이디와 이메일이 같으면 특정한 메일이 가게..
	@RequestMapping(value = "/noticeMail", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean sendEmail(@RequestBody HashMap<String, Object> requestMap) throws Exception {
		boolean result = false;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		String email = (String) requestMap.get("user_id");
		
		Random rand = new Random();
		String numStr = "";
		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr += ran;
		}
		
		result = loginService.checkEmailInfo(requestMap);
		
		if(result) {

			logger.info(email+"님에게 메일 전송 시도.. ");
//			ModelAndView mv = new ModelAndView();
			
			String addr = "krdut1@gmail.com";
			
			String subject = "[기프티콘 저장소] 이메일 인증을 위한 인증번호가 발급되었습니다.";
			
			String body = "안녕하세요.\n\n기프티콘 저장소 인증을 위한 인증번호가 발급되었습니다.\n아래의 인증번호 복사하거나 직접 입력하여 이메일 인증을 완료해주세요.\n\n"+ numStr;
			
			mailService.sendEmail(email, addr, subject, body);
			loginService.certNumberDB(email, numStr);
			
			return true;	
		}else {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/check/email", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean checkEmail(@RequestBody HashMap<String, Object> requestMap) throws Exception {
		boolean result = false;
		result = loginService.checkEmail(requestMap);
		
		return result;
	}
}

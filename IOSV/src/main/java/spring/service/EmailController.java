package spring.service;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmailController {
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	private MailService mailService;
	
	Properties props = new Properties();

	//DB작업이 필요한 만큼 DAO들 선언해야함
	
	//아이디와 이메일이 같으면 특정한 메일이 가게..
	@RequestMapping("/noticeMail")
	@ResponseBody
	public boolean sendEmail(HttpServletRequest request) throws Exception {
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		String email = request.getParameter("email");
		logger.info(email+"님에게 메일 전송 시도.. ");
		ModelAndView mv = new ModelAndView();
		
		String addr = "krdut1@gmail.com";
		
		String subject = "[기프티콘 저장소] 이메일 인증을 위한 인증번호가 발급되었습니다.";
		
		String body = "안녕하세요.\n\n기프티콘 저장소 인증을 위한 인증번호가 발급되었습니다.\n아래의 인증번호 복사하거나 직접 입력하여 이메일 인증을 완료해주세요.\n\n188392";
		
		mailService.sendEmail(email, addr, subject, body);
		
		return true;
	}
}

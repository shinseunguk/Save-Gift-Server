package com.savegift.giftcon;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * Handles requests for the application home page.
 */

// 스케줄러
// https://blog.naver.com/deeperain/221609802306
@Controller
public class GiftController {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftController.class);
	
	String downPath = "/Users/ukbook/git/Save-Gift-Server/IOSV/src/main/webapp/upload";
	
	@Autowired
	GiftService giftService;
	
	
	@RequestMapping(value = "/asdfasdf", method = RequestMethod.GET)
	@ResponseBody
	public void duplicationid(){
		logger.info("asdfasdf");
		giftService.duplicationid();
	}
	
	@RequestMapping(value = "/register/image",
			method = RequestMethod.POST,
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, //수신
			produces = MediaType.APPLICATION_JSON_VALUE //송신
			)
	@ResponseBody
	public String registerImage(@RequestParam HashMap<String, Object> requestMap, MultipartHttpServletRequest request) throws IOException {
		boolean resultBool = false;
		
		logger.info("/register/gift -------> " + requestMap.toString());
        
		String fileName = (String) requestMap.get("file_name");
//		logger.info("fileName ----------> "+fileName);
		
//		logger.info("request.tostring() -------> "+request);
		
		
		MultipartFile file = request.getFile("photo");
		
		if(file != null) {
			logger.info("file is not null");
			
			//저장 받은 파일의 확장명을 가져온다.
			String fileExtensions = FilenameUtils.getExtension(file.getOriginalFilename());
//			logger.info("fileExtensions ----------> "+fileExtensions);
			
			//저장 파일명을 정의한다.
			String saveFileName = String.format(fileName+"."+fileExtensions);
			logger.info("saveFileName ----------> "+saveFileName);
			
			requestMap.put("img_url", saveFileName);
			
	        resultBool = giftService.registerGift(requestMap);
	        
	        if(resultBool) {
	        	logger.info("DB insert 성공");
	        }else {
	        	logger.info("DB insert 실패");
	        }
			
			// 파일생성 
		    //파일 저장경로, 저장파일 이름
			File saveFile = new File(downPath, saveFileName);
		    file.transferTo(saveFile);
		    
		  //파일 생성 여부 체크
		    if(saveFile.exists()) {
		    	//파일 체크 가능한 log 이용하기
		    	logger.info("파일생성 완료");
		    }else {
		    	logger.info("파일생성 실패");
		    }
		}else {
			logger.info("file is null");
		}
		logger.info("/register/image -------> " + requestMap.toString());
		
//		resultBool = giftService.registerGift(requestMap);
		
		return "success";
	}
	
	@RequestMapping(value = "/gift/save", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public List<GiftVO> registerGift(@RequestBody HashMap<String, Object> requestMap){
		List<GiftVO> list = null;
		list = giftService.giftSave(requestMap);
		
		if(list != null) {
			for(int i = 0; i < list.size() ; i++) {
				logger.info("list"+ i +"--> " + list.get(i).toString());
			}	
		}
		
        
        return list;
	}
	
	@RequestMapping(value = "/gift/detail", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public List<GiftVO> giftDetail(@RequestBody HashMap<String, Object> requestMap){
		logger.info("giftDetail requestMap... " + requestMap.toString());
		List<GiftVO> list = giftService.giftDetail(requestMap);;
		
		return list;
	}
	
	@RequestMapping(value = "/gift/delete", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean giftDelete(@RequestBody HashMap<String, Object> requestMap){
		boolean result = false;
		logger.info("giftDelete requestMap... " + requestMap.toString());
		result = giftService.giftDelete(requestMap);;
		
		return result;
	}
	
	@RequestMapping(value = "/overlap/photo", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public int overlapPhoto(@RequestBody HashMap<String, Object> requestMap){
        logger.info("/overlap/photo -------> " + requestMap.toString());
        
        int result = giftService.overlapPhoto(requestMap);
        
        return result;
	}
	
	@RequestMapping(value = "/gift/revise", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public int giftRevise(@RequestBody HashMap<String, Object> requestMap){
        logger.info("/giftRevise -------> " + requestMap.toString());
        
        int result = giftService.giftRevise(requestMap);
        
        return result;
	}
	
	@RequestMapping(value = "/gift/useyn", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public int giftUseyn(@RequestBody HashMap<String, Object> requestMap){
        logger.info("/giftUseyn -------> " + requestMap.toString());
        
        int result = giftService.giftUseyn(requestMap);
        
        return result;
	}
	
	@RequestMapping(value = "/gift/present", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean giftPresent(@RequestBody HashMap<String, Object> requestMap){
		logger.info("/giftPresent -------> " + requestMap.toString());
		
		int result = giftService.giftPresent(requestMap);
		
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value = "/present/tab", method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public boolean presentTab(@RequestBody HashMap<String, Object> requestMap){
		logger.info("/presentTab -------> " + requestMap.toString());
		
		int result = giftService.presentTab(requestMap);
		
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}
	
//	
//	@RequestMapping(value = "/login", method = RequestMethod.POST , produces = "application/json")
//	@ResponseBody
//	public boolean login(@RequestBody HashMap<String, Object> requestMap){
//		boolean result = false;
//		
//		String user_id = (String) requestMap.get("user_id"); // 아이디   
//		String index = (String) requestMap.get("index"); // 아이디
//		
//		if(index != null) {
//			logger.info("회원정보수정 비밀번호 확인 ... user_id : " + user_id);
//		}else {
//			logger.info("로그인 ... user_id : " + user_id);
//		}
//        
//        
//        //SHA256으로 암호화된 비밀번호
//		try {
//			String cryptogram = sha256.encrypt((String) requestMap.get("user_password"));
//			requestMap.put("user_password", cryptogram);
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			logger.info("error ", e);
//			e.printStackTrace();
//		}
//		
//        result = loginService.login(requestMap);
//		
//		return result;
//	}
	
}

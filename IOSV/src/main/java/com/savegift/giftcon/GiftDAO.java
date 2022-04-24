package com.savegift.giftcon;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GiftDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftDAO.class);
	
	@Autowired
    SqlSession mybatis;
	
	
	//TEST
	public void duplicationid() {
		mybatis.selectOne("GiftMapper.test");
		logger.info("TEST....");
	}
	
	public boolean registerGift(HashMap<String, Object> requestMap) {
		int result = mybatis.insert("GiftMapper.registerGift", requestMap);
		
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public int overlapPhoto(HashMap<String, Object> requestMap) {
		int result = 0;
		GiftVO giftVo = mybatis.selectOne("GiftMapper.overlapPhoto", requestMap);
		
		if(giftVo != null) {
			result = 1;
			logger.info("overlapPhoto result -------> " + giftVo.toString());
		}
		
		
		return result;
	}
	
	public List<GiftVO> giftSave(HashMap<String, Object> requestMap) {
		List<GiftVO> list = null;
		logger.info("giftSave --------> \n" + requestMap.toString());
//		mybatis.selectList("GiftMapper.overlapPhoto", requestMap);
		
		String index = (String) requestMap.get("index");
		String use_yn = (String) requestMap.get("use_yn");
        logger.info("index -------> "+ index);
        logger.info("use_yn -------> "+ use_yn);
		
		if(!index.contains("blogin")) { // 로그인
			String user_id = (String) requestMap.get("user_id");
			logger.info("해당 아이디로 select -----> " + user_id);
        	
        	if(use_yn.equals("All")){
        		logger.info("로그인 All");
        		list = mybatis.selectList("GiftMapper.giftSaveAllLogin", requestMap);
        	}else if(use_yn.equals("Unused")) {
        		logger.info("로그인 Unused");
        		list = mybatis.selectList("GiftMapper.giftSaveUnUsedLogin", requestMap);
        	}else if(use_yn.equals("Used")) {
        		logger.info("로그인 Used");
        		list = mybatis.selectList("GiftMapper.giftSaveUsedLogin", requestMap);
        	}
        	
        }else { // 비로그인
        	String device_id = (String) requestMap.get("device_id");
        	logger.info("해당 디바이스 아이디로 select -----> " + device_id);
        	
        	if(use_yn.equals("All")){
        		logger.info("비로그인 All");
        		list = mybatis.selectList("GiftMapper.giftSaveAllBLogin", requestMap);
        	}else if(use_yn.equals("Unused")) {
        		logger.info("비로그인 Unused");
        		list = mybatis.selectList("GiftMapper.giftSaveUnUsedBLogin", requestMap);
        	}else if(use_yn.equals("Used")) {
        		logger.info("비로그인 Used");
        		list = mybatis.selectList("GiftMapper.giftSaveUsedBLogin", requestMap);
        	}
        	
        }
		
		return list;
	}
	
}
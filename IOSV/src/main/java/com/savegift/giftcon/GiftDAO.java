package com.savegift.giftcon;

import java.util.HashMap;

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
		
		return true;
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
	
}
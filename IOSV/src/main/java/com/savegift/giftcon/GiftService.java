package com.savegift.giftcon;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GiftService")
public class GiftService {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftService.class);
	
	@Autowired
	GiftDAO giftDAO;
	
//	//TEST 
	public void duplicationid() {
		giftDAO.duplicationid();
	}
	
//	//기프티콘 insert 
	public boolean registerGift(HashMap<String, Object> requestMap) {
		return giftDAO.registerGift(requestMap);
	}
	
//	//기프티콘 중복체크 
	public int overlapPhoto(HashMap<String, Object> requestMap) {
		return giftDAO.overlapPhoto(requestMap);
	}
	
//	//기프티콘 불러오기 
	public List<GiftVO> giftSave(HashMap<String, Object> requestMap) {
		return giftDAO.giftSave(requestMap);
	}
	
//	//기프티콘 디테일 
	public List<GiftVO> giftDetail(HashMap<String, Object> requestMap) {
		return giftDAO.giftDetail(requestMap);
	}
	
//	//기프티콘 삭제 
	public boolean giftDelete(HashMap<String, Object> requestMap) {
		return giftDAO.giftDelete(requestMap);
	}
}
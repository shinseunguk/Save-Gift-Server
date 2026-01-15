package com.savegift.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savegift.repository.GiftRepository;
import com.savegift.domain.Gift;
import com.savegift.domain.GiftUserDevice;

@Service("GiftService")
public class GiftService {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftService.class);
	
	@Autowired
	GiftRepository giftRepository;
	
//	//TEST 
	public void duplicationid() {
		giftRepository.duplicationid();
	}
	
//	//기프티콘 insert 
	public boolean registerGift(HashMap<String, Object> requestMap) {
		return giftRepository.registerGift(requestMap);
	}
	
//	//기프티콘 중복체크 
	public int overlapPhoto(HashMap<String, Object> requestMap) {
		return giftRepository.overlapPhoto(requestMap);
	}
	
//	//기프티콘 수정
	public int giftRevise(HashMap<String, Object> requestMap) {
		return giftRepository.giftRevise(requestMap);
	}
	
//	//기프티콘 사용여부 수정
	public int giftUseyn(HashMap<String, Object> requestMap) {
		return giftRepository.giftUseyn(requestMap);
	}
	
//	//기프티콘 선물
	public int giftPresent(HashMap<String, Object> requestMap) {
		return giftRepository.giftPresent(requestMap);
	}
	
//	//기프티콘 선물탭 Action
	public int presentTab(HashMap<String, Object> requestMap) {
		return giftRepository.presentTab(requestMap);
	}
	
//	//기프티콘 불러오기 
	public List<Gift> giftSave(HashMap<String, Object> requestMap) {
		return giftRepository.giftSave(requestMap);
	}
	
//	//기프티콘 디테일 
	public List<Gift> giftDetail(HashMap<String, Object> requestMap) {
		return giftRepository.giftDetail(requestMap);
	}
	
//	//기프티콘 삭제 
	public boolean giftDelete(HashMap<String, Object> requestMap) {
		return giftRepository.giftDelete(requestMap);
	}
	
//	//기프티콘 PUSH 추출 
	public List<GiftUserDevice> getPushList(Date date) {
		return giftRepository.getPushList(date);
	}
}
package com.savegift.giftcon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.savegift.notification.NotificationDAO;

@Repository
public class GiftDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftDAO.class);
	
	@Autowired
    SqlSession mybatis;
	
	@Autowired
	NotificationDAO notificationDAO;
	
	
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
	
	public int giftRevise(HashMap<String, Object> requestMap) {
		int result = 0;
		result = mybatis.update("GiftMapper.giftRevise", requestMap);
		
		return result;
	}
	
	public int giftUseyn(HashMap<String, Object> requestMap) {
		int result = 0;
		result = mybatis.update("GiftMapper.giftUseyn", requestMap);
		
		return result;
	}
	
	public int giftPresent(HashMap<String, Object> requestMap) {
		int result = 0;
		result = mybatis.update("GiftMapper.giftPresent", requestMap);
		if(result == 1) {
			notificationDAO.friendRequestPush(requestMap);
		}
		
		return result;
	}
	
	public int presentTab(HashMap<String, Object> requestMap) {
		int result = 0;
		
		String index = (String) requestMap.get("index");
		
		if(index.equals("one")) {
			int use_yn = 1;
			requestMap.put("use_yn", use_yn);
			result = mybatis.update("GiftMapper.presentTab1", requestMap);
		}else if(index.equals("two")) {
			int use_yn = 0;
			requestMap.put("use_yn", use_yn);
			result = mybatis.update("GiftMapper.presentTab1", requestMap);
		}else if(index.equals("three")) {
			result = mybatis.update("GiftMapper.presentTab2", requestMap);
		}
		
		
//		result = mybatis.update("GiftMapper.presentTab", requestMap);
		
		return result;
	}
	
	public List<GiftVO> giftSave(HashMap<String, Object> requestMap) {
		int present = 0;
		List<GiftVO> list = null;
		logger.info("giftSave --------> \n" + requestMap.toString());
//		mybatis.selectList("GiftMapper.overlapPhoto", requestMap);
		
		String index = (String) requestMap.get("index");
		String use_yn = (String) requestMap.get("use_yn");
		String category = (String) requestMap.get("category");
		
        logger.info("index -------> "+ index);
        logger.info("use_yn -------> "+ use_yn);
        logger.info("category -------> "+ category);
        
        
        if(requestMap.get("present") != null) {
        	present = (Integer) requestMap.get("present");	
        }
        logger.info("present ===> " + present);
        
		
		if(!index.contains("blogin")) { // 로그인
			String user_id = (String) requestMap.get("user_id");
			logger.info("해당 아이디로 select -----> " + user_id);
        	
			if(present == 0) { // 저장소
				
				if(use_yn.equals("All")){
					if(category.equals("registrationDate")) {
						logger.info("로그인 All 최근 등록순");
						list = mybatis.selectList("GiftMapper.giftSaveAllLogin1", requestMap);
					}else if(category.equals("expirationDate")) {
						logger.info("로그인 All 유효기간 임박순");
						list = mybatis.selectList("GiftMapper.giftSaveAllLogin2", requestMap);
					}else if(category.equals("productName")) {
						logger.info("로그인 All 상품명순");
						list = mybatis.selectList("GiftMapper.giftSaveAllLogin3", requestMap);
					}else if(category.equals("brandName")) {
						logger.info("로그인 All 교환처 이름순");
						list = mybatis.selectList("GiftMapper.giftSaveAllLogin4", requestMap);
					}
				}else if(use_yn.equals("Unused")) {
					if(category.equals("registrationDate")) {
						logger.info("로그인 Unused 최근 등록순");
						list = mybatis.selectList("GiftMapper.giftSaveUnUsedLogin1", requestMap);
					}else if(category.equals("expirationDate")) {
						logger.info("로그인 Unused 유효기간 임박순");
						list = mybatis.selectList("GiftMapper.giftSaveUnUsedLogin2", requestMap);
					}else if(category.equals("productName")) {
						logger.info("로그인 Unused 상품명순");
						list = mybatis.selectList("GiftMapper.giftSaveUnUsedLogin3", requestMap);
					}else if(category.equals("brandName")) {
						logger.info("로그인 Unused 교환처 이름순");
						list = mybatis.selectList("GiftMapper.giftSaveUnUsedLogin4", requestMap);
					}
				}else if(use_yn.equals("Used")) {
					if(category.equals("registrationDate")) {
						logger.info("로그인 Used 최근 등록순");
						list = mybatis.selectList("GiftMapper.giftSaveUsedLogin1", requestMap);
					}else if(category.equals("expirationDate")) {
						logger.info("로그인 Used 유효기간 임박순");
						list = mybatis.selectList("GiftMapper.giftSaveUsedLogin2", requestMap);
					}else if(category.equals("productName")) {
						logger.info("로그인 Used 상품명순");
						list = mybatis.selectList("GiftMapper.giftSaveUsedLogin3", requestMap);
					}else if(category.equals("brandName")) {
						logger.info("로그인 Used 교환처 이름순");
						list = mybatis.selectList("GiftMapper.giftSaveUsedLogin4", requestMap);
					}
				}
			}else { //선물함
				if(present == 1) {
					if(category.equals("registrationDate")) {
						logger.info("선물함 최근 등록순");
						list = mybatis.selectList("GiftMapper.myPresent1NotUse", requestMap);
					}else if(category.equals("expirationDate")) {
						logger.info("선물함 유효기간 임박순");
						list = mybatis.selectList("GiftMapper.myPresent2NotUse", requestMap);
					}else if(category.equals("productName")) {
						logger.info("선물함 상품명순");
						list = mybatis.selectList("GiftMapper.myPresent3NotUse", requestMap);
					}else if(category.equals("brandName")) {
						logger.info("선물함 교환처 이름순");
						list = mybatis.selectList("GiftMapper.myPresent4NotUse", requestMap);
					}
				}else if(present == 2) {
					if(category.equals("registrationDate")) {
						logger.info("선물함 사용 최근 등록순");
						list = mybatis.selectList("GiftMapper.myPresent1Use", requestMap);
					}else if(category.equals("expirationDate")) {
						logger.info("선물함 사용 유효기간 임박순");
						list = mybatis.selectList("GiftMapper.myPresent2Use", requestMap);
					}else if(category.equals("productName")) {
						logger.info("선물함 사용 상품명순");
						list = mybatis.selectList("GiftMapper.myPresent3Use", requestMap);
					}else if(category.equals("brandName")) {
						logger.info("선물함 사용 교환처 이름순");
						list = mybatis.selectList("GiftMapper.myPresent4Use", requestMap);
					}
				}else if(present == 3) {
					if(category.equals("registrationDate")) {
						logger.info("내가준 선물 최근 등록순");
						list = mybatis.selectList("GiftMapper.giftPresent1", requestMap);
					}else if(category.equals("expirationDate")) {
						logger.info("내가준 선물 유효기간 임박순");
						list = mybatis.selectList("GiftMapper.giftPresent2", requestMap);
					}else if(category.equals("productName")) {
						logger.info("내가준 선물 상품명순");
						list = mybatis.selectList("GiftMapper.giftPresent3", requestMap);
					}else if(category.equals("brandName")) {
						logger.info("내가준 선물 교환처 이름순");
						list = mybatis.selectList("GiftMapper.giftPresent4", requestMap);
					}
				}
			}
        	
        }else { // 비로그인
        	String device_id = (String) requestMap.get("device_id");
        	logger.info("해당 디바이스 아이디로 select -----> " + device_id);
        	
        	if(use_yn.equals("All")){
        		if(category.equals("registrationDate")) {
        			logger.info("비로그인 All 최근 등록순");
        			list = mybatis.selectList("GiftMapper.giftSaveAllBLogin1", requestMap);
        		}else if(category.equals("expirationDate")) {
        			logger.info("비로그인 All 유효기간 임박순");
        			list = mybatis.selectList("GiftMapper.giftSaveAllBLogin2", requestMap);
        		}else if(category.equals("productName")) {
        			logger.info("비로그인 All 상품명순");
        			list = mybatis.selectList("GiftMapper.giftSaveAllBLogin3", requestMap);
        		}else if(category.equals("brandName")) {
        			logger.info("비로그인 All 교환처 이름순");
        			list = mybatis.selectList("GiftMapper.giftSaveAllBLogin4", requestMap);
        		}
        	}else if(use_yn.equals("Unused")) {
        		if(category.equals("registrationDate")) {
        			logger.info("비로그인 Unused 최근 등록순");
        			list = mybatis.selectList("GiftMapper.giftSaveUnUsedBLogin1", requestMap);
        		}else if(category.equals("expirationDate")) {
        			logger.info("비로그인 Unused 유효기간 임박순");
        			list = mybatis.selectList("GiftMapper.giftSaveUnUsedBLogin2", requestMap);
        		}else if(category.equals("productName")) {
        			logger.info("비로그인 Unused 상품명순");
        			list = mybatis.selectList("GiftMapper.giftSaveUnUsedBLogin3", requestMap);
        		}else if(category.equals("brandName")) {
        			logger.info("비로그인 Unused 교환처 이름순");
        			list = mybatis.selectList("GiftMapper.giftSaveUnUsedBLogin4", requestMap);
        		}
        		
        	}else if(use_yn.equals("Used")) {
        		if(category.equals("registrationDate")) {
        			logger.info("로그인 Used 최근 등록순");
        			list = mybatis.selectList("GiftMapper.giftSaveUsedBLogin1", requestMap);
        		}else if(category.equals("expirationDate")) {
        			logger.info("로그인 Used 유효기간 임박순");
        			list = mybatis.selectList("GiftMapper.giftSaveUsedBLogin2", requestMap);
        		}else if(category.equals("productName")) {
        			logger.info("로그인 Used 상품명순");
        			list = mybatis.selectList("GiftMapper.giftSaveUsedBLogin3", requestMap);
        		}else if(category.equals("brandName")) {
        			logger.info("로그인 Used 교환처 이름순");
        			list = mybatis.selectList("GiftMapper.giftSaveUsedBLogin4", requestMap);
        		}
        	}
        	
        }
		
		return list;
	}
	
	public List<GiftVO> giftDetail(HashMap<String, Object> requestMap) {
		List<GiftVO> list = null;
		logger.info("giftDetail --------> \n" + requestMap.toString());
//		mybatis.selectList("GiftMapper.overlapPhoto", requestMap);
		
		list = mybatis.selectList("GiftMapper.giftDetail", requestMap);
		
		return list;
	}
	
	public boolean giftDelete(HashMap<String, Object> requestMap) {
		// 선물한 기프티콘도 삭제해야함.
		boolean result = false;
		logger.info("giftDetail --------> \n" + requestMap.toString());
//		int resultNum = 1;
		
		int resultNum = mybatis.delete("GiftMapper.giftDelete", requestMap);
		
		if(resultNum == 1) {
			result = true;
		}
		
		return result;
	}
	
	public boolean autoUseYnUpdate() {
		int result = mybatis.update("GiftMapper.autoUseYnUpdate");
		if (result == 1) {
			return true;
		}else {
			return false;
		}
	}
	
//	public List<GiftUserDeviceVO> getPushList(Date date) {
//		List<GiftUserDeviceVO> list1 = null;
//		List<GiftUserDeviceVO> list2 = null;
//		List<GiftUserDeviceVO> list3 = null;
//
//        Calendar cal = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        Calendar cal3 = Calendar.getInstance();
//        
//        cal.setTime(new Date());
//        cal2.setTime(new Date());
//        cal3.setTime(new Date());
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("current: " + df.format(cal.getTime()));
//
//        cal.add(Calendar.MONTH, 1);
//        cal2.add(Calendar.DATE, 7);
//        cal3.add(Calendar.DATE, 1);
//        System.out.println("after MONTH: " + df.format(cal.getTime()));
//        System.out.println("after DATE 7: " + df.format(cal2.getTime()));
//        System.out.println("after DATE 1: " + df.format(cal3.getTime()));
//        String mDate1 = df.format(cal.getTime());
//        String mDate2 = df.format(cal2.getTime());
//        String mDate3 = df.format(cal3.getTime());
//        
//		//30일전 대상자 추출		
//		list1 = mybatis.selectList("GiftMapper.periodSelect", mDate1);
//		int[] arr30 = new int[list1.size()];
//		for(int i = 0 ; i < list1.size() ; i++) {
//			arr30[i] = list1.get(i).getSeq();
//			logger.info(list1.get(i).getSeq()+"!");	
//		}
//		
//		//7일전 대상자 추출		
//		list2 = mybatis.selectList("GiftMapper.periodSelect", mDate2);
//		int[] arr7 = new int[list2.size()];
//		for(int i = 0 ; i < list2.size() ; i++) {
//			arr7[i] = list2.get(i).getSeq();
//			logger.info(list2.get(i).getSeq()+"@");	
//		}
//		
//		//1일전 대상자 추출
//		list3 = mybatis.selectList("GiftMapper.periodSelect", mDate3);
//		int[] arr1 = new int[list3.size()];
//		for(int i = 0 ; i < list3.size() ; i++) {
//			arr1[i] = list3.get(i).getSeq();
////			logger.info(list3.get(i).getSeq()+"#");
//		}
//		
//		System.out.println("");
//		for(int i = 0 ; i < list1.size() ; i++) {
//			System.out.print(arr30[i]+" ");
//		}
//		System.out.println("");
//		for(int i = 0 ; i < list2.size() ; i++) {
//			System.out.print(arr7[i]+" ");
//		}
//		System.out.println("");
//		for(int i = 0 ; i < list3.size() ; i++) {
//			System.out.print(arr1[i]+" ");
//		}
//		
//		for(int i = 0 ; i < arr30.length ; i++) {
//			for(int j = 0 ; j < arr7.length ; j++) {
//				if(arr30[i] == arr7[j]) {
//					arr7[j] = 0;
//				}
//			}
//		}
//		
//		for(int i = 0 ; i < arr30.length ; i++) {
//			for(int j = 0 ; j < arr1.length ; j++) {
//				if(arr30[i] == arr1[j]) {
//					arr1[j] = 0;
//				}
//			}
//		}
//		
//		for(int i = 0 ; i < arr7.length ; i++) {
//			for(int j = 0 ; j < arr1.length ; j++) {
//				if(arr7[i] == arr1[j]) {
//					arr1[j] = 0;
//				}
//			}
//		}
//		
//		System.out.println("");
//		for(int i = 0 ; i < list1.size() ; i++) {
//			System.out.print(arr30[i]+" ");
//		}
//		System.out.println("");
//		for(int i = 0 ; i < list2.size() ; i++) {
//			System.out.print(arr7[i]+" ");
//		}
//		System.out.println("");
//		for(int i = 0 ; i < list3.size() ; i++) {
//			System.out.print(arr1[i]+" ");
//		}
//		
//		System.out.println("!!!!!!!!");
//		for(int i = 0 ; i < list1.size() ; i++) {
//			if(arr30[i] != 0) {
//				System.out.println(list1.get(i).getPush_token());	
//			}
//		}
//		
//		return list1;
//	}
	
	public List<GiftUserDeviceVO> getPushList(Date date) {
		List<GiftUserDeviceVO> list = null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("current: " + df.format(cal.getTime()));

        cal.add(Calendar.MONTH, 1);
        System.out.println("after MONTH: " + df.format(cal.getTime()));
        String mDate = df.format(cal.getTime());
        
		//30일전 대상자 추출		
		list = mybatis.selectList("GiftMapper.periodSelect", mDate);
		for(int i=0; i<list.size(); i++) {
			System.out.print(list.get(i).getSeq()+" ");
		}
		return list;
	}
	
}
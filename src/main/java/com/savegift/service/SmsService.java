package com.savegift.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.savegift.repository.UserRepository;

@Service
@Transactional
@PropertySource("classpath:datasource.properties")
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    private UserRepository userRepository;

    @Value("${sms.sender.phone}")
    private String senderPhoneNumber;

    public String certifiedPhoneNumber(String phoneNumber, String cerNum, String deviceId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);
        params.put("from", senderPhoneNumber);
        params.put("type", "SMS");
        params.put("text", "[기프티콘 저장소] 기프티콘 저장소 회원 인증번호는 " + cerNum + " 입니다");
        params.put("app_version", "기프티콘 저장소 ver 1.0.0");

        return userRepository.sendSMS(phoneNumber, cerNum, deviceId, params);
    }

    public boolean smsCheck(HashMap<String, Object> requestMap) {
        return userRepository.smsCheck(requestMap);
    }
}

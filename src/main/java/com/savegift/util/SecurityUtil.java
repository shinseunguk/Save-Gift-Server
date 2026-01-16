package com.savegift.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;

    public SecurityUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.secureRandom = new SecureRandom();
    }

    /**
     * BCrypt를 사용하여 비밀번호 암호화
     */
    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * BCrypt를 사용하여 비밀번호 검증
     */
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * SHA-256 해시 (레거시 호환용)
     * @deprecated BCrypt 사용 권장
     */
    @Deprecated
    public String encryptSHA256(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        return bytesToHex(md.digest());
    }

    /**
     * 인증번호 생성 (6자리 숫자)
     */
    public String generateCertificationNumber() {
        int number = secureRandom.nextInt(900000) + 100000;
        return String.valueOf(number);
    }

    /**
     * 인증번호 생성 (지정 자릿수)
     */
    public String generateCertificationNumber(int digits) {
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - min;
        int number = secureRandom.nextInt(max) + min;
        return String.valueOf(number);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}

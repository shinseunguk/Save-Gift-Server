package com.savegift.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        logger.error("Unexpected error occurred", e);

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "서버 오류가 발생했습니다.");
        response.put("error", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("Invalid argument: {}", e.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "잘못된 요청입니다.");
        response.put("error", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e) {
        logger.error("Null pointer exception", e);

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "데이터 처리 중 오류가 발생했습니다.");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.savegift.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.savegift.repository.GiftRepository;

@Component
public class GiftExpirationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(GiftExpirationScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GiftRepository giftRepository;

    /**
     * 만료된 기프티콘 자동 사용처리
     * 매일 00:00:10에 실행
     */
    @Scheduled(cron = "10 0 0 * * *")
    public void autoUseYnUpdate() {
        String startTime = dateFormat.format(new Date());
        logger.info("=== 기프티콘 만료 처리 스케줄러 시작: {} ===", startTime);

        try {
            long startMillis = System.currentTimeMillis();
            boolean result = giftRepository.autoUseYnUpdate();
            long duration = System.currentTimeMillis() - startMillis;

            if (result) {
                logger.info("기프티콘 만료 처리 완료 - 실행시간: {}ms", duration);
            } else {
                logger.info("만료 처리할 기프티콘 없음 - 실행시간: {}ms", duration);
            }
        } catch (Exception e) {
            logger.error("기프티콘 만료 처리 중 오류 발생", e);
        }

        String endTime = dateFormat.format(new Date());
        logger.info("=== 기프티콘 만료 처리 스케줄러 종료: {} ===", endTime);
    }
}

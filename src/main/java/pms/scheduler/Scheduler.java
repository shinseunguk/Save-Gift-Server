package pms.scheduler;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.savegift.giftcon.GiftController;
import com.savegift.giftcon.GiftDAO;

@Component
public class Scheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	@Autowired
	GiftDAO giftDAO;
	
	@Scheduled(cron = "10 0 0 * * *")
	public void autoUseYnUpdate() {
		boolean result = giftDAO.autoUseYnUpdate();
		
		if(result) {
			logger.info("스케줄러 실행");
		}else {
			logger.info("스케줄러 오류");
		}
	}

}

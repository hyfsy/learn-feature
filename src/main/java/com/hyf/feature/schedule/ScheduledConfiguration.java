package com.hyf.feature.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author baB_hyf
 * @date 2020/12/06
 */
// @Configuration
@EnableScheduling
public class ScheduledConfiguration {

    @Component
    static class SpringTaskTest {

        private final Logger log = LoggerFactory.getLogger(SpringTaskTest.class);

        @Scheduled(cron = "0/3 * * * * ? ")
        public void scheduleWithCron() {
            log.info("schedule cron...");
        }

        @Scheduled(fixedDelay = 1000)
        public void scheduleWithDelay() {
            log.info("schedule delay...");
        }

        @Scheduled(fixedRate = 3000)
        public void scheduleWithRate() {
            log.info("schedule rate...");
        }
    }
}



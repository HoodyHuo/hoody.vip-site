package vip.hoody.api.task

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

//Example patterns:
//        "0 0 * * * *" = the top of every hour of every day.
//        "*/10 * * * * *" = every ten seconds.
//        "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
//        "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
//        "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
//        "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
//        "0 0 0 25 12 ?" = every Christmas Day at midnight

@Component
class AliyunCacheTask {
    private static final Logger log = LoggerFactory.getLogger(AliyunCacheTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @CacheEvict("AliyunOneDay")
    @Scheduled(fixedRate = 1800000L)
    public void cleanAliyunOneDay() {
        log.info("清理Aliyun监控信息缓存:AliyunOneDay")
    }


    @CacheEvict("AliyunLastPeriod")
    @Scheduled(fixedRate = 1800000L)
    public void cleanAliyunLastPeriod() {
        log.info("清理Aliyun监控信息缓存:AliyunLastPeriod")
    }
}

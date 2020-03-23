package bluecitron.cleanblog.core.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class PostViewerSchedule {

    @Profile("dev")
    @Transactional
    @Scheduled(cron = "* * * * * *")
    public void refreshHistoryDev() {
        log.info("Refreshed post view history. {} rows are affected. [dev]", 0);
    }

    @Profile("prod")
    @Transactional
    @Scheduled(cron = "* 6 * * *")
    public void refreshHistoryProd() {
        log.info("Refreshed post view history. {} rows are affected. [prod]", 0);
    }

}

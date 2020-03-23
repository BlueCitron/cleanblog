package bluecitron.cleanblog.core.domain.post;

import bluecitron.cleanblog.core.repository.PostViewerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
@Component
public class PostViewerSchedule {

    PostViewerRepository postViewerRepository;

    @Profile("dev")
    @Transactional
    @Scheduled(cron = "*/10 * * * * *")
    public void refreshHistoryDev() {
        postViewerRepository.deleteAll();
        log.info("Refreshed post view history. {} rows are affected. [dev]", 0);
    }

    @Profile("prod")
    @Transactional
    @Scheduled(cron = "* * 6 * * *")
    public void refreshHistoryProd() {
        postViewerRepository.deleteAll();
        log.info("Refreshed post view history. {} rows are affected. [prod]", 0);
    }

}

package bluecitron.cleanblog.core.domain.post;

import bluecitron.cleanblog.core.repository.PostViewerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
@Component
public class PostViewerSchedule {

    PostViewerRepository postViewerRepository;

    @Transactional
    @Scheduled(cron = "* * 6 * * *")
    public void refreshHistoryProd() {
        postViewerRepository.deleteAll();
        log.info("Refreshed post view history. {} rows are affected.", 0);
    }

}

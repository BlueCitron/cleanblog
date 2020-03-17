package bluecitron.cleanblog.common;

import bluecitron.cleanblog.core.repository.RoleRepository;
import bluecitron.cleanblog.web.dto.command.CategoryCommand;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import bluecitron.cleanblog.web.service.CategoryService;
import bluecitron.cleanblog.web.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
// @Component
public class InitData implements ApplicationListener<ApplicationStartedEvent> {

    PostService postService;
    CategoryService categoryService;
    RoleRepository roleRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setName("TestCategory");
        categoryService.create(categoryCommand);

        for (int i = 1; i < 23; i++) {
            PostCommand postCommand = new PostCommand();
            postCommand.setTitle("TestPost [" + i + "]");
            postCommand.setSubtitle("SubTitle..");
            postCommand.setContent("Lorem itsum dolars");
            postCommand.setHeaderImgUrl("sample.jpg");
            postCommand.setCategoryName("TestCategory");
            postService.create(postCommand);
        }
    }
}

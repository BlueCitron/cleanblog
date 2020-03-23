package bluecitron.cleanblog.web.service;

import bluecitron.cleanblog.core.domain.post.Post;
import bluecitron.cleanblog.web.dto.CommonListResponse;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Date;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostService postService;

    @Transactional
    void createPost() {
        String title = String.format("Test[%s]", (new Date()).toString());
        Post post = Post.create(title, "subtitle", "content", "url");
        em.persist(post);
    }

    @Test
    void 리스트_파라미터_없음() {
        // given
        for (int i = 0; i < 23; i++) {
            createPost();
        }

        // when
        PostCommand postCommand = new PostCommand();
        postCommand.setPage(1);
        CommonListResponse list = postService.getList(postCommand);

        // then
        Integer page = list.getPageInfo().getNumber();
        Integer size = list.getPageInfo().getSize();

        Assertions.assertEquals(page, 1);
    }
}
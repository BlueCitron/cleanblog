package bluecitron.cleanblog.web.service;

import bluecitron.cleanblog.core.domain.post.Post;
import bluecitron.cleanblog.core.domain.post.PostViewer;
import bluecitron.cleanblog.core.domain.post.PostViewerKey;
import bluecitron.cleanblog.core.repository.PostViewerRepository;
import bluecitron.cleanblog.web.dto.CommonListResponse;
import bluecitron.cleanblog.web.dto.PostDto;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.Optional;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostService postService;

    @Autowired
    PostViewerRepository postViewerRepository;

    @BeforeEach
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

    @Test
    void 포스트조회() {
        // given
        Post post = em.createQuery("select p from Post p", Post.class).getSingleResult();
        em.flush();
        em.clear();
        Long id = post.getId();
        String ip = "127.0.0.1";

        // when
        PostDto postDto = postService.viewPost(id, ip);

        // then
        PostViewerKey postViewerKey = new PostViewerKey(id, ip);
        PostViewer postViewer = postViewerRepository
                .findById(postViewerKey)
                .orElseThrow(() -> new EntityNotFoundException());


        Assertions.assertEquals(postViewer.getId().getPostId(), id);
        Assertions.assertEquals(postViewer.getId().getIp(), ip);
    }

    @Test
    void 포스트조회_이미조회() {
        // given
        Post post = em.createQuery("select p from Post p", Post.class).getSingleResult();
        em.flush();
        em.clear();
        Long id = post.getId();
        String ip = "127.0.0.1";

        // when
        for (int i = 0; i < 2; i++) {
            postService.viewPost(id, ip);
            em.flush();
            em.clear();
        }

        // then
        PostDto postDto = postService.viewPost(id, ip);

        Assertions.assertEquals(postDto.getViewCount(), 1);
    }
}
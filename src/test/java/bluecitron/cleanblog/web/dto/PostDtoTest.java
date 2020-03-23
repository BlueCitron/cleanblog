package bluecitron.cleanblog.web.dto;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Transactional
@SpringBootTest
class PostDtoTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    EntityManager em;

    @Test
    void 변환_테스트() {
        Post post = Post.create("테스트", "subtitle", "content", "sample.jpg");
        Category parent_category = Category.create("Parent Category");
        Category child_category = Category.create("Child Category");
        post.setCategory(parent_category);
        child_category.setParent(parent_category);
        em.persist(post);
        em.persist(parent_category);
        em.persist(child_category);
        em.flush();

        Post findPost = em.createQuery("select p from Post p", Post.class).getSingleResult();

        Assertions.assertNotNull(findPost);
        Assertions.assertEquals(findPost.getTitle(), "테스트");

        PostDto dto = modelMapper.map(findPost, PostDto.class);
        Assertions.assertNotNull(dto);
        log.info(dto.toString());
    }
}
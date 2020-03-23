package bluecitron.cleanblog.web.service;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.post.Post;
import bluecitron.cleanblog.core.domain.exception.EntityNotFoundException;
import bluecitron.cleanblog.core.domain.post.PostViewer;
import bluecitron.cleanblog.core.domain.post.PostViewerKey;
import bluecitron.cleanblog.core.repository.CategoryRepository;
import bluecitron.cleanblog.core.repository.PostRepository;
import bluecitron.cleanblog.core.repository.PostViewerRepository;
import bluecitron.cleanblog.web.dto.CommonListResponse;
import bluecitron.cleanblog.web.dto.PostDto;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.thymeleaf.util.StringUtils.isEmpty;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class PostService {

    CategoryService categoryService;
    ModelMapper modelMapper;

    PostRepository postRepository;
    CategoryRepository categoryRepository;
    PostViewerRepository postViewerRepository;

    public CommonListResponse getList(PostCommand command) {
        Integer page = command.getPage();
        Long categoryId = command.getCategoryId();

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.DESC, "createdDate");

        Category category = nonNull(categoryId) ?
                categoryRepository.findById(categoryId).orElse(null) : null;

        Page<Post> result = postRepository.searchByCategory(category, pageRequest);
        List<PostDto> list = result.getContent().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        CommonListResponse<Post, PostDto> postPostDtoCommonListResponse = new CommonListResponse<>(list, command, result);

        return new CommonListResponse<Post, PostDto>(list, command, result);
    }

    public PostDto viewPost(Long postId, String ip) {
        Post post = getOneEntity(postId);
        increaseViewCount(post, ip);

        log.info("[{}] views post(id={}, title={}).", ip, post.getId(), post.getTitle());

        return modelMapper.map(post, PostDto.class);
    }

    public void create(PostCommand command) {
        Post post = Post.create(command.getTitle(), command.getSubtitle(), command.getContent(), command.getHeaderImgUrl());
        String categoryName = command.getCategoryName();
        if (!isEmpty(categoryName)) {
            Category category = categoryService.getOneEntity(categoryName);
            post.setCategory(category);
        }
        postRepository.save(post);



    }

    public void update(Long postId, PostCommand command) {
        // 포스트 조회
        Post post = getOneEntity(postId);

        // 카테고리 조회
        String categoryName = command.getCategoryName();
        Category category = categoryService.getOneEntity(categoryName);

        String headerImgUrl = command.getHeaderImgUrl();

        post.setTitle(command.getTitle());
        post.setSubtitle(command.getSubtitle());
        post.setContent(command.getContent());
        if (!isEmpty(headerImgUrl)) {
            post.setHeaderImgUrl(headerImgUrl);
        }
        post.setCategory(category);
    }

    public void delete(Long postId) {
        Post post = getOneEntity(postId);
        post.setDeleteYn("Y");
    }

    public void increaseViewCount(Post post, String ip) {
        // 확인 후
        PostViewerKey postViewerKey = new PostViewerKey(post.getId(), ip);
        PostViewer findPostViewer = postViewerRepository.findById(postViewerKey).orElse(null);

        // 조회 기록 저장
        if (isNull(findPostViewer)) {
            PostViewer postViewer = new PostViewer(postViewerKey);
            postViewerRepository.save(postViewer);
        }
    }

    public PostDto getOne(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post", postId));
        return modelMapper.map(post, PostDto.class);
    }

    public Post getOneEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post", postId));
    }
}

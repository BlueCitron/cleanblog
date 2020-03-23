package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Page<Post> findByCategoryIn(List<Category> childrenId, Pageable pageable);
}

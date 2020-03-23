package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> searchByCategory(Category category, Pageable pageable);
}

package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.Post;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    Page<Post> searchByCategory(Category category, Pageable pageable);
}

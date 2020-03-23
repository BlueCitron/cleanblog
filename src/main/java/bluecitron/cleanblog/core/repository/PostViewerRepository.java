package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.post.PostViewer;
import bluecitron.cleanblog.core.domain.post.PostViewerKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostViewerRepository extends JpaRepository<PostViewer, PostViewerKey> {
}

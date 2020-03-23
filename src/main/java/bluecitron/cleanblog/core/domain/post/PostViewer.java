package bluecitron.cleanblog.core.domain.post;

import bluecitron.cleanblog.core.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class PostViewer extends BaseTimeEntity {

    @Id
    @EmbeddedId
    private PostViewerKey id;

    public PostViewer(Post post, String ip) {
        Long id = post.getId();
        this.id = new PostViewerKey(id, ip);
    }

    public PostViewer(PostViewerKey key) {
        this.id = key;
    }

}

package bluecitron.cleanblog.core.domain.post;

import bluecitron.cleanblog.core.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
@Setter
public class PostViewerKey implements Serializable {

    @Column(name = "post_id", updatable = false)
    private Long postId;

    @Column(name = "ip", updatable = false)
    private String ip;

}

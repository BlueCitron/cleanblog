package bluecitron.cleanblog.core.domain.post;

import bluecitron.cleanblog.core.domain.BaseTimeEntity;
import bluecitron.cleanblog.core.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    private String subtitle;

    @Lob
    @Column(nullable = false)
    private String content;

    private String headerImgUrl;
    private Integer viewCount = 0;

    @Column(name = "DELETE_YN", length = 1)
    private String deleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    protected Post(String title, String subtitle, String content, String headerImgUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.headerImgUrl = headerImgUrl;
        this.viewCount = 0;
    }

    public static Post create(String title, String subtitle, String content, String headerImgUrl) {
        return new Post(title, subtitle, content, headerImgUrl);
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

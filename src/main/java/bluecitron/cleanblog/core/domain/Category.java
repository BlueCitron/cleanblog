package bluecitron.cleanblog.core.domain;

import bluecitron.cleanblog.core.domain.exception.CategoryDepthOverException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String headerImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    protected Category(String name) {
        this.name = name;
    }

    public static Category create(String name) {
        return new Category(name);
    }

    public void setParent(Category parent) {
        if (parent.hasParent()) {
            throw new CategoryDepthOverException();
        }
        this.parent = parent;
        parent.getChildren().add(this);
    }

    private boolean hasParent() {
        return Objects.nonNull(parent);
    }
}

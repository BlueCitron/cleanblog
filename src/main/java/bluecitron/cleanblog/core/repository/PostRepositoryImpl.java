package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.Post;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static bluecitron.cleanblog.core.domain.QPost.post;
import static java.util.Objects.nonNull;

@AllArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    EntityManager em;
    JPAQueryFactory queryFactory;

    @Override
    public Page<Post> searchByCategory(Category category, Pageable pageable) {
        QueryResults<Post> postQueryResults = queryFactory.selectFrom(post)
                .where(categoryEq(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdDate.desc())
                .fetchResults();
        List<Post> content = postQueryResults.getResults();
        long total = postQueryResults.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    /*******************************************************************************/

    public BooleanExpression categoryEq(Category category) {
        return nonNull(category) ? post.category.eq(category) : null;
    }


}

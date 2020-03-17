package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static bluecitron.cleanblog.core.domain.QCategory.category;
import static org.springframework.util.StringUtils.isEmpty;

@AllArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    EntityManager em;
    JPAQueryFactory queryFactory;

    @Override
    public List<Category> searchByName(String name) {
        return queryFactory.selectFrom(category)
                .where(nameEq(name))
                .fetch();
    }

    public BooleanExpression nameEq(String name) {
        return !isEmpty(name) ? category.name.eq(name) : null;
    }
}

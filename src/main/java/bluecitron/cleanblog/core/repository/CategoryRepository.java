package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    List<Category> findAllByName(String name);
    Optional<Category> findByName(String name);
}

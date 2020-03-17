package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Category;

import java.util.List;

public interface CategoryRepositoryCustom {

   List<Category> searchByName(String name);
}

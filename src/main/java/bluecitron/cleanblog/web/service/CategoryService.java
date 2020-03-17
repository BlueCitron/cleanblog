package bluecitron.cleanblog.web.service;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.core.domain.exception.CategoryNameDuplicatedException;
import bluecitron.cleanblog.core.domain.exception.EntityNotFoundException;
import bluecitron.cleanblog.core.repository.CategoryRepository;
import bluecitron.cleanblog.web.dto.CategoryDto;
import bluecitron.cleanblog.web.dto.command.CategoryCommand;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

    public List<CategoryDto> getList(CategoryCommand command) {
        List<Category> result = getListEntity(command);
        return result.stream()
                .map(c -> modelMapper.map(c, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public CategoryDto getOne(Long categoryId) {
        Category category = getOneEntity(categoryId);
        return modelMapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> getChildren(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category", categoryId));
        return category.getChildren().stream()
                .map(c -> modelMapper.map(c, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public CategoryDto getOne(String name) {
        Category category = getOneEntity(name);
        return modelMapper.map(category, CategoryDto.class);
    }

    public List<Category> getListEntity(CategoryCommand command) {
        String name = command.getName();
        return categoryRepository.searchByName(name);
    }

    public Category getOneEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category", categoryId));
    }

    public Category getOneEntity(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category", name));
    }

    public void create(CategoryCommand command) {
        String name = command.getName();
        Category check = categoryRepository.findByName(name).orElse(null);

        if (Objects.isNull(check)) {
            Category category = Category.create(command.getName());
            categoryRepository.save(category);
        } else {
            throw new CategoryNameDuplicatedException(name);
        }
    }

    public void update(CategoryCommand command) {
        String name = command.getName();
        Category nameCheck = categoryRepository.findByName(name).orElse(null);
        if (Objects.isNull(nameCheck)) {
            Category category = getOneEntity(command.getCategoryId());
            category.setName(name);
        } else {
            throw new CategoryNameDuplicatedException(name);
        }
    }

    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}

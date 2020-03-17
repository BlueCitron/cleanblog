package bluecitron.cleanblog.web.controller;

import bluecitron.cleanblog.core.domain.Category;
import bluecitron.cleanblog.web.dto.command.CategoryCommand;
import bluecitron.cleanblog.web.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/category")
@AllArgsConstructor
@Controller
public class CategoryController {

    CategoryService categoryService;

    @GetMapping("/edit")
    public String editView() {
        return "category-edit";
    }

    @PostMapping("/create")
    public String create(CategoryCommand command) {
        categoryService.create(command);
        return "redirect:/category/edit";
    }

    @PostMapping("/update")
    public String update(CategoryCommand command) {
        categoryService.update(command);
        return "redirect:/category/edit";
    }

    @PostMapping("/delete")
    public String delete(CategoryCommand command) {
        Long categoryId = command.getCategoryId();
        categoryService.delete(categoryId);
        return "redirect:/category/edit";
    }
}

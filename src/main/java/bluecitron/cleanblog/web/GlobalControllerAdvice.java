package bluecitron.cleanblog.web;

import bluecitron.cleanblog.web.dto.CategoryDto;
import bluecitron.cleanblog.web.dto.command.CategoryCommand;
import bluecitron.cleanblog.web.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {

    CategoryService categoryService;

    @ModelAttribute("categories")
    public List<CategoryDto> categories() {
        return categoryService.getList(new CategoryCommand());
    }
}

package bluecitron.cleanblog.web;

import bluecitron.cleanblog.web.dto.CategoryDto;
import bluecitron.cleanblog.web.dto.command.CategoryCommand;
import bluecitron.cleanblog.web.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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

    @ExceptionHandler(Exception.class)
    public String globalExceptionHandler(HttpServletRequest request, Exception e) {

        log.error("{}", e.getMessage());
        Arrays.stream(e.getStackTrace())
                .forEach(stackTraceElement -> log.error(stackTraceElement.toString()));

        return "redirect:/";
    }
}

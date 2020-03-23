package bluecitron.cleanblog.web.controller;

import bluecitron.cleanblog.web.dto.CategoryDto;
import bluecitron.cleanblog.web.dto.CommonListResponse;
import bluecitron.cleanblog.web.dto.PostDto;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import bluecitron.cleanblog.web.service.CategoryService;
import bluecitron.cleanblog.web.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    PostService postService;
    CategoryService categoryService;

    @GetMapping
    public String listView(Model model, PostCommand command) {
        CommonListResponse result = postService.getList(command);
        CategoryDto categoryDto = categoryService.getOne(command.getCategoryId());

        model.addAttribute("posts", result.getList());
        model.addAttribute("category", categoryDto);
        model.addAttribute("searchInfo", result.getSearchInfo());
        model.addAttribute("pageInfo", result.getPageInfo());

        return "post-list";
    }

    @GetMapping("/{id}")
    public String detailView(Model model, @PathVariable Long id, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        PostDto postDto = postService.viewPost(id, remoteAddr);
        model.addAttribute("post", postDto);
        return "post-detail";
    }

    @GetMapping("/write")
    public String writeView(Model model) {
        model.addAttribute("isUpdate", false);
        return "post-write";
    }

    @GetMapping("/update/{id}")
    public String updateView(Model model, @PathVariable Long id) {
        PostDto post = postService.getOne(id);
        model.addAttribute("post", post);
        log.info("UpdateView");
        return "post-edit";
    }

    @PostMapping("/create")
    public String create(PostCommand command) {
        log.info(command.toString());
        postService.create(command);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String update(PostCommand command, @PathVariable Long id) {
        log.info(command.toString());
        postService.update(id, command);
        return "redirect:/post/" + id;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/";
    }
}

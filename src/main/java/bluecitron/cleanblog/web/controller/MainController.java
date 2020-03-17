package bluecitron.cleanblog.web.controller;

import bluecitron.cleanblog.web.dto.CommonListResponse;
import bluecitron.cleanblog.web.dto.command.PostCommand;
import bluecitron.cleanblog.web.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class MainController {

    PostService postService;

    @GetMapping("/")
    public String main(Model model, PostCommand command) {
        CommonListResponse result = postService.getList(command);
        model.addAttribute("posts", result.getList());
        model.addAttribute("searchInfo", result.getSearchInfo());
        model.addAttribute("pageInfo", result.getPageInfo());
        return "index";
    }
}

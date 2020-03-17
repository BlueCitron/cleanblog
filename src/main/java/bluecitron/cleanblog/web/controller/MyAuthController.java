package bluecitron.cleanblog.web.controller;

import bluecitron.cleanblog.web.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@Controller
public class MyAuthController {

    MemberService memberService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/sign-in")
    public String joinView() {
        return "sign-in";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public String signIn(SingInCommand command) {
        try {
            log.info(command.toString());
            memberService.joinUser(command.getAccount(), command.getPassword());
            return "login";
        } catch (Exception e) {
            return "sign-in";
        }
    }

    @GetMapping("/user/denied")
    public String insufficientAuthority(Model model, Authentication authentication, HttpServletRequest request) {

        return "redirect:/";
    }

    @Data
    class SingInCommand {
        String account;
        String password;
        String passwordAgain;
    }

}

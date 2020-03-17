package bluecitron.cleanblog.web.controller;

import bluecitron.cleanblog.web.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class SampleController {

    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/test")
    public @ResponseBody String fileTest(MultipartFile file) {
        try {
            log.info("getFile : [{}]", file);
            String save = fileUploadService.save(file);
            log.info("FilePath : {}", save);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "upload success";
    }
}

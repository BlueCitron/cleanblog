package bluecitron.cleanblog.web.controller;

import bluecitron.cleanblog.web.dto.FileUploadDto;
import bluecitron.cleanblog.web.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;


@Slf4j
@Controller
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/upload/{imgFileName}")
    public ResponseEntity<Resource> getImageFile(@PathVariable String imgFileName) {
        try {
            Resource file = fileUploadService.getFile(imgFileName);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + file.getFilename() + "\"").body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload/image")
    public ResponseEntity<FileUploadDto> uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        FileUploadDto fileUploadDto = new FileUploadDto();
        try {
            String fileName = fileUploadService.save(multipartFile);
            fileUploadDto.setFileName(fileName);
            URI uri = URI.create("/upload/" + fileName);
            return ResponseEntity
                    .created(uri)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(fileUploadDto);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}

package bluecitron.cleanblog.web.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Service
public class FileUploadService {

    private final String UPLOAD_PATH = "upload";

    ResourceLoader resourceLoader;

    @PostConstruct
    public void initFolder () {
        File file = new File(UPLOAD_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public String save(MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buffer = new byte[1024];

        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = getFileNameWithoutExtension(originalFilename);
        String extension = getExtension(originalFilename);

        File file = new File(makeFileName(fileName, extension));

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

        while (bis.read(buffer) != -1) {
            bos.write(buffer);
        }

        bis.close();
        bos.close();

        return file.getName();
    }

    public Resource getFile(String fileName) throws FileNotFoundException{
        return resourceLoader.getResource("file:./upload/" + fileName);
    }

    private String makeFileName(String fileName, String extension) {
        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        StringBuffer sb = new StringBuffer();
        sb.append(UPLOAD_PATH);
        sb.append("/");
        sb.append(fileName);
        sb.append("_");
        sb.append(dateFormat);
        sb.append(".");
        sb.append(extension);
        return sb.toString();
    }

    private String getFileNameWithoutExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(0, index);
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1, fileName.length());
    }
}

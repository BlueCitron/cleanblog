package bluecitron.cleanblog.web.dto.command;

import lombok.Data;

@Data
public class PostCommand implements BaseCommand {
    private String title;
    private String subtitle;
    private String content;
    private String headerImgUrl;
    private Long categoryId;
    private String categoryName;
    private Integer page = 0;
}

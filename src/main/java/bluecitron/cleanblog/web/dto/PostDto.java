package bluecitron.cleanblog.web.dto;

import lombok.Data;

@Data
public class PostDto extends BaseTimeDto{
    private Long id;
    private String title;
    private String subtitle;
    private String content;
    private String headerImgUrl;
    private Integer viewCount;
    private CategoryDto category;
}

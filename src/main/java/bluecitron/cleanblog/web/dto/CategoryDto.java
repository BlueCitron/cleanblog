package bluecitron.cleanblog.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CategoryDto extends BaseTimeDto {
    private Long id;
    private String name;
    private String headerImgUrl;
    private Integer postCount;
    private CategoryDto parent;
}

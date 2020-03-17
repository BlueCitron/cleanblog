package bluecitron.cleanblog.web.dto.command;

import lombok.Data;

@Data
public class CategoryCommand implements BaseCommand {
    private String name;
    private Long categoryId;
    private Long parentId;
}

package bluecitron.cleanblog.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseTimeDto {
    protected LocalDateTime createdDate;
    protected LocalDateTime lastModifiedDate;
}

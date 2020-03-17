package bluecitron.cleanblog.web.dto;

import lombok.Data;

@Data
public class MemberDto {

    private String account;
    private String password;
    private String role;
}

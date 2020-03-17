package bluecitron.cleanblog.core.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String account;

    private String password;

    private LocalDateTime lastLoggedIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    protected Member(String account, String password, Role role) {
        this.account = account;
        this.password = password;
        this.role = role;
    }

    public static Member create(String account, String password, Role role) {
        return new Member(account, password, role);
    }
}

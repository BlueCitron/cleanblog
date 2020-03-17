package bluecitron.cleanblog.web.service;

import bluecitron.cleanblog.core.domain.Member;
import bluecitron.cleanblog.core.domain.Role;
import bluecitron.cleanblog.core.domain.exception.AccountDuplicatedException;
import bluecitron.cleanblog.core.repository.MemberRepository;
import bluecitron.cleanblog.core.repository.RoleRepository;
import bluecitron.cleanblog.web.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class MemberService implements UserDetailsService {

    MemberRepository memberRepository;
    RoleRepository roleRepository;

    public Long joinUser(String account, String rawPassword) {

        Member findMember = memberRepository.findByAccount(account).orElse(null);

        if (Objects.isNull(findMember)) {
            // 비밀번호 암호화
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encoded = passwordEncoder.encode(rawPassword);
            Role guest = roleRepository.findByRoleName("ROLE_GUEST");
            Member member = Member.create(account, encoded, guest);

            return memberRepository.save(member).getId();
        } else {
            throw new AccountDuplicatedException(account);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        log.info("loadUserByUsername [account={}]", account);
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("not found. (account=%s)", account)));
        Role role = member.getRole();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        return new User(member.getAccount(), member.getPassword(), authorities);
    }
}

package bluecitron.cleanblog.core.repository;

import bluecitron.cleanblog.core.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}

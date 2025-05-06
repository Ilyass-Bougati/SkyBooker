package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

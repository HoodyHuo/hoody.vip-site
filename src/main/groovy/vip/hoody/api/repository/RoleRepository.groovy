package vip.hoody.api.repository

import vip.hoody.api.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Hoody on 2019/1/14.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    public List<Role> findAllByparentRole(Role role)
}

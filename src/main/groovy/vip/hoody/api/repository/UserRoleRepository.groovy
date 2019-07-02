package vip.hoody.api.repository

import vip.hoody.api.domain.User
import vip.hoody.api.domain.UserRole
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Hoody on 2019/1/15.
 */

interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    public List<UserRole> findAllByUser(User user)
}
package vip.hoody.api.repository

import vip.hoody.api.domain.Role
import vip.hoody.api.domain.RolePermission
import org.springframework.data.jpa.repository.JpaRepository

interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    public List<RolePermission> findAllByRole(Role role)
}
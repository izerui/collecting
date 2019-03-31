package com.github.collecting.dao;



import com.github.collecting.entity.Role;
import com.github.collecting.jpa.PlatformJpaRepository;

import java.util.List;

public interface RoleDao extends PlatformJpaRepository<Role,Long> {
    List<Role> findByTenantCode(String tenantCode);
}

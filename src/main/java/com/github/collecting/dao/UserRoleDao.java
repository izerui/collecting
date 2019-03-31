package com.github.collecting.dao;


import com.github.collecting.entity.UserRole;
import com.github.collecting.jpa.PlatformJpaRepository;

import java.util.List;

public interface UserRoleDao extends PlatformJpaRepository<UserRole,Long> {
    List<UserRole> findByTenantCodeAndUserCode(String tenantCode, String userCode);
}

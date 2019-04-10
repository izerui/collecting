package com.github.collecting.dao;


import com.github.collecting.entity.User;
import com.github.collecting.jpa.PlatformJpaRepository;

import java.util.List;

public interface UserDao extends PlatformJpaRepository<User, Long> {
    List<User> findByTenantCode(String tenantCode);
    User findByUserCode(String userCode);
    boolean existsByTenantCodeAndUserName(String tenantCode, String userName);
}

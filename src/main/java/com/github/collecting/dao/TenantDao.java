package com.github.collecting.dao;


import com.github.collecting.entity.Tenant;
import com.github.collecting.jpa.PlatformJpaRepository;

public interface TenantDao extends PlatformJpaRepository<Tenant, Long> {
}

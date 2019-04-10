package com.github.collecting.dao;



import com.github.collecting.entity.Dept;
import com.github.collecting.jpa.PlatformJpaRepository;

import java.util.List;

public interface DeptDao extends PlatformJpaRepository<Dept,Long> {
    List<Dept> findByTenantCodeAndRecordStatus(String tenantCode,Integer recordStatus);
    Dept findByTenantCodeAndDeptCode(String tenantCode, String deptCode);
}

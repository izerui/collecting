package com.github.collecting.service;

import com.github.collecting.dao.*;
import com.github.collecting.entity.Dept;
import com.github.collecting.entity.Resource;
import com.github.collecting.entity.Role;
import com.github.collecting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RbacService {


    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    RoleResourceDao roleResourceDao;
    @Autowired
    ResourceDao resourceDao;
    @Autowired
    DeptDao deptDao;

    public User getUserByUserCode(String userCode) {
        return userDao.findByUserCode(userCode);
    }

    public List<Resource> findResources() {
        return resourceDao.findAll();
    }

    public List<User> findUsers(String tenantCode) {
        return userDao.findByTenantCode(tenantCode);
    }

    public List<Role> findRoles(String tenantCode) {
        return roleDao.findByTenantCode(tenantCode);
    }

    public List<Dept> findDepts(String tenantCode) {
        return deptDao.findByTenantCode(tenantCode);
    }

}

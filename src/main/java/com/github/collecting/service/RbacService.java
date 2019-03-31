package com.github.collecting.service;

import com.github.collecting.dao.DeptDao;
import com.github.collecting.dao.UserDao;
import com.github.collecting.entity.Dept;
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
    DeptDao deptDao;

    public User getUserByUserCode(String userCode) {
        return userDao.findByUserCode(userCode);
    }

    public List<User> findUsers(String tenantCode) {
        return userDao.findByTenantCode(tenantCode);
    }

    public List<Dept> findDepts(String tenantCode) {
        return deptDao.findByTenantCode(tenantCode);
    }

    public Dept getDept(String tenantCode, String deptCode) {
        return deptDao.findByTenantCodeAndDeptCode(tenantCode, deptCode);
    }

    public void saveDept(Dept dept) {
        deptDao.save(dept);
    }
}

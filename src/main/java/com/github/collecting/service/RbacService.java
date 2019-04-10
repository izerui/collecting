package com.github.collecting.service;

import com.github.collecting.BusinessException;
import com.github.collecting.dao.DeptDao;
import com.github.collecting.dao.UserDao;
import com.github.collecting.dto.UserDto;
import com.github.collecting.entity.Dept;
import com.github.collecting.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class RbacService {


    @Autowired
    UserDao userDao;
    @Autowired
    DeptDao deptDao;

    private final static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public User getUserByUserCode(String userCode) {
        return userDao.findByUserCode(userCode);
    }

    public List<User> findUsers(String tenantCode) {
        return userDao.findByTenantCode(tenantCode);
    }

    public List<Dept> findDepts(String tenantCode) {
        return deptDao.findByTenantCodeAndRecordStatus(tenantCode, 1);
    }

    public Dept getDept(String tenantCode, String deptCode) {
        return deptDao.findByTenantCodeAndDeptCode(tenantCode, deptCode);
    }

    public void saveDept(Dept dept) {
        deptDao.save(dept);
    }

    public void deleteDept(String tenantCode, String deptCode) {
        Dept dept = deptDao.findByTenantCodeAndDeptCode(tenantCode, deptCode);
        dept.setRecordStatus(-1);
        deptDao.save(dept);
    }

    public void addUser(String tenantCode, UserDto userDto) {
        boolean exists = userDao.existsByTenantCodeAndUserName(tenantCode, userDto.getUserName());
        if (exists) {
            throw new BusinessException("用户已存在");
        }
        User user = new User();
        user.setUserCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserName(userDto.getUserName());
        user.setNickName(userDto.getNickName());
        user.setRecordStatus(1);
        user.setTenantCode(tenantCode);
        user.setDeptCode(userDto.getDeptCode());
        user.setRoleName("ROLE_SHOPER");
        userDao.save(user);
    }

    public void editUser(UserDto userDto){
        User user = userDao.findByUserCode(userDto.getUserCode());
        Assert.notNull(user,"未找到用户");
        user.setUpdateTime(new Date());
        user.setUserName(userDto.getUserName());
        user.setNickName(userDto.getNickName());
        user.setRecordStatus(userDto.getRecordStatus());
        user.setDeptCode(userDto.getDeptCode());
        userDao.save(user);
    }

    public void deleteUser(String userCode) {
        User user = userDao.findByUserCode(userCode);
        user.setRecordStatus(-1);
        user.setUpdateTime(new Date());
        userDao.save(user);
    }
}

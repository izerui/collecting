package com.github.collecting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.collecting.jpa.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//用户
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    //用户编号
    @Column(unique = true, nullable = false, updatable = false, length = 64)
    private String userCode;

    //用户名 手机号
    private String userName;

    //密码
    @JsonIgnore
    private String password;

    //昵称
    private String nickName;

    //状态：-1删除 0禁用 1正常
    private int recordStatus;

    //租户
    private String tenantCode;

    //部门编号
    private String deptCode;

    //拥有的角色 多个逗号分隔
    @Column(nullable = false)
    private String roleName;

    //头像
    private String avatar;

}

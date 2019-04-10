package com.github.collecting.dto;

import lombok.Data;
import org.springframework.util.Assert;

@Data
public class UserDto {
    private String userCode;
    private String userName;
    private String password;
    private String nickName;
    private String deptCode;
    private int recordStatus;

    public void validAdd(){
        Assert.notNull(userName,"手机号不能为空");
        Assert.notNull(password,"密码不能为空");
        Assert.notNull(nickName,"昵称不能为空");
        Assert.notNull(deptCode,"门店不能为空");
    }
    public void validEdit(){
        Assert.notNull(userName,"手机号不能为空");
        Assert.notNull(nickName,"昵称不能为空");
        Assert.notNull(deptCode,"门店不能为空");
    }
}

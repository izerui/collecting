package com.github.collecting.controller;

import com.github.collecting.advice.Response;
import com.github.collecting.entity.Dept;
import com.github.collecting.entity.User;
import com.github.collecting.service.RbacService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Api(description = "RBAC管理api")
@RestController
public class RbacController {

    @Autowired
    RbacService rbacService;

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("列表-部门")
    @GetMapping("/rbac/list-depts")
    public Response<List<Dept>> listDepts(@RequestHeader("tenantCode") String tenantCode) {
        List<Dept> depts = rbacService.findDepts(tenantCode);
        return Response.success(depts);
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("列表-用户")
    @GetMapping("/rbac/list-users")
    public Response<List<User>> listUsers(@RequestHeader("tenantCode") String tenantCode) {
        List<User> users = rbacService.findUsers(tenantCode);
        return Response.success(users);
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("详情-用户")
    @GetMapping("/rbac/get-user")
    public Response<User> getUser(@RequestParam("userCode") String userCode) {
        User user = rbacService.getUserByUserCode(userCode);
        return Response.success(user);
    }

}

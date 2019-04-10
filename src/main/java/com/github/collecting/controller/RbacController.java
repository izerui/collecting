package com.github.collecting.controller;

import com.github.collecting.advice.Response;
import com.github.collecting.dto.UserDto;
import com.github.collecting.entity.Dept;
import com.github.collecting.entity.User;
import com.github.collecting.service.RbacService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.github.collecting.advice.Response.success;

@Api(description = "RBAC管理api")
@RestController
public class RbacController {

    @Autowired
    RbacService rbacService;

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("列表-门店")
    @GetMapping("/rbac/list-depts")
    public Response<List<Dept>> listDepts(@RequestHeader("tenantCode") String tenantCode) {
        List<Dept> depts = rbacService.findDepts(tenantCode);
        return success(depts);
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("详情-门店")
    @GetMapping("/rbac/get-dept")
    public Response<Dept> getDept(@RequestHeader("tenantCode") String tenantCode,
                                  @RequestParam("deptCode") String deptCode) {
        Dept dept = rbacService.getDept(tenantCode, deptCode);
        return success(dept);
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("添加-门店")
    @PutMapping("/rbac/add-dept")
    public Response addDept(@RequestHeader("tenantCode") String tenantCode,
                            @RequestHeader("userCode") String userCode,
                            @RequestParam("deptName") String deptName) {
        Dept dept = new Dept();
        dept.setDeptCode(UUID.randomUUID().toString());
        dept.setDeptName(deptName);
        dept.setSort(0);
        dept.setRecordStatus(1);
        dept.setTenantCode(tenantCode);
        dept.setCreator(userCode);
        dept.setCreateTime(new Date());
        rbacService.saveDept(dept);
        return success();
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("编辑-门店")
    @PutMapping("/rbac/edit-dept")
    public Response editDept(@RequestHeader("tenantCode") String tenantCode,
                             @RequestHeader("userCode") String userCode,
                             @RequestParam("deptCode") String deptCode,
                             @RequestParam("deptName") String deptName) {
        Dept dept = rbacService.getDept(tenantCode, deptCode);
        dept.setDeptName(deptName);
        rbacService.saveDept(dept);
        return success();
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("删除-门店")
    @DeleteMapping("/rbac/delete-dept")
    public Response editDept(@RequestHeader("tenantCode") String tenantCode,
                             @RequestHeader("userCode") String userCode,
                             @RequestParam("deptCode") String deptCode) {
        rbacService.deleteDept(tenantCode, deptCode);
        return success();
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("新增-用户")
    @PutMapping("/rbac/add-user")
    public Response addUser(@RequestHeader("tenantCode") String tenantCode,
                             @RequestBody UserDto userDto) {
        userDto.validAdd();
        rbacService.addUser(tenantCode, userDto);
        return success();
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("编辑-用户")
    @PutMapping("/rbac/edit-user")
    public Response editUser(@RequestHeader("tenantCode") String tenantCode,
                             @RequestBody UserDto userDto) {
        userDto.validEdit();
        rbacService.editUser(userDto);
        return success();
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("删除-用户")
    @DeleteMapping("/rbac/delete-user")
    public Response deleteUser(@RequestHeader("tenantCode") String tenantCode,
                             @RequestParam String userCode) {
        rbacService.deleteUser(userCode);
        return success();
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("列表-用户")
    @GetMapping("/rbac/list-users")
    public Response<List<User>> listUsers(@RequestHeader("tenantCode") String tenantCode) {
        List<User> users = rbacService.findUsers(tenantCode);
        return success(users);
    }

    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation("详情-用户")
    @GetMapping("/rbac/get-user")
    public Response<User> getUser(@RequestParam("userCode") String userCode) {
        User user = rbacService.getUserByUserCode(userCode);
        return success(user);
    }

}

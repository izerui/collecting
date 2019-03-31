package com.github.collecting.controller;

import com.github.collecting.advice.Response;
import com.github.collecting.dto.UserSession;
import com.github.collecting.entity.User;
import com.github.collecting.service.RbacService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.github.collecting.advice.Response.error;
import static com.github.collecting.advice.Response.success;


@Api(description = "获取已登录用户相关信息")
@RestController
public class ContextController {

    @Autowired
    RbacService rbacService;

    @ApiOperation("获取当前sessionId")
    @GetMapping("/context/session")
    public Response<String> getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return success(session.getId());
        } else {
            return error("-1", "获取session失败");
        }
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/context/user-info")
    public Response<String> userInfo() {
        User user = rbacService.getUserByUserCode(UserSession.getUser().getUserCode());
        return success(user);
    }

    @ApiOperation("param多种请求类型测试")
    @RequestMapping("/context/test-param")
    public Response<String> testParam(@RequestParam(value = "name",required = false) String name){
        return success("hello: "+name);
    }

    @ApiOperation("put body请求类型测试")
    @PutMapping("/context/put-body")
    public Response<String> putBody(@RequestBody Map map){
        return success("hello: "+map.get("name"));
    }

    @ApiOperation("post body请求类型测试")
    @PostMapping("/context/post-body")
    public Response<String> postBody(@RequestBody Map map){
        return success("hello: "+map.get("name"));
    }

}

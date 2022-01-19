package com.sxau.cms.controller;

import com.sxau.cms.pojo.User;
import com.sxau.cms.service.UserService;
import com.sxau.cms.util.JwtUtil;
import com.sxau.cms.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.controller
 * @ClassName: JwtController
 * @Author: 张晟睿
 * @Date: 2022/1/10 16:32
 * @Version: 1.0
 */
@RestController
public class JwtController {
    @Autowired
    private UserService userService;

    @GetMapping("/auth/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/login")
    public Result login(String userName, String passWord) {
// 假设数据库中查询到了该用户，这里测试先随机生成一个UUID，作为用户ID
        String userId = UUID.randomUUID().toString();
// 准备存放在JWT中的自定义数据
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("username", "tom");
        info.put("role", "admin");
// 生成JWT字符串
        String token = JwtUtil.sign(userId, info);
        return Result.success("token: " + token);
    }

    @ApiOperation(value = "新增用户", notes = "新增用户，需要json格式的字符串")
    @PostMapping("/register")
    public Result regiser(@RequestBody User user) {
        System.out.println(user);
        userService.saveOrUpdateUser(user);
        return Result.success(user);
    }

    @ApiOperation(value = "验证测试", notes = "添加JWT后，测试指定接口是否需要验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType =
                    "String", required = true, paramType = "query", defaultValue = "tom"),
            @ApiImplicitParam(name = "token", value = "JWT", dataType =
                    "String", required = true, paramType = "header", defaultValue = "aaaa.bbbb.cccc"),
    })
    @GetMapping("/auth/test")
    public Result test(String username) {
        return Result.success("hello! " + username);
    }

    /**
     * 注意，
     * 使用@ApiOperation(consumes = "application/x-www-form-urlencoded")来指定
     * swagger中，把参数以什么形式进行传递
     * 如果这里不指定，那么它或默认使用application/json
     * 一定要观察swagger中显示执行的CURL命令中指定的请求头信息
     */
    @ApiOperation(value = "用户登录", notes = "登录后，返回JWT的token值", consumes =
            "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType =
                    "String", required = true, paramType = "form", defaultValue = "tom"),
            @ApiImplicitParam(name = "password", value = "密码", dataType =
                    "String", required = true, paramType = "form", defaultValue = "123456"),
    })
    @PostMapping(value = "/loginto", consumes = "application/x-www-form-urlencoded")
    public Result loginto(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.login(username, password);
        System.out.println("jwtuser:"+user);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("username", user.getUsername());
        info.put("realname", user.getRealname());
        //签发JWT的token
        String token = JwtUtil.sign(Long.toString(user.getId()), info);
        return Result.success("token:" + token);
    }
}

package com.sxau.cms.controller;

import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.User;
import com.sxau.cms.service.UserService;
import com.sxau.cms.util.JwtUtil;
import com.sxau.cms.util.Result;
import com.sxau.cms.util.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.controller
 * @ClassName: UserController
 * @Author: 张晟睿
 * @Date: 2022/1/9 10:21
 * @Version: 1.0
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    //注册用户
    @ApiOperation(value = "注册用户",notes = "传递一个user用户类型的json格式的字符串")
    @PostMapping("/saveUser")
    public Result saveUser(@RequestBody User user){
        System.out.println(user);
        userService.saveOrUpdateUser(user);
        return Result.success();
    }

    //根据用户名查找用户信息
    @ApiOperation(value = "查找用户",notes = "传递一个用户名")
    @ApiImplicitParams(
            @ApiImplicitParam(value = "用户名",name = "username"))
    @GetMapping("/findUserByName")
    public Result findUserByName(String username){
        User user = userService.findUserByUsername(username);
        if (user==null){
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(user);
    }
    //登陆
    @ApiOperation(value = "登陆",notes = "用户登陆")
    @PostMapping("/login")
    @CrossOrigin
    public Result login(@RequestBody User user ) {
        User uu =userService.login(user.getUsername(),user.getPassword());
        Map<String, Object> info = new HashMap<String, Object>();
        //签发JWT的token
        String token = JwtUtil.sign(Long.toString(user.getId()), info);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("token",token);
        return Result.success(uu + "," + result);
    }
    //更改用户的状态
    @ApiOperation(value = "更改用户的状态",notes = "传递需要更改的用户的id和状态需要改为什么")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "用户的ID",name = "id"),
                    @ApiImplicitParam(value = "用户的状态",name="status")
            }
    )
    @GetMapping("/changeState")
    public Result changeState(Long id ,String status) {
        userService.updateUserStatus(id, status);
        return Result.success();
    }
    //查找所有用户信息
    @ApiOperation(value = "分页查询所有用户",notes = "一个整数类型的页码数")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "需要查看第几页的内容",name = "pageIndex")
            })
    @GetMapping("/findAllUser")
    public Result findAllUser(int pageIndex) {
        Page<User> page =userService.getAll(pageIndex, 2);
        return Result.success(page);
    }

    @ApiOperation(value = "单个删除用户",notes = "提供需要删除用户的Id")
    @DeleteMapping("/deleteUserById/{id}")
    public Result deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return Result.success("单个删除成功");
    }

    @ApiOperation(value = "批量删除用户",notes = "提供需要被删除用户的ID")
    @PostMapping("/deleteUserInBatch")
    public Result deleteAllUser(@RequestBody List<Long> list) {
        userService.deleteUserInBatch(list);
        return Result.success("删除成功");
    }
}

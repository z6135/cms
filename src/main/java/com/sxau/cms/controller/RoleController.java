package com.sxau.cms.controller;

import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Role;
import com.sxau.cms.service.RoleService;
import com.sxau.cms.util.Result;
import com.sxau.cms.util.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.controller.controller
 * @ClassName: RoleController
 * @Author: 张晟睿
 * @Date: 2022/1/9 9:29
 * @Version: 1.0
 */
@Api(tags = "角色模块")
@Controller
public class RoleController {
    @Autowired
    private RoleService service;
    //注册一个角色
    @ApiOperation(value = "注册角色" ,notes = "传递一个json格式的角色对象的字符串")
    @PostMapping("/saveRole")
    @ResponseBody
    public Result  saveRole(@RequestBody Role role) {
        //调用service代码完成注册的业务逻辑
        service.saveOrUpdateRole(role);
        return  Result.success(role);
    }
    //根据角色名称查找具体的角色信息（单个参数）
    @ApiOperation(value="查找单个角色信息",notes = "需要出入一个字符串类型的名字")
    @ApiImplicitParams(
            @ApiImplicitParam(value="角色名称",name="name")
    )
    @GetMapping("/findRoleByName")
    @ResponseBody
    public Result findRoleByName(String name) {
        Role role =service.findRoleByName(name);
        if(role==null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        return   Result.success(role);

    }
    //可以自己下去改进：接受参数表示我们想要查看第几页的数据
    @ApiOperation(value = "查找全部用户信息",notes = "不需要传递参数")
    @GetMapping("/findAllRole")
    @ResponseBody
    //查找所有的角色信息
    public Result findAllRole() {
        Page<Role> page =service.findAll(0, 2);
        return  Result.success(page);
    }
    @ApiOperation(value = "删除单个角色信息",notes = "传入被删除的用户的ID")
    //通过ID删除用户的信息
    @DeleteMapping("/delete/{roleId}")
    @ResponseBody
    public Result deleteRoleById(@PathVariable long roleId) {
        service.deleteRoleById(roleId);
        return Result.success("删除成功");
    }

    @ApiOperation(value = "删除多个角色信息",notes = "传入需要被删除角色的ID（集合或者数组格式的字符串）")
    @PostMapping("/deleteAll")
    @ResponseBody
    //通过ID删除用户的信息
    public Result deleteRole(@RequestBody List<Long> ids) {
        System.out.println(ids);
        service.deleteRoleInBatch(ids);
        return Result.success("删除成功");
    }

}

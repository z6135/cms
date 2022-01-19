package com.sxau.cms.service.impl;

import com.sxau.cms.mapper.RoleMapper;
import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Role;
import com.sxau.cms.service.RoleService;
import com.sxau.cms.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.service.impl
 * @ClassName: RoleServiceImpl
 * @Author: 张晟睿
 * @Date: 2022/1/9 9:25
 * @Version: 1.0
 */
@Service
public class RoleServiceImpl  implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page<Role> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
        PageRequest pg = PageRequest.of(pageNum, pageSize);
        Page<Role> page = roleMapper.findAll(pg);
        return page;
    }

    //角色是否能够保存在数据的一个业务逻辑判断
    //角色名称不能重复
    //角色名称不能为null
    @Override
    public void saveOrUpdateRole(Role role) throws ServiceException {
        //角色名称为null
        if (role.getName().equals(null) || role.getName().trim().equals("")) {
            throw new ServiceException(ResultCode.USER_NAME_ISNULL);
        }
        //角色名称在数据库中是不能重复的
        //通过角色名称去数据库中查找该角色是否已经被构建
        Role rr = findRoleByName(role.getName());
        if (rr != null) {
            throw new ServiceException(ResultCode.USER_HAS_EXISTED);
        }
        //注册角色
        roleMapper.save(role);
    }

    @Override
    public void deleteRoleInBatch(List<Long> ids) throws ServiceException {
        //从集合获取到每个被删除的角色ID
        for (Long id : ids) {
            roleMapper.deleteById(id);
        }
    }

    @Override
    public Role findRoleByName(String name) throws ServiceException {
        if (name == null || name.trim().equals("")) {
            throw new ServiceException(ResultCode.USER_NAME_ISNULL);
        }
        //数据库中查询这个角色
        Role role = roleMapper.findByName(name);
        return role;
    }

    @Override
    public void deleteRoleById(Long ids) throws ServiceException {
        //根据角色的ID查找有无该角色
        Role role = findRoleById(ids);
        //如果该ID对应的用户不存在那就不用删除
        if (role == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        roleMapper.deleteById(ids);
    }

    @Override
    public Role findRoleById(Long id) throws ServiceException {
        return roleMapper.findById(id).orElse(null);
    }
}


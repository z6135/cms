package com.sxau.cms.service.impl;

import com.sxau.cms.mapper.UserMapper;
import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.User;
import com.sxau.cms.service.UserService;
import com.sxau.cms.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.service.impl
 * @ClassName: UserServiceImpl
 * @Author: 张晟睿
 * @Date: 2022/1/9 9:25
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Page<User> getAll(Integer pageNum, Integer pageSize) throws ServiceException {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        Page<User> page =userMapper.findAll(pageRequest);
        return page;
    }

    @Override
    public void saveOrUpdateUser(User user) throws ServiceException {
        //选判断传递过来的用户的用户名和密码不能为null
        //非空
        if (user.getUsername()==null||user.getUsername().trim().equals("")){
            throw  new ServiceException(ResultCode.USER_NAME_ISNULL);
        }
        if (user.getPassword()==null||user.getPassword().trim().equals("")){
            throw  new ServiceException(ResultCode.USER_PASS_ISNULL);
        }
        //唯一
        User user1 = findUserByUsername(user.getUsername());
        if (user1!=null){
            throw new ServiceException(ResultCode.USER_HAS_EXISTED);
        }
        //表中没有要插入的User
        userMapper.save(user);
    }

    @Override
    public void deleteUserInBatch(List<Long> ids) throws ServiceException {
        for (Long id:ids){
            userMapper.deleteById(id);
        }
    }

    @Override
    public void deleteUserById(Long id) throws ServiceException {
        User user = findUserById(id);
        if (user==null){
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        userMapper.deleteById(id);
    }

    @Override
    public void updateUserStatus(Long id, String status) throws ServiceException {
        //先根据用户的id去数据库中查找到需要被更新的这个用户
        User user =userMapper.findById(id).orElse(null);
        if(user ==null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        //更改这个用户的状态
        user.setStatus(status);
        userMapper.save(user);

    }

    @Override
    public User login(String username, String password) throws ServiceException {
        if(username==null||username.trim().equals("")) {
            throw new ServiceException(ResultCode.USER_NAME_ISNULL);
        }
        if(password==null||password.trim().equals("")) {
            throw new ServiceException(ResultCode.USER_PASS_ISNULL);
        }
        //登陆的业务逻辑
        User user = findUserByUsername(username);
        if(user==null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        //比较密码 "123" "123"
        if(!user.getPassword().equals(password.trim())) {
            throw new ServiceException(ResultCode.USER_PASSWD_ERROR);
        }
        //用户状态为可用状态
        if(user.getStatus().equals("1")) {
            throw new ServiceException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }

        return user;
    }


    @Override
    public User findUserByUsername(String username) throws ServiceException {
        //非空
        if (username==null||username.trim().equals("")){
            throw  new ServiceException(ResultCode.USER_NAME_ISNULL);
        }

        return userMapper.findByUsername(username);
    }

    @Override
    public User findUserById(Long id) throws ServiceException {
        return userMapper.findById(id).orElse(null);
    }
}

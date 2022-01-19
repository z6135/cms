package com.sxau.cms.service;

import java.util.List;

import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.User;
import org.springframework.data.domain.Page;

public interface UserService {
	//分页获取所有用户信息
	Page<User> getAll(Integer pageNum,Integer pageSize)throws ServiceException;
	//新增用户信息或者更新学生信息
	void saveOrUpdateUser(User user)throws ServiceException;
	//批量删除用户信息
	void deleteUserInBatch(List<Long> ids)throws ServiceException;
	//单个删除用户信息
	void deleteUserById(Long id)throws ServiceException;

	//修改用户的状态
	void updateUserStatus(Long id,String status)throws ServiceException;

	//登录
	User login(String username,String password)throws ServiceException;
	//根据用户名获取用户信息
	User findUserByUsername(String username)throws ServiceException;
	//根据用户id获取用户信息
	User findUserById(Long id)throws ServiceException;
	
}

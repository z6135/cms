package com.sxau.cms.service;

import java.util.List;

import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Role;
import org.springframework.data.domain.Page;


public interface RoleService {
	//分页获取所有角色信息
	Page<Role> findAll(Integer pageNum, Integer pageSize)throws ServiceException;
	//新增角色信息或者更新角色信息
	void saveOrUpdateRole(Role role)throws ServiceException;
	//批量删除角色信息
	void deleteRoleInBatch(List<Long> ids)throws ServiceException;
	//根据角色id删除单个角色信息
	void deleteRoleById(Long ids)throws ServiceException;
	//根据角色查询角色信息
	Role  findRoleByName(String name) throws ServiceException;
	Role  findRoleById(Long id) throws ServiceException;
}

package com.sxau.cms.service;

import java.util.List;

import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Category;
import com.sxau.cms.pojo.Role;
import org.springframework.data.domain.Page;

public interface CategoryService {
	//分页获取所有分类
	Page<Category> findAll(Integer pageNum,Integer pageSize)throws ServiceException;
	//通过栏目的名字查找栏目信息
	Category findCategoryByName(String name) throws ServiceException;
	//新增分类或者更新分类
	void saveOrUpdateCategory(Category category)throws ServiceException;
	//批量删除分类
	void deleteCategoryInBatch(List<Long> ids)throws ServiceException;

	//根据角色id删除单个栏目信息
	void deleteCategoryById(Long ids)throws ServiceException;
	//按序号升序查询分类信息，并进行分页
	Page<Category> findAllSortbyno(Integer pageNum,Integer pageSize)throws ServiceException;

	//更新分类序号
	void updateCategoryNo(Long id,Integer no)throws ServiceException;

	Category  findCategoryById(Long id) throws ServiceException;
	
}

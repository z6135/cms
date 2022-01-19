package com.sxau.cms.service.impl;

import com.sxau.cms.mapper.CategoryMapper;
import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Category;
import com.sxau.cms.pojo.Role;
import com.sxau.cms.service.CategoryService;
import com.sxau.cms.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.service.impl
 * @ClassName: CategoryServiceImpl
 * @Author: 张晟睿
 * @Date: 2022/1/9 9:24
 * @Version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public Page<Category> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
        PageRequest pq=PageRequest.of(pageNum, pageSize);
        Page<Category> pg=categoryMapper.findAll(pq);
        return pg;
    }

    @Override
    public void saveOrUpdateCategory(Category category) throws ServiceException {
        //保存  更新
        String name= category.getName();
        if(name==null||name.trim().equals("")) {
            throw  new ServiceException(ResultCode.PARAM_IS_BLANK);
        }
        //更新还是保存
        Category cate=findCategoryByName(name);
        //如果cate为null我们就做保存工作
        if(cate==null) {
            categoryMapper.save(category);
        }else {
            //如果cate不为null我们就做更新操作
            cate.setDescription(category.getDescription());
            categoryMapper.save(cate);
        }

    }

    @Override
    public void deleteCategoryInBatch(List<Long> ids) throws ServiceException {
        for(long id :ids) {
            categoryMapper.deleteById(id);
        }
    }

    @Override
    public Page<Category> findAllSortbyno(Integer pageNum, Integer pageSize) throws ServiceException {
        PageRequest pq=PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "no"));
        Page<Category> pg=categoryMapper.findAll(pq);
        return pg;
    }

    @Override
    public void updateCategoryNo(Long id, Integer no) throws ServiceException {
        //通过id获栏目的信息
        Category cate = categoryMapper.findById(id).orElse(null);
        if(cate==null) {
            throw new ServiceException(ResultCode.USER_NAME_ISNULL);
        }
        cate.setNo(no);
        categoryMapper.save(cate);
    }

    @Override
    public Category findCategoryByName(String name) throws ServiceException {
        if (name == null || name.trim().equals("")) {
            throw new ServiceException(ResultCode.PARAM_IS_INVALID);
        }
        System.out.println("service="+name);
        Category category=categoryMapper.findByName(name);
        System.out.println("service="+category);
        return category;
    }

    @Override
    public void deleteCategoryById(Long ids) throws ServiceException {
        //根据栏目的ID查找有无该角色
        Category category = findCategoryById(ids);
        //如果该ID对应的栏目不存在那就不用删除
        if (category == null) {
            throw new ServiceException(ResultCode.Category_NOT_ISNULL);
        }
        categoryMapper.deleteById(ids);
    }

    @Override
    public Category findCategoryById(Long id) throws ServiceException {
        return categoryMapper.findById(id).orElse(null);
    }
}

package com.sxau.cms.controller;

import com.sxau.cms.pojo.Category;
import com.sxau.cms.service.CategoryService;
import com.sxau.cms.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.controller
 * @ClassName: CategoryController
 * @Author: 张晟睿
 * @Date: 2022/1/10 9:38
 * @Version: 1.0
 */
@Api(tags = "栏目模块")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService ;
    //保存栏目
    @ApiOperation(value = "栏目的添加",notes = "把栏目相关的信息添加进来")
    @PostMapping("/save")
    public Result  save(@RequestBody Category category) {
        System.out.println(category);
        categoryService.saveOrUpdateCategory(category);
        return  Result.success();
    }
    //根据栏目名称查询栏目信息
    @ApiOperation(value = "栏目的查询",notes = "根据栏目名称查询栏目信息")
    @ApiImplicitParams(
            @ApiImplicitParam(value = "栏目名称",name = "name"))
    @GetMapping("/findByName")
    public Result findByName(String name) {
        System.out.println(name);
        Category cate =categoryService.findCategoryByName(name);
        System.out.println(cate);
        return  Result.success(cate);
    }
    //查询所有的信息
    @ApiOperation(value = "查询所有的栏目",notes = "查询所有的栏目")
    @ApiImplicitParams(
            @ApiImplicitParam(value = "需要查看第几页的内容",name = "pageIndex"))
    @GetMapping("/findAll")
    public  Result findAll(Integer pageIndex) {
        Page<Category> cates =categoryService.findAll(pageIndex, 2);
        return Result.success(cates);
    }
    //查询到的所有的信息根据no进行排序
    @ApiOperation(value = "查询所有的栏目并按照no进行降序排列",notes = "查询所有的栏目")
    @ApiImplicitParams(
            @ApiImplicitParam(value = "需要查看第几页的内容",name = "pageIndex"))
    @GetMapping("/findAllSort")
    public  Result findAllSort(int pageIndex) {
        Page<Category> cates =categoryService.findAllSortbyno(pageIndex, 2);
        return Result.success(cates);
    }
    //删除所有的栏目的信息
    @ApiOperation(value = "传递需要被删除的栏目的信息",notes = "传递需要被删除的栏目的ID")
    @PostMapping("/deleteAll")
    public  Result delete(@RequestBody List<Long> list) {
        categoryService.deleteCategoryInBatch(list);
        return Result.success("删除成功");
    }
    //更新排序号   
    @ApiOperation(value = "修改no",notes = "查询所有的栏目")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "被修改的栏目的ID",name = "ID"),
                    @ApiImplicitParam(value = "修改no",name = "no")
            }
    )
    @GetMapping("/updateNo")
    public  Result findAllSort(long id,int no) {
        categoryService.updateCategoryNo(id, no);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "删除单个栏目信息",notes = "传入被删除的栏目的ID")
    //通过ID删除栏目的信息
    @DeleteMapping("/delete/{categoryId}")
    @ResponseBody
    public Result deleteRoleById(@PathVariable long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return Result.success("删除成功");
    }
}

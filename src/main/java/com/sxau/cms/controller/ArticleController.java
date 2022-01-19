package com.sxau.cms.controller;

import com.sxau.cms.mapper.ArticleMapper;
import com.sxau.cms.pojo.Article;
import com.sxau.cms.pojo.Category;
import com.sxau.cms.pojo.Comment;
import com.sxau.cms.service.ArticleService;
import com.sxau.cms.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.controller
 * @ClassName: ArticleController
 * @Author: 张晟睿
 * @Date: 2022/1/9 20:32
 * @Version: 1.0
 */
@Api(tags = "文章模块")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //查询所有的信息
    @ApiOperation(value = "查询所有的文章",notes = "查询所有的文章")
    @ApiImplicitParams(
            @ApiImplicitParam(value = "需要查看第几页的内容",name = "pageIndex"))
    @GetMapping("/findAll")
    public Result findAll(int pageIndex) {
        Page<Article> page = articleService.findAll(pageIndex, 2);
        return Result.success(page);
    }

    //保存文章
    @ApiOperation(value = "文章的添加",notes = "把文章相关的信息添加进来")
    @PostMapping("/save")
    public Result  save(@RequestBody Article article){
        articleService.saveOrUpdateArticle(article);
        return Result.success();
    }

    //删除所有的文章的信息
    @ApiOperation(value = "传递需要被删除的文章的信息",notes = "传递需要被删除的文章的ID")
    @PostMapping("/deleteAll")
    public  Result delete(@RequestBody List<Long> list) {
        articleService.deleteArticleInBatch(list);
        return Result.success("删除成功");
    }

    //更改用户的状态
    @ApiOperation(value = "更改文章的状态",notes = "传递需要更改的文章的id和状态需要改为什么")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "文章的ID",name = "id"),
                    @ApiImplicitParam(value = "文章的状态",name="status")
            }
    )
    @GetMapping("/changeState")
    public Result changeState(Long id ,String status) {
        articleService.updateArticleStatus(id, status);
        return Result.success();
    }


    //查询指定文章下的评论
    @ApiOperation(value = "查询指定文章下的评论",notes = "分页查询指定文章下的评论")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "文章Id",name = "articleId"),
                    @ApiImplicitParam(value = "当前文章页数",name = "pageNum"),
                    @ApiImplicitParam(value = "每页显示多少数据",name = "pageSize")
            }
    )
    @GetMapping("/findAllByArticleId")
    public Page<Comment> findAllByArticleId(Long articleId, Integer pageNum, Integer pageSize) {
        return articleService.findAllCommentsByArticleId(articleId,pageNum,pageSize);
    }

    @ApiOperation(value = "删除单个文章信息",notes = "传入被删除的文章的ID")
    //通过ID删除文章的信息
    @DeleteMapping("/delete/{articleId}")
    @ResponseBody
    public Result deleteArticleById(@PathVariable long articleId) {
        articleService.deleteArticleById(articleId);
        return Result.success("删除成功");
    }
}

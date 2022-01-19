package com.sxau.cms.controller;

import com.sxau.cms.pojo.Comment;
import com.sxau.cms.service.CommentService;
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
 * @ClassName: CommentController
 * @Author: 张晟睿
 * @Date: 2022/1/10 9:30
 * @Version: 1.0
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    //查询所有的信息
    @ApiOperation(value = "查询所有的评论",notes = "查询所有的评论")
    @ApiImplicitParams(
            @ApiImplicitParam(value = "需要查看第几页的内容",name = "pageIndex"))
    @GetMapping("/findAll")
    public Result findAll(int pageIndex) {
        Page<Comment> comments = commentService.findAll(pageIndex, 2);
        return  Result.success(comments);
    }

    @ApiOperation(value = "评论的添加",notes = "把评论相关的信息添加进来")
    @PostMapping("/save")
    public Result save(@RequestBody Comment comment){

        commentService.saveOrUpdateComment(comment);
       return Result.success();
    }

    //删除所有的栏目的信息
    @ApiOperation(value = "传递需要被删除的评论的信息,多个删除",notes = "传递需要被删除的评论的ID")
    @PostMapping("/deleteAll")
    public  Result delete(@RequestBody List<Long> list){
        commentService.deleteCommentInBatch(list);
        return Result.success("删除成功");
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
    public Page<Comment> findAllByArticleId(Long articleId,Integer pageNum, Integer pageSize) {
        return commentService.findAllByArticleId(articleId,pageNum,pageSize);
    }
    @ApiOperation(value = "删除单个评论信息",notes = "传入被删除的评论的ID")
    //通过ID删除评论的信息
    @DeleteMapping("/delete/{commentId}")
    @ResponseBody
    public Result deleteCommentById(@PathVariable long commentId) {
        commentService.deleteCommentById(commentId);
        return Result.success("删除成功");
    }
}

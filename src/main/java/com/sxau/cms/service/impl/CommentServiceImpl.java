package com.sxau.cms.service.impl;

import com.sxau.cms.mapper.CommentMapper;
import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Article;
import com.sxau.cms.pojo.Comment;
import com.sxau.cms.service.CommentService;
import com.sxau.cms.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.service.impl
 * @ClassName: CommentServiceImpl
 * @Author: 张晟睿
 * @Date: 2022/1/9 9:24
 * @Version: 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Page<Comment> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
        PageRequest pg = PageRequest.of(pageNum, pageSize);
        Page<Comment> page = commentMapper.findAll(pg);
        return page;
    }

    @Override
    public void saveOrUpdateComment(Comment comment) throws ServiceException {
        if (comment.getContent().equals(null) || comment.getContent().trim().equals("")) {
            throw new ServiceException(ResultCode.PARAM_IS_BLANK);
        }
        commentMapper.save(comment);
    }

    @Override
    public void deleteCommentInBatch(List<Long> ids) throws ServiceException {
        for (Long id: ids
             ) {
            commentMapper.deleteById(id);
        }
    }

    @Override
    public Page<Comment> findAllByArticleId(Long articleId, Integer pageNum, Integer pageSize) throws ServiceException {
        PageRequest pg = PageRequest.of(pageNum, pageSize);
        return commentMapper.findByArticleId(articleId,pg);
    }

    @Override
    public Comment findCommentById(Long id) throws ServiceException {
        return commentMapper.findById(id).orElse(null);
    }

    @Override
    public void deleteCommentById(Long ids) throws ServiceException {
        //根据评论的ID查找有无该角色
        Comment comment = findCommentById(ids);
        //如果该ID对应的评论不存在那就不用删除
        if (comment == null) {
            throw new ServiceException(ResultCode.Comment_NOT_ISNULL);
        }
        commentMapper.deleteById(ids);
    }

}

package com.sxau.cms.service;

import java.util.List;

import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Article;
import com.sxau.cms.pojo.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
	//分页获取所有评论
	Page<Comment> findAll(Integer pageNum, Integer pageSize)throws ServiceException;
	//新增评论或者更新评论
	void saveOrUpdateComment(Comment comment)throws ServiceException;
	//批量删除评论
	void deleteCommentInBatch(List<Long> ids)throws ServiceException;
	
	//根据资讯id分页获取所有评论
	Page<Comment> findAllByArticleId(Long articleId, Integer pageNum, Integer pageSize)throws ServiceException;

	Comment findCommentById(Long id) throws ServiceException;

	void deleteCommentById(Long ids)throws ServiceException;

}

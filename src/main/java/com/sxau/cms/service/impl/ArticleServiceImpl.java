package com.sxau.cms.service.impl;

import com.sxau.cms.mapper.ArticleMapper;
import com.sxau.cms.exception.ServiceException;
import com.sxau.cms.pojo.Article;
import com.sxau.cms.pojo.Category;
import com.sxau.cms.pojo.Comment;
import com.sxau.cms.service.ArticleService;
import com.sxau.cms.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.service.impl
 * @ClassName: ArticleServiceImpl
 * @Author: 张晟睿
 * @Date: 2022/1/9 9:24
 * @Version: 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void saveOrUpdateArticle(Article article) throws ServiceException {
        if (article.getContent().equals(null) || article.getContent().trim().equals("")) {
            throw new ServiceException(ResultCode.PARAM_IS_BLANK);
        }
        articleMapper.save(article);
    }

    @Override
    public Page<Article> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
        PageRequest pg = PageRequest.of(pageNum, pageSize);
        Page<Article> page = articleMapper.findAll(pg);
        return page;
    }

    @Override
    public void deleteArticleInBatch(List<Long> ids) throws ServiceException {
        for (Long id : ids) {
            articleMapper.deleteById(id);
        }
    }

    @Override
    public void updateArticleStatus(Long id, String status) throws ServiceException {
        Article article = articleMapper.findById(id).orElse(null);
        article.setStatus(status);
        articleMapper.save(article);
    }

    @Override
    public Page<Comment> findAllCommentsByArticleId(Long articleId, int pageNum, int pageSize) throws ServiceException {
        PageRequest pg = PageRequest.of(pageNum, pageSize);
        return articleMapper.findByCommentList(articleId,pg);
    }

    @Override
    public Article findArticleById(Long id) throws ServiceException {
        return articleMapper.findById(id).orElse(null);
    }

    @Override
    public void deleteArticleById(Long ids) throws ServiceException {
        //根据文章的ID查找有无该角色
        Article article = findArticleById(ids);
        //如果该ID对应的文章不存在那就不用删除
        if (article == null) {
            throw new ServiceException(ResultCode.Article_NOT_ISNULL);
        }
        articleMapper.deleteById(ids);
    }
}

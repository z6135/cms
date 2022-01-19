package com.sxau.cms.mapper;

import com.sxau.cms.pojo.Article;
import com.sxau.cms.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleMapper extends JpaRepository<Article, Long>{
    Page<Comment> findByCommentList(Long id, Pageable pageable);
}

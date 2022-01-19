package com.sxau.cms.mapper;

import com.sxau.cms.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentMapper extends JpaRepository<Comment, Long> {
	
	Page<Comment> findByArticleId(Long id,Pageable pageable);
}

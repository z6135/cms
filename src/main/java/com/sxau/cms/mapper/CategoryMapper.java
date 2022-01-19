package com.sxau.cms.mapper;

import com.sxau.cms.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryMapper extends JpaRepository<Category, Long> {
    public Category findByName(String name);
}

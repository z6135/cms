package com.sxau.cms.mapper;

import com.sxau.cms.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMapper extends JpaRepository<User, Long> {

	public User findByUsername(String name);
}

package com.sxau.cms.mapper;

import com.sxau.cms.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMapper extends JpaRepository<Role, Long>{
	public Role findByName(String name);
}

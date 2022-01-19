package com.sxau.cms;

import com.sxau.cms.mapper.ArticleMapper;
import com.sxau.cms.pojo.Article;
import com.sxau.cms.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmsApplicationTests {
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleMapper articleMapper;
	@Test
	void contextLoads() {

	}

	@Test
	void test1(){
	}

}

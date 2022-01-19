package com.sxau.cms.interceptor;

import com.sxau.cms.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.interceptor
 * @ClassName: JwtInterceptor
 * @Author: 张晟睿
 * @Date: 2022/1/10 16:33
 * @Version: 1.0
 */

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object object) {
// 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
// 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        if (token == null) {
            throw new RuntimeException("无token，请重新登录");
        }
// 验证 token
        JwtUtil.checkSign(token);
// 验证通过后，这里测试取出JWT中存放的数据
// 获取 token 中的 userId
        String userId = JwtUtil.getUserId(token);
        System.out.println("id:" + userId);
// 获取 token 中的 其他数据
        Map<String, Object> info = JwtUtil.getInfo(token);
        info.forEach((k,v)->System.out.println(k+":"+v));
        return true;
    }
    }

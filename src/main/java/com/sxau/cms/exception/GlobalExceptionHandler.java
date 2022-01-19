package com.sxau.cms.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sxau.cms.util.Result;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Object handleException(Exception e) {
		Result result = null;
		if(e instanceof ServiceException) {
			result = Result.failure(((ServiceException) e).getResultCode());
		}
		else {
			result = Result.failure(500, "服务器意外错误："+e.getMessage());
		}
		return result;
	}
}

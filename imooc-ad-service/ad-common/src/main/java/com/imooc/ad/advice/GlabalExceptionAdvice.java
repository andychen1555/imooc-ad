package com.imooc.ad.advice;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.CommonResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlabalExceptionAdvice {

  @ExceptionHandler(AdException.class)
  public CommonResponse<String> handlerAdException(HttpServletRequest req, AdException ex){
    CommonResponse<String> response = new CommonResponse<>(-1, "bussiness error!");
    response.setData(ex.getMessage());
    return response;
  }
}

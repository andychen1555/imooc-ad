package com.imooc.ad.exception;

/**
 * 自定义业务异常 继承 统一异常
 */
public class AdException extends Exception {
  public AdException(String message){
    super(message);
  }
}

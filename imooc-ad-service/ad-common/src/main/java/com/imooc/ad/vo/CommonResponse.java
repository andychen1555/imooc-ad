package com.imooc.ad.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应 vo
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
  private Integer code;
  private String message;
  private T data;

  /**
   * 带参构造
   * @param code
   * @param message
   */
  public CommonResponse(Integer code, String message){
    this.code = code;
    this.message= message;
  }
}

package com.imooc.ad.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {

  private Long userId;
  private String username;
  private String token;
  private Date createTime;
  private Date updateTime;
}

package com.imooc.ad.client.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlan {

  private Long id;
  private Long userId;
  private String planName;
  private Integer planStatus;
  private Date startDate;
  private Date endDate;
  private Date createTime;
  private Date updateTime;
}

package com.imooc.ad.client.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetRequest {

  private Long userId;
  private List<Long> ids;

}

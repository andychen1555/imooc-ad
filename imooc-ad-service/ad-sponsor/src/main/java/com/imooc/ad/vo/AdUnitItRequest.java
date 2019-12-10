package com.imooc.ad.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItRequest {

  private List<UnitIt> unitIts;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UnitIt {

    private Long unitId;
    private String itTag;
  }
}

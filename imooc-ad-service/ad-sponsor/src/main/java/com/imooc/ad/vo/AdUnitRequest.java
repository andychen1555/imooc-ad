package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

  private Long planId;
  private String unitName;

  private Integer positionType;
  private Long budget;

  public boolean createValidate() {
    return null != planId
        && StringUtils.isNotBlank(unitName)
        && null != positionType
        && null != budget;
  }
}

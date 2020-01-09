package com.imooc.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 倒排索引  根据关键词 获取对应出现的位置
 */
public class UnitKeywordObject {

  private Long unitId;
  private String keyword;
}

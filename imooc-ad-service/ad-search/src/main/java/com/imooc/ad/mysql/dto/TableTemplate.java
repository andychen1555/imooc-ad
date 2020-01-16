package com.imooc.ad.mysql.dto;

import com.imooc.ad.mysql.constant.OpType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 方便操作时读取表的一些信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

  private String tableName;
  private String level;
  /**
   * 操作类型，字段顺序：对应多个列
   */
  private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

  /**
   * 字段索引 -> 字段名 映射
   * */
  private Map<Integer, String> posMap = new HashMap<>();
}

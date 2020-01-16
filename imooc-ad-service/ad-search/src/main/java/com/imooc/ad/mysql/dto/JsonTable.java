package com.imooc.ad.mysql.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * binlog 监听对应 json 表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

  private String tableName;
  private Integer level;

  private List<Column> insert;
  private List<Column> update;
  private List<Column> delete;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Column {

    private String column;
  }
}

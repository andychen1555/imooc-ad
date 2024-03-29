package com.imooc.ad.mysql.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

  private String database;
  private List<JsonTable> tableList;
}

package com.imooc.ad.index;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.dump.DConstant;
import com.imooc.ad.dump.table.AdCreativeTable;
import com.imooc.ad.dump.table.AdCreativeUnitTable;
import com.imooc.ad.dump.table.AdPlanTable;
import com.imooc.ad.dump.table.AdUnitDistrictTable;
import com.imooc.ad.dump.table.AdUnitItTable;
import com.imooc.ad.dump.table.AdUnitKeywordTable;
import com.imooc.ad.dump.table.AdUnitTable;
import com.imooc.ad.handler.AdLevelDataHandler;
import com.imooc.ad.mysql.constant.OpType;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 系统启动时自动加载 data 文件中的索引数据
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

  /**
   * 被注解的方法，在对象加载完依赖注入后执行。
   * 全量索引导入
   */
  @PostConstruct
  public void init() {
    /**
     * 构建 adPlan 索引
     */
    List<String> adPlanStrings = loadDumpData(
        String.format("%s%s",
                      DConstant.DATA_ROOT_DIR,
                      DConstant.AD_PLAN
        )
    );

    adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
        JSON.parseObject(p, AdPlanTable.class),
        OpType.ADD
    ));

    /**
     * 构建 adCreative 索引
     */
    List<String> adCreativeStrings = loadDumpData(
        String.format("%s%s",
                      DConstant.DATA_ROOT_DIR,
                      DConstant.AD_CREATIVE
        )
    );

    adCreativeStrings.forEach(
        c -> AdLevelDataHandler.handleLevel2(
            JSON.parseObject(c, AdCreativeTable.class),
            OpType.ADD
        )
    );
    /**
     * 构建 adUnit 索引
     */
    List<String> adUnitStrings = loadDumpData(
        String.format("%s%s",
                      DConstant.DATA_ROOT_DIR,
                      DConstant.AD_UNIT)
    );
    adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(
        JSON.parseObject(u, AdUnitTable.class),
        OpType.ADD
    ));
    /**
     * 构建 adCreativeUnit 索引
     */
    List<String> adCreativeUnitStrings = loadDumpData(
        String.format("%s%s",
                      DConstant.DATA_ROOT_DIR,
                      DConstant.AD_CREATIVE_UNIT)
    );
    adCreativeUnitStrings.forEach(cu -> AdLevelDataHandler.handleLevel3(
        JSON.parseObject(cu, AdCreativeUnitTable.class),
        OpType.ADD
    ));
    /**
     * 构建 adUnitDistrict 索引
     */
    List<String> adUnitDistrictStrings = loadDumpData(
        String.format("%s%s",
                      DConstant.DATA_ROOT_DIR,
                      DConstant.AD_UNIT_DISTRICT)
    );
    adUnitDistrictStrings.forEach(d -> AdLevelDataHandler.handleLevel4(
        JSON.parseObject(d, AdUnitDistrictTable.class),
        OpType.ADD
    ));
    /**
     * 构建 adUnitIt 索引
     */
    List<String> adUnitItStrings = loadDumpData(
        String.format("%s%s",
                      DConstant.DATA_ROOT_DIR,
                      DConstant.AD_UNIT_IT)
    );
    adUnitItStrings.forEach(i -> AdLevelDataHandler.handleLevel4(
        JSON.parseObject(i, AdUnitItTable.class),
        OpType.ADD
    ));
    /**
     * 构建 adUnitKeyword 索引
     */
    List<String> adUnitKeywordStrings = loadDumpData(
        String.format("%s%s",
                      DConstant.DATA_ROOT_DIR,
                      DConstant.AD_UNIT_KEYWORD)
    );
    adUnitKeywordStrings.forEach(k -> AdLevelDataHandler.handleLevel4(
        JSON.parseObject(k, AdUnitKeywordTable.class),
        OpType.ADD
    ));
  }

  /**
   * 读取 data 文件，进行序列化
   */
  private List<String> loadDumpData(String fileName) {
    try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
      return br.lines().collect(Collectors.toList());
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}

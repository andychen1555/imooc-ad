package com.imooc.ad.index.keyword;

import com.imooc.ad.index.IndexAware;
import com.imooc.ad.utils.CommonUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * IndexAware<String, Set<Long>>
 * K：关键词
 * V：关键词出现的 position
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

  //  keyword    unitIds<Set>
  private static Map<String, Set<Long>> keywordUnitMap;
  // unitId      keyWords<Set>
  private static Map<Long, Set<String>> unitKeywordMap;

  static {
    keywordUnitMap = new ConcurrentHashMap<>();
    unitKeywordMap = new ConcurrentHashMap<>();
  }

  @Override
  public Set<Long> get(String key) {

    if (StringUtils.isEmpty(key)) {
      return Collections.emptySet();
    }

    Set<Long> result = keywordUnitMap.get(key);
    if (result == null) {
      return Collections.emptySet();
    }

    return result;
  }

  @Override
  public void add(String key, Set<Long> value) {
    log.info("UnitKeywordIndex, before add: {}", unitKeywordMap);
    /**
     * Map 中的 key 不存在时，帮助 key new 一个新的 value
     * 如果 存储 倒排索引的 set 存在，直接取出。否则 new ConcurrentSkipListSet
     */
    Set<Long> unitIdSet = CommonUtils.getorCreate(
        key, keywordUnitMap,
        ConcurrentSkipListSet::new
    );
    unitIdSet.addAll(value);

    for (Long unitId : value) {
      Set<String> keywordSet = CommonUtils.getorCreate(
          unitId, unitKeywordMap,
          ConcurrentSkipListSet::new
      );
      keywordSet.add(key);
    }
    log.info("UnitKeywordIndex, after add: {}", unitKeywordMap);
  }

  @Override
  public void update(String key, Set<Long> value) {
    log.error("keyword index can not support update");
  }

  @Override
  public void delete(String key, Set<Long> value) {
    log.info("UnitKeywordIndex, before delete: {}", unitKeywordMap);
    Set<Long> keywordSet = CommonUtils.getorCreate(
        key, keywordUnitMap,
        ConcurrentSkipListSet::new
    );
    keywordSet.removeAll(value);
    for (Long unitId : value) {
      Set<String> keywords = CommonUtils.getorCreate(
          unitId, unitKeywordMap,
          ConcurrentSkipListSet::new);
      keywords.remove(key);
    }
    log.info("UnitKeywordIndex, before delete: {}", unitKeywordMap);
  }

  /**
   * 根据 传入的 unitId 和 keywords， 判断某一推广单元 是否存在这些 关键词
   * @param unitId
   * @param keywords
   * @return
   */
  public boolean match(Long unitId, List<String> keywords) {
    if (unitKeywordMap.containsKey(unitId) &&
        CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {
      Set<String> unitKeyWords = unitKeywordMap.get(unitId);
      return CollectionUtils.isSubCollection(keywords, unitKeyWords);
    }
    return false;
  }
}

package com.imooc.ad.index.adunit;

import com.imooc.ad.index.IndexAware;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

  private static Map<Long, AdUnitObject> objectMap;

  static {
    objectMap = new ConcurrentHashMap<>();
  }

  @Override
  public AdUnitObject get(Long key) {
    return objectMap.get(key);
  }

  @Override
  public void add(Long key, AdUnitObject value) {
    log.info("before add: {}", objectMap);
    objectMap.put(key, value);
    log.info("after add: {}", objectMap);
  }

  @Override
  public void update(Long key, AdUnitObject value) {
    log.info("before update: {}", objectMap);

    AdUnitObject oldObject = objectMap.get(key);
    if (null == oldObject) {
      objectMap.put(key, value);
    } else {
      oldObject.update(value);
    }

    log.info("after update: {}", objectMap);
  }

  @Override
  public void delete(Long key, AdUnitObject value) {
    log.info("before delete: {}", objectMap);
    objectMap.remove(key);
    log.info("after delete: {}", objectMap);
  }
}

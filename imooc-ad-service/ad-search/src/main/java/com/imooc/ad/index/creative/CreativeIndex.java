package com.imooc.ad.index.creative;

import com.imooc.ad.index.IndexAware;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

  private static Map<Long, CreativeObject> objectMap;

  static {
    objectMap = new ConcurrentHashMap<>();
  }

  @Override
  public CreativeObject get(Long key) {
    return objectMap.get(key);
  }

  @Override
  public void add(Long key, CreativeObject value) {
    log.info("before add: {}", objectMap);
    objectMap.put(key, value);
    log.info("after add: {}", objectMap);
  }

  @Override
  public void update(Long key, CreativeObject value) {
    log.info("before update: {}", objectMap);

    CreativeObject oldObject = objectMap.get(key);
    if (null == oldObject) {
      objectMap.put(key, value);
    } else {
      oldObject.update(value);
    }

    log.info("after update: {}", objectMap);
  }

  @Override
  public void delete(Long key, CreativeObject value) {
    log.info("before delete: {}", objectMap);
    objectMap.remove(key);
    log.info("after delete: {}", objectMap);
  }
}

package com.imooc.ad.dao;

import com.imooc.ad.entity.AdUnit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

  AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

  List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}

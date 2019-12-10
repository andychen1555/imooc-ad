package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.constant.Constants.ErrorMsg;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.imooc.ad.dao.unit_condition.AdUnitItRepository;
import com.imooc.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.imooc.ad.dao.unit_condition.CreativeUnitRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUnit;
import com.imooc.ad.entity.unit_condition.AdUnitDistrict;
import com.imooc.ad.entity.unit_condition.AdUnitIt;
import com.imooc.ad.entity.unit_condition.AdUnitKeyword;
import com.imooc.ad.entity.unit_condition.CreativeUnit;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitService;
import com.imooc.ad.vo.AdUnitDistrictRequest;
import com.imooc.ad.vo.AdUnitDistrictResponse;
import com.imooc.ad.vo.AdUnitItRequest;
import com.imooc.ad.vo.AdUnitItRequest.UnitIt;
import com.imooc.ad.vo.AdUnitItResponse;
import com.imooc.ad.vo.AdUnitKeywordRequest;
import com.imooc.ad.vo.AdUnitKeywordRequest.UnitKeyword;
import com.imooc.ad.vo.AdUnitKeywordResponse;
import com.imooc.ad.vo.AdUnitRequest;
import com.imooc.ad.vo.AdUnitResponse;
import com.imooc.ad.vo.CreativeUnitRequest;
import com.imooc.ad.vo.CreativeUnitRequest.CreativeUnitItem;
import com.imooc.ad.vo.CreativeUnitResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AdUnitServiceImpl implements IAdUnitService {

  private final AdUnitRepository unitRepository;
  private final AdPlanRepository planRepository;

  private final AdUnitDistrictRepository unitDistrictRepository;
  private final AdUnitKeywordRepository unitKeywordRepository;
  private final AdUnitItRepository unitItRepository;

  private final CreativeRepository creativeRepository;
  private final CreativeUnitRepository creativeUnitRepository;

  @Autowired
  public AdUnitServiceImpl(AdUnitItRepository unitItRepository,
                           AdUnitRepository unitRepository,
                           AdPlanRepository planRepository,
                           AdUnitDistrictRepository unitDistrictRepository,
                           AdUnitKeywordRepository unitKeywordRepository,
                           AdUnitItRepository unitItRepository1,
                           CreativeRepository creativeRepository,
                           CreativeUnitRepository creativeUnitRepository) {
    this.unitRepository = unitRepository;
    this.planRepository = planRepository;
    this.unitDistrictRepository = unitDistrictRepository;
    this.unitKeywordRepository = unitKeywordRepository;
    this.unitItRepository = unitItRepository1;
    this.creativeRepository = creativeRepository;
    this.creativeUnitRepository = creativeUnitRepository;
  }

  @Override
  public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
    if (!request.createValidate()) {
      throw new AdException(ErrorMsg.REQUEST_PARAM_ERROR);
    }
    Optional<AdPlan> oldPlan = planRepository.findById(request.getPlanId());
    if (!oldPlan.isPresent()) {
      throw new AdException(ErrorMsg.CAN_NOT_FIND_RECORD);
    }
    AdUnit oldUnit = unitRepository
        .findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
    if (null != oldUnit) {
      throw new AdException(ErrorMsg.SAME_NAME_UNIT_ERROR);
    }
    AdUnit newAdUnit = unitRepository.save(AdUnit.builder()
                                               .planId(request.getPlanId())
                                               .unitName(request.getUnitName())
                                               .positionType(request.getPositionType())
                                               .budget(request.getBudget()).build());
    return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
  }

  @Override
  public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
    List<Long> unitIds = request.getUnitKeywords()
        .stream()
        .map(UnitKeyword::getUnitId)
        .collect(Collectors.toList());
    if (!isRelatedUnitExist(unitIds)) {
      throw new AdException(ErrorMsg.REQUEST_PARAM_ERROR);
    }
    List<AdUnitKeyword> unitKeywords = new ArrayList<>();
    if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
      request.getUnitKeywords()
          .forEach(i -> unitKeywords.add(new AdUnitKeyword(i.getUnitId(), i.getKeyword())));
    }
    List<Long> ids = unitKeywordRepository.saveAll(unitKeywords)
        .stream()
        .map(AdUnitKeyword::getId)
        .collect(Collectors.toList());

    return new AdUnitKeywordResponse(ids);
  }


  @Override
  public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
    List<Long> unitIds = request.getUnitIts()
        .stream()
        .map(UnitIt::getUnitId)
        .collect(Collectors.toList());
    if (!isRelatedUnitExist(unitIds)) {
      throw new AdException(ErrorMsg.REQUEST_PARAM_ERROR);
    }
    List<AdUnitIt> unitIts = new ArrayList<>();
    if (!CollectionUtils.isEmpty(request.getUnitIts())) {
      request.getUnitIts().forEach(i -> unitIts.add(new AdUnitIt(i.getUnitId(), i.getItTag())));
    }
    List<Long> ids = unitItRepository.saveAll(unitIts)
        .stream()
        .map(AdUnitIt::getId)
        .collect(Collectors.toList());
    return new AdUnitItResponse(ids);
  }

  @Override
  public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
    List<Long> unitIds = request.getUnitDistricts()
        .stream()
        .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
        .collect(Collectors.toList());
    if (!isRelatedUnitExist(unitIds)) {
      throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
    }
    List<AdUnitDistrict> unitDistricts = new ArrayList<>();
    request.getUnitDistricts().forEach(d -> unitDistricts.add(
        new AdUnitDistrict(d.getUnitId(), d.getProvince(),
                           d.getCity())
    ));
    List<Long> ids = unitDistrictRepository.saveAll(unitDistricts)
        .stream().map(AdUnitDistrict::getId)
        .collect(Collectors.toList());

    return new AdUnitDistrictResponse(ids);
  }

  @Override
  public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
    List<Long> unitIds = request.getUnitItems().stream().map(CreativeUnitItem::getUnitId)
        .collect(Collectors.toList());
    List<Long> creativeIds = request.getUnitItems().stream().map(CreativeUnitItem::getCreativeId)
        .collect(Collectors.toList());
    if (!isRelatedUnitExist(unitIds) && !isRelatedCreativeExist(creativeIds)) {
      throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
    }
    List<CreativeUnit> creativeUnits = new ArrayList<>();
    request.getUnitItems().forEach(i -> creativeUnits.add(
        new CreativeUnit(i.getCreativeId(), i.getUnitId())
    ));
    List ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId)
        .collect(Collectors.toList());
    return new CreativeUnitResponse(ids);
  }

  private boolean isRelatedCreativeExist(List<Long> creativeIds) {
    if (CollectionUtils.isEmpty(creativeIds)) {
      return false;
    }
    return creativeRepository.findAllById(creativeIds).size() ==
        new HashSet<>(creativeIds).size();
  }

  private boolean isRelatedUnitExist(List<Long> unitIds) {
    if (CollectionUtils.isEmpty(unitIds)) {
      return false;
    }
    return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
  }
}

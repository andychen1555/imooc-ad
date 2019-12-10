package com.imooc.ad.service.impl;

import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdPlanService;
import com.imooc.ad.utils.CommonUtils;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AdPlanServiceImpl implements IAdPlanService {

  private final AdPlanRepository adPlanRepository;
  private final AdUserRepository adUserRepository;

  @Autowired
  public AdPlanServiceImpl(AdPlanRepository adPlanRepository,
                           AdUserRepository adUserRepository) {
    this.adPlanRepository = adPlanRepository;
    this.adUserRepository = adUserRepository;
  }

  @Override
  @Transactional(rollbackFor = {AdException.class})
  public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
    if (!request.createValidate()) {
      throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
    }

    Optional<AdUser> adUser = adUserRepository.findById(request.getUserId());
    if (!adUser.isPresent()) {
      throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
    }
    AdPlan oldPlan = adPlanRepository
        .findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
    if (null != oldPlan) {
      throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
    }
    AdPlan newPlan = adPlanRepository.save(AdPlan.builder()
                                               .userId(request.getUserId())
                                               .planName(request.getPlanName())
                                               .startDate(CommonUtils.parseStringDate(request
                                                                                          .getStartDate()))
                                               .endDate(CommonUtils
                                                            .parseStringDate(request.getEndDate()))
                                               .build());
    return new AdPlanResponse(newPlan.getId(), newPlan.getPlanName());
  }

  @Override
  public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
    if (!request.validate()) {
      throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
    }
    return adPlanRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
  }

  @Override
  @Transactional(rollbackFor = {AdException.class})
  public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
    if (!request.updateValidate()) {
      throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
    }

    AdPlan adPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
    if (null == adPlan) {
      throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
    }
    if (StringUtils.isNotBlank(request.getPlanName())) {
      adPlan.setPlanName(request.getPlanName());
    }
    if (StringUtils.isNotBlank(request.getStartDate())) {
      adPlan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
    }
    if (StringUtils.isNotBlank(request.getEndDate())) {
      adPlan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
    }
    adPlan.setUpdateTime(new Date());
    adPlan = adPlanRepository.save(adPlan);
    return new AdPlanResponse(adPlan.getId(), adPlan.getPlanName());
  }

  @Override
  @Transactional
  public void deleteAdPlan(AdPlanRequest request) throws AdException {
    if (!request.deleteValidate()) {
      throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
    }
    AdPlan adPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
    if (null == adPlan) {
      throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
    }
    adPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
    adPlan.setUpdateTime(new Date());
    adPlanRepository.save(adPlan);
  }
}

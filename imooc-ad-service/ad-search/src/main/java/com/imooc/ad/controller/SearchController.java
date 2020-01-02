package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.annotation.IgnoreResponseAdvice;
import com.imooc.ad.client.SponsorClient;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class SearchController {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  SponsorClient sponsorClient;

  @IgnoreResponseAdvice
  @PostMapping("/getAdPlands")
  public CommonResponse<List<AdPlan>> getAdPlands(@RequestBody AdPlanGetRequest request){
    log.info("ad-search: getAdPlans -> {}",
             JSON.toJSONString(request));
    return sponsorClient.getAdPlans(request);
  }

  @SuppressWarnings(value = "all")
  @IgnoreResponseAdvice
  @PostMapping("/getAdPlansByRibbon")
  public CommonResponse<List<AdPlan>> getAdPlandsByRebbon(
      @RequestBody AdPlanGetRequest request
  ) {
    log.info("ad-search: getAdPlansByRibbon -> {}",
             JSON.toJSONString(request));
    return restTemplate.postForEntity(
        "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
        request,
        CommonResponse.class
    ).getBody();
  }
}

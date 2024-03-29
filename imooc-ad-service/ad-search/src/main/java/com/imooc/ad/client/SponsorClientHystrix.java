package com.imooc.ad.client;

import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SponsorClientHystrix implements SponsorClient {

  @Override
  public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
    return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
  }
}

package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IUserService;
import com.imooc.ad.utils.CommonUtils;
import com.imooc.ad.vo.CreateUserRequest;
import com.imooc.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

  private final AdUserRepository adUserRepository;

  @Autowired
  public UserServiceImpl(AdUserRepository adUserRepository) {
    this.adUserRepository = adUserRepository;
  }

  @Override
  @Transactional
  public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
    if (!request.validate()) {
      throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
    }
    AdUser oldUser = adUserRepository.findByUsername(request.getUsername());
    if (null != oldUser) {
      throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
    }
    AdUser newUser = adUserRepository
        .save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));
    return CreateUserResponse.builder()
        .userId(newUser.getId())
        .username(newUser.getUsername())
        .token(newUser.getToken())
        .createTime(newUser.getCreateTime())
        .updateTime(newUser.getUpdateTime())
        .build();
  }
}

package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.ApplyUserFilter;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  ResponseMessage<BaseResult> getList(Filter filter);

  ResponseMessage<BaseResult> getOne(Long id);

  ResponseMessage<BaseResult> insert(User user, MultipartFile file);

  ResponseMessage<BaseResult> update(User user, MultipartFile file);

  ResponseMessage<String> changePassword(String oldPassword, String newPassword);

  ResponseMessage<BaseResult> updateUser(User user, MultipartFile file); // Edit profile

  ResponseMessage<String> delete(Long id);

  ResponseMessage<BaseResult> getListApplyUser(ApplyUserFilter filter);

  ResponseMessage<User> me();

  User getUserAuth();

}
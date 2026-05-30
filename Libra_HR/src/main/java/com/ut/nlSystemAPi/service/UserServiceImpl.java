package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.FileUploadUtils;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.UserMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.response.ApplyUserFilter;
import com.ut.nlSystemAPi.model.response.ApplyUserList;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@SuppressWarnings("all")
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  Environment environment;

  @Autowired
  private MessageService messageService;

  @Autowired
  private MessageSource messageSource;

  public ResponseMessage<BaseResult> getList(Filter filter) {
    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(userMapper.countList(filter));

    if (filter != null) {
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
    }
    List<User> userList = userMapper.getList(filter);

    if(userList != null && userList.size() > 0){
      for(int i = 0; i < userList.size(); i++){
        userList.get(i).setUserGroupLists(userMapper.getOneUserGroupList(userList.get(i).getId()));
        userList.get(i).setUserDepartmentLists(userMapper.getUserApplyDepartment(userList.get(i).getId()));
      }
    }

    makeHidden(userList);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", userList, true));
  }

  public ResponseMessage<BaseResult> getOne(Long id) {
    List<User> userList = userMapper.getOne(id);

    if(userList != null && userList.size() > 0){
      for(int i = 0; i < userList.size(); i++){
        userList.get(i).setUserGroupLists(userMapper.getOneUserGroupList(userList.get(i).getId()));
        userList.get(i).setUserDepartmentLists(userMapper.getUserApplyDepartment(userList.get(i).getId()));
      }
    }

    makeHidden(userList);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", userList, true));
  }

  public ResponseMessage<BaseResult> insert(User user, MultipartFile file) {
    // Check Permission
    Long userId = getUserAuth().getId();

    List<User> existingUserList = userMapper.getOneByUsername(user.getUsername());
    if (existingUserList.size() > 0) {
      // TODO: localize
      return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate User", false));
    }

    //Password
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Photo User
    if(file != null){
      String filename = FileUploadUtils.saveFileUploaded(file);
      user.setPhoto(filename);
    }
    // Check Data
    user.setIsActive(1);
    user.setCreatedBy(userId);

    Boolean result = userMapper.insert(user);
    if (result) {
      if (user.getUserGroup().length > 0){
        insertSystemRoleUser(user.getUserGroup(), user.getId());
      }

      // insert apply department
      if (user.getApplyDepartment().length > 0 || user.getApplyDepartment().length == 0){
        insertApplyUserDepartment(user.getApplyDepartment(), user.getId());
      }

      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    } else {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }
  }

  public ResponseMessage<BaseResult> update(User user, MultipartFile file) {
    //Check Permission
    Long userId = getUserAuth().getId();

    if (user.getPassword() != null && user.getPassword() != "") {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    // User Photo
    if(file != null){
      String filename = FileUploadUtils.saveFileUploaded(file);
      user.setPhoto(filename);
    }

    // Check Data
    user.setModifiedBy(getUserAuth().getId());
    user.setIsActive(1);

    Boolean result = userMapper.update(user);
    if (result) {
      if (user.getUserGroup().length>0){
          insertSystemRoleUser(user.getUserGroup(), user.getId());
      }
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    } else {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }
  }

  public ResponseMessage<BaseResult> updateUser(User user, MultipartFile file) {
    //Check Permission
    Long userId = getUserAuth().getId();

    //Photo User
    if(file != null){
      String filename = FileUploadUtils.saveFileUploaded(file);
      user.setPhoto(filename);
    }

    //Check Data
    user.setModifiedBy(getUserAuth().getId());
    user.setIsActive(1);

    Boolean result = userMapper.updateUser(user);
    if (result) {
      // update apply user to departments
      if (user.getApplyDepartment().length > 0 || user.getApplyDepartment().length == 0){
        insertApplyUserDepartment(user.getApplyDepartment(), user.getId());
        userMapper.updateUserApplyDepartment(user.getId());
      }

      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    } else {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }
  }

  public ResponseMessage<String> delete(Long id) {
    if(id == 1) {
      return ResponseMessageUtils.makeResponse(true, messageSource.getMessage("message.data.admin.en", null, Locale.getDefault()));
    }

    Boolean result = userMapper.delete(id);
    if (result) {
      String msgEntityname = messageSource.getMessage("user.entityname", null, Locale.getDefault());
      String msgSuccess = messageSource.getMessage("form.delete.success", null, Locale.getDefault());
      return ResponseMessageUtils.makeResponse(true, msgEntityname + " " + msgSuccess);
    } else {
      String msgEntityname = messageSource.getMessage("user.entityname", null, Locale.getDefault());
      String msgSuccess = messageSource.getMessage("form.delete.fail", null, Locale.getDefault());
      return ResponseMessageUtils.makeResponse(false, msgEntityname + " " + msgSuccess);
    }
  }

  // function delete UserGroup before update
  private void insertSystemRoleUser(String[] groupId, Long userId){
    if(groupId.length > 0){
      userMapper.deleteSystemRoleUser(userId);
      for (int i = 0; i < groupId.length; i++){
        userMapper.insertSystemRoleUser(userId, groupId[i].replace("\"",""));
      }
    }
  }

  // function delete apply department before update
  private void insertApplyUserDepartment(String[] departmentId, Long userId){
    if(departmentId.length > 0 || departmentId.length == 0){
      userMapper.deleteApplyUserDepartment(userId);
      for (int i = 0; i < departmentId.length; i++){
        userMapper.insertApplyUserDepartment(userId, departmentId[i].replace("\"",""));
      }
    }
  }

  public ResponseMessage<User> me() {
    List<User> userList = userMapper.getOneByUsername(UserAuthSession.getUserAuth().getUsername());
    makeHidden(userList);
    return ResponseMessageUtils.makeSuccessResponse(userList.get(0));
  }

  public ResponseMessage<String> changePassword(String oldPassword, String newPassword) {
    List<User> userList = userMapper.getOne(getUserAuth().getId());
    User user = userList.get(0);

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (passwordEncoder.matches(oldPassword, user.getPassword())) {
      user.setPassword(passwordEncoder.encode(newPassword));
    } else {
      // TODO: localize
      return ResponseMessageUtils.makeResponse(false, "The password you entered is incorrect.");
    }

    user.setModifiedBy(getUserAuth().getId());

    Boolean result = userMapper.update(user);

    if (result) {
      String msgEntityname = messageSource.getMessage("user.entityname", null, Locale.getDefault());
      String msgSuccess = messageSource.getMessage("form.update.success", null, Locale.getDefault());
      return ResponseMessageUtils.makeResponse(true, msgEntityname + " " + msgSuccess);
    } else {
      String msgEntityname = messageSource.getMessage("user.entityname", null, Locale.getDefault());
      String msgSuccess = messageSource.getMessage("form.update.fail", null, Locale.getDefault());
      return ResponseMessageUtils.makeResponse(false, msgEntityname + " " + msgSuccess);
    }
  }

  public User getUserAuth() {
    if (UserAuthSession.getUserAuth() != null) {
      List<User> userList = userMapper.getOneByUsername(UserAuthSession.getUserAuth().getUsername());
      return userList.get(0);
    } else {
      return new User();
    }
  }

  private String getAccessToken(User user) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("grant_type", "password");
    map.add("client_id", "ut-client");
    map.add("client_secret", "ut-secret");
    map.add("username", user.getUsername());
    map.add("password", user.getPassword());
    HttpEntity<MultiValueMap<String, String>> requestOauthToken = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    String authUrl = environment.getProperty("api.authUrl");
    ResponseEntity<String> responseOauthToken = restTemplate.postForEntity(authUrl, requestOauthToken, String.class);
    JSONObject jsonObject = new JSONObject(responseOauthToken.getBody());
    return jsonObject.getString("access_token");
  }

  private void makeHidden(List<User> userList) {
    for (User user : userList) {
      user.setPassword(null);
      user.setPhoneVerifyToken(null);
      user.setPhoneVerifyTokenExpiredDate(null);
      user.setPhoneVerifyCode(null);
      user.setPhoneVerifyCodeExpiredDate(null);
      user.setPhoneResetPasswordToken(null);
      user.setPhoneResetPasswordTokenExpiredDate(null);
      user.setPhoneResetPasswordCode(null);
      user.setPhoneResetPasswordCodeExpiredDate(null);
      user.setPhoneNewPasswordToken(null);
      user.setPhoneNewPasswordTokenExpiredDate(null);
    }
  }

  public ResponseMessage<BaseResult> getListApplyUser(ApplyUserFilter filter) {
    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());

    if (filter != null) {
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
    }

    List<ApplyUserList> userList = userMapper.getListApplyUser(filter);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", userList, true));
  }

}
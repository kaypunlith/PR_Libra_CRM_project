//package com.ut.nlSystemAPi.controller;
//
//import com.ut.nlSystemAPi.base.UserAuthSession;
//import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
//import com.ut.nlSystemAPi.model.Users.User;
//import com.ut.nlSystemAPi.model.Users.UserFilter;
//import com.ut.nlSystemAPi.model.base.BaseResult;
//import com.ut.nlSystemAPi.model.base.ResponseMessage;
//import com.ut.nlSystemAPi.model.response.ApplyUserFilter;
//import com.ut.nlSystemAPi.service.UserService;
//import io.micrometer.core.annotation.Timed;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import springfox.documentation.annotations.ApiIgnore;
//
//@RestController
//@RequestMapping("/user")
//@Api(tags = "02. User", description = "User Resource")
//@Timed
//public class UserController {
//
//  @Autowired
//  private UserService userService;
//
//  @PostMapping("/list")
//  @ApiOperation(value = "List user by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> list(@RequestBody UserFilter filter) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return userService.getList(filter);
//  }
//
//  @PostMapping("/find/{id}")
//  @ApiOperation(value = "Find user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return userService.getOne(id);
//  }
//
//  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//  @ApiOperation(value = "Add new user", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  @ApiImplicitParams({
//    @ApiImplicitParam(name = "fullName", dataType = "string", paramType = "form", required = true),
//    @ApiImplicitParam(name = "username", dataType = "string", paramType = "form", required = true),
//    @ApiImplicitParam(name = "applyEmployees", dataType = "long", paramType = "form"),
//    @ApiImplicitParam(name = "email", dataType = "string", paramType = "form"),
//    @ApiImplicitParam(name = "password", dataType = "string", paramType = "form", required = true),
//    @ApiImplicitParam(name = "userGroup", dataType = "string", paramType = "form", required = true, allowMultiple = true),
//    @ApiImplicitParam(name = "applyDepartment", dataType = "string", paramType = "form", allowMultiple = true)
//  })
//  public ResponseMessage<BaseResult> add(@ApiIgnore User user, BindingResult bindingResult, @RequestPart(name = "file", required = false) MultipartFile file) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    // Check Validation
//    if (bindingResult.hasErrors()) {
//      return ResponseMessageUtils.makeResponse(false, bindingResult);
//    }
//    return userService.insert(user, file);
//  }
//
//  @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//  @ApiOperation(value = "Update user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  @ApiImplicitParams({
//    @ApiImplicitParam(name = "id", dataType = "long", paramType = "form", required = true),
//    @ApiImplicitParam(name = "fullName", dataType = "string", paramType = "form"),
//    @ApiImplicitParam(name = "username", dataType = "string", paramType = "form", required = true),
//    @ApiImplicitParam(name = "email", dataType = "string", paramType = "form"),
//    @ApiImplicitParam(name = "password", dataType = "string", paramType = "form", required = true),
//    @ApiImplicitParam(name = "userGroup", dataType = "string", paramType = "form", required = true, allowMultiple = true)
//  })
//  public ResponseMessage<BaseResult> update(@ApiIgnore User user, BindingResult bindingResult, @RequestPart(name = "file", required = false) MultipartFile file) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    // Check Validation
//    if (bindingResult.hasErrors()) {
//      return ResponseMessageUtils.makeResponse(false, bindingResult);
//    }
//    return userService.update(user, file);
//  }
//
//  @PostMapping(value = "/edit-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//  @ApiOperation(value = "edit user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  @ApiImplicitParams({
//          @ApiImplicitParam(name = "id", dataType = "long", paramType = "form", required = true),
//          @ApiImplicitParam(name = "fullName", dataType = "string", paramType = "form"),
//          @ApiImplicitParam(name = "email", dataType = "string", paramType = "form"),
//          @ApiImplicitParam(name = "applyDepartment", dataType = "string", paramType = "form", allowMultiple = true)
//  })
//  public ResponseMessage<BaseResult> updateUser(@ApiIgnore User user, BindingResult bindingResult, @RequestPart(name = "file", required = false) MultipartFile file) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    // Check Validation
//    if (bindingResult.hasErrors()) {
//      return ResponseMessageUtils.makeResponse(false, bindingResult);
//    }
//      return userService.updateUser(user, file);
//  }
//
//  @PostMapping("/delete/{id}")
//  @ApiOperation(value = "Delete user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<String> delete(@PathVariable("id") Long id) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return userService.delete(id);
//  }
//
//  @PostMapping("/me")
//  @ApiOperation(value = "Get logged in user", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<User> me() {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return userService.me();
//  }
//
//  @PostMapping(value = "/change-password", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//  @ApiOperation(value = "Change password", notes = "Change password", authorizations = {@Authorization(value = "Bearer")})
//  @ApiImplicitParams({
//    @ApiImplicitParam(name = "oldPassword", dataType = "string", paramType = "form", required = true),
//    @ApiImplicitParam(name = "newPassword", dataType = "string", paramType = "form", required = true)
//  })
//  public ResponseMessage<String> changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return userService.changePassword(oldPassword, newPassword);
//  }
//
//  @PostMapping("/list-applyUser")
//  @ApiOperation(value = "List apply user by filter", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> getListApplyUser(@RequestBody ApplyUserFilter filter) {
//    // Check Header Token
//    if (UserAuthSession.getUserAuth() == null){
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//    return userService.getListApplyUser(filter);
//  }
//
//}
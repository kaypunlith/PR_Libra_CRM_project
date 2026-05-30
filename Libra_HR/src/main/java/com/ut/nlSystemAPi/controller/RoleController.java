//package com.ut.nlSystemAPi.controller;
//
//import com.ut.nlSystemAPi.model.RoleData;
//import com.ut.nlSystemAPi.model.RoleDataUpdate;
//import com.ut.nlSystemAPi.model.base.BaseResult;
//import com.ut.nlSystemAPi.model.base.Filter;
//import com.ut.nlSystemAPi.model.base.ResponseMessage;
//import com.ut.nlSystemAPi.service.RoleService;
//import io.micrometer.core.annotation.Timed;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.Authorization;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/role")
//@Api(tags = "03. Role", description = "Role Resource")
//@Timed
//public class RoleController {
//
//  @Autowired
//  private RoleService roleService;
//
//  @PostMapping("/list")
//  @ApiOperation(value = "List role by filter", notes = "List role by filter", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> list(@RequestBody Filter filter) {
//    return roleService.getList(filter);
//  }
//
//  @PostMapping("/find/{id}")
//  @ApiOperation(value = "Find role by id", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
//    return roleService.getOne(id);
//  }
//
//  @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
//  @ApiOperation(value = "Add new role", notes = "Add new role", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> add(@RequestBody RoleData roleData, BindingResult bindingResult) {
//    return roleService.insert(roleData, bindingResult);
//  }
//
//  @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
//  @ApiOperation(value = "Update role by id", notes = "Update role", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> update(@RequestBody RoleDataUpdate roleDataUpdate, BindingResult bindingResult) {
//    return roleService.update(roleDataUpdate, bindingResult);
//  }
//
//  @PostMapping("/menu")
//  @ApiOperation(value = "list menu", notes = "list menu", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> menu() {
//    return roleService.menu();
//  }
//
//  @PostMapping("/delete/{id}")
//  @ApiOperation(value = "Delete role by id", notes = "Delete role", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
//    return roleService.delete(id);
//  }
//}
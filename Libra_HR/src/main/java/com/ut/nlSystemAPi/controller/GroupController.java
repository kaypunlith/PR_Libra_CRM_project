package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.GroupRequest;
import com.ut.nlSystemAPi.model.request.GroupUpdateRequest;
import com.ut.nlSystemAPi.service.GroupService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/group")
@Api(tags = "Group")
@Timed
public class GroupController {

  @Autowired
  private GroupService groupService;

    @PostMapping("/list")
    @ApiOperation(value = "Group (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody Filter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return groupService.getList(filter);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Group by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return groupService.getOne(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Group (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody GroupRequest groupRequest, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return groupService.insert(groupRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Group by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody GroupUpdateRequest groupUpdateRequest, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return groupService.update(groupUpdateRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete Group by id", notes = "Delete document  Group", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
        return groupService.delete(id);
    }

    @PostMapping("/permission-user")
    @ApiOperation(value = "Group (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listFilterGroup(@RequestBody Filter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return groupService.listFilterGroup(filter);
    }

}

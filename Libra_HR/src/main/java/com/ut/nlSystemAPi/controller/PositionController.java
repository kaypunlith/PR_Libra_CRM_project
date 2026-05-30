package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.PositionOrderingRequest;
import com.ut.nlSystemAPi.model.request.PositionRequest;
import com.ut.nlSystemAPi.model.request.PositionUpdateRequest;
import com.ut.nlSystemAPi.service.PositionService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
@Api(tags = "Position")
@Timed
public class PositionController {

  @Autowired
  private PositionService positionService;

    @PostMapping("/list")
    @ApiOperation(value = "Position (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody Filter filter) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return positionService.getList(filter);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find user by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return positionService.getOne(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Position (Add)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody PositionRequest positionRequest, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return positionService.insert(positionRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Position by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody PositionUpdateRequest positionUpdateRequest, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return positionService.update(positionUpdateRequest);
    }

    @PostMapping(value = "/ordering", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Position Ordering", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateOrdering(@RequestBody PositionOrderingRequest positionOrderingRequest, BindingResult bindingResult) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        // Check Validation
        if (bindingResult.hasErrors()) {
            return ResponseMessageUtils.makeResponse(false, bindingResult);
        }
        return positionService.updateOrdering(positionOrderingRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete Position by id", notes = "Delete document Position", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id) {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null){
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return positionService.delete(id);
    }
}

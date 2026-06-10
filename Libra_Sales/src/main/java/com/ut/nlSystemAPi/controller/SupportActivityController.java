package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportActivityRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportActivityUpdateRequest;
import com.ut.nlSystemAPi.service.SupportActivityService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/support-activity")
@Api(tags = "61. Support Ticket Activity", description = "Support Ticket Activity Resource")
@Timed
public class SupportActivityController {

    @Autowired
    private SupportActivityService service;

    @PostMapping("/list")
    @ApiOperation(value = "List support ticket activity by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return service.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find support ticket activity by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return service.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new support ticket activity", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody SupportActivityRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return service.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update support ticket activity by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody SupportActivityUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return service.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete support ticket activity by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return service.delete(id, httpServletRequest);
    }
}

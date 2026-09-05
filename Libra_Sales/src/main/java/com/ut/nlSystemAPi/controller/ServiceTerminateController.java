package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ServiceFilter;
import com.ut.nlSystemAPi.model.filter.ServiceTerminateFilter;
import com.ut.nlSystemAPi.model.request.Service.ServiceRequest;
import com.ut.nlSystemAPi.model.request.Service.ServiceUpdateRequest;
import com.ut.nlSystemAPi.model.request.serviceTerminate.ServiceTerminateRequest;
import com.ut.nlSystemAPi.model.request.serviceTerminate.ServiceUpdateTerminateRequest;
import com.ut.nlSystemAPi.service.ServiceService;
import com.ut.nlSystemAPi.service.ServiceTerminateService;
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
@RequestMapping("/service-terminate")
@Api(tags = "34. service-terminate", description = "Service terminate Resource")
@Timed
public class ServiceTerminateController {

    @Autowired
    private ServiceTerminateService serviceTerminateService;

    @PostMapping("/list")
    @ApiOperation(value = "List service by filter",notes = "status 1:Do , 2:Undo", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody ServiceTerminateFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return serviceTerminateService.getList(filter, httpServletRequest);
    }

    @PostMapping("/customer-quotation/{customerId}")
    @ApiOperation(value = "List service by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCustomerQuotation(@PathVariable("customerId") Long customerId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return serviceTerminateService.getListCustomerQuotation(customerId, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find service by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return serviceTerminateService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new service", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ServiceTerminateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return serviceTerminateService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update service by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ServiceUpdateTerminateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return serviceTerminateService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete service by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return serviceTerminateService.delete(id, httpServletRequest);
    }
}
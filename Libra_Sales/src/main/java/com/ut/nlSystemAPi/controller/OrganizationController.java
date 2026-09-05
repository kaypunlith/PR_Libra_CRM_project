package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OrganizationActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.OrganizationFilter;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationActivityCardRequest;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationRequest;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationUpdateRequest;
import com.ut.nlSystemAPi.service.OrganizationService;
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
@RequestMapping("/organization")
@Api(tags = "27. Organization", description = "Organization Resource")
@Timed
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/list")
    @ApiOperation(value = "List organization by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody OrganizationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find organization by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new organization", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody OrganizationRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update organization by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody OrganizationUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete organization by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationService.delete(id, httpServletRequest);
    }

    @PostMapping(value = "/add-activity-card", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new organization", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> addActivityCard(@RequestBody OrganizationActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationService.addActivityCard(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/list-activity-card")
    @ApiOperation(value = "List activity card organization by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listActivityCard(@RequestBody OrganizationActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return organizationService.getListActivityCard(filter, httpServletRequest);
    }
}

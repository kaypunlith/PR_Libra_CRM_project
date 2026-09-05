package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OpportunitiesFilter;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityAddMoreRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityUpdateRequest;
import com.ut.nlSystemAPi.service.OpportunitiesService;
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
@RequestMapping("/opportunities")
@Api(tags = "53. Opportunities", description = "Opportunities Resource")
@Timed
public class OpportunitiesController {

    @Autowired
    private OpportunitiesService opportunitiesService;

    @PostMapping("/list")
    @ApiOperation(value = "List opportunities by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody OpportunitiesFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunitiesService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find opportunities by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunitiesService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new opportunities", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody OpportunityRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunitiesService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update opportunities by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody OpportunityUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunitiesService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/add-more/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save opportunity stage activity, task, description, and stage action", notes = "action: SAVE, NEXT, BACK, SKIP", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> addMore(@PathVariable("id") Long id, @RequestBody OpportunityAddMoreRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunitiesService.addMore(id, request, bindingResult, httpServletRequest);
    }

    @PostMapping("/list-log/{id}")
    @ApiOperation(value = "List opportunity add more log by opportunity id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listLog(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunitiesService.getListLog(id, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete opportunities by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunitiesService.delete(id, httpServletRequest);
    }
}

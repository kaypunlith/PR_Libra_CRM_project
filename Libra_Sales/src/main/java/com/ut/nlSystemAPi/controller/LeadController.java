package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeadActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.LeadFilter;
import com.ut.nlSystemAPi.model.request.Lead.LeadActivityCardRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadConvertRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadUpdateRequest;
import com.ut.nlSystemAPi.service.LeadService;
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
@RequestMapping("/lead")
@Api(tags = "41. Lead", description = "Lead Resource")
@Timed
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping("/list")
    @ApiOperation(value = "List lead by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody LeadFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find lead by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new lead", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody LeadRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update lead by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody LeadUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete lead by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.delete(id, httpServletRequest);
    }

    @PostMapping(value = "/add-activity-card", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new lead activity card", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> addActivityCard(@RequestBody LeadActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.addActivityCard(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/list-activity-card")
    @ApiOperation(value = "List lead activity card report", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listActivityCard(@RequestBody LeadActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.getListActivityCard(filter, httpServletRequest);
    }

    @PostMapping("/find-activity-card/{id}")
    @ApiOperation(value = "Find lead activity card by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findActivityCard(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.getOneActivityCard(id, httpServletRequest);
    }

    @PostMapping("/find-convert/{id}")
    @ApiOperation(value = "Find convert lead by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findConvert(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.getConvert(id, httpServletRequest);
    }

    @PostMapping(value = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Convert lead to customer", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> convert(@RequestBody LeadConvertRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return leadService.convert(request, bindingResult, httpServletRequest);
    }
}

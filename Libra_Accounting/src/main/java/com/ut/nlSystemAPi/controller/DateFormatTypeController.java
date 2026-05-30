package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.BranchType.BranchTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.BranchType.BranchTypeUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.DateFormatType.DateFormatTypeRequest;
import com.ut.nlSystemAPi.service.BranchTypeService;
import com.ut.nlSystemAPi.service.DateFormatTypeService;
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
@RequestMapping("/dateFormatType")
@Api(tags = "39. Date Format Type", description = "dateFormat Type Resource")
public class DateFormatTypeController {
    @Autowired
    private DateFormatTypeService dateFormatTypeService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new dateFormatType", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody DateFormatTypeRequest dateFormatTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token   
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dateFormatTypeService.insert(dateFormatTypeRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/list")
    @ApiOperation(value = "List by filter", notes = "List zone by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listNationality(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dateFormatTypeService.getList(filter, httpServletRequest);
    }
}

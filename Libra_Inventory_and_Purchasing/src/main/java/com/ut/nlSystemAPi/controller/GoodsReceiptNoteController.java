package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.GoodReceiptNoteFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote.GoodReceiptNoteRequest;
import com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote.GoodReceiptNoteUpdateRequest;
import com.ut.nlSystemAPi.service.GoodReceiptNoteService;
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
@RequestMapping("/good-receipt-note")
@Api(tags = "29. Good Receipt Note", description = "Good Receipt Note Resource")
@Timed
public class GoodsReceiptNoteController {
    @Autowired
    private GoodReceiptNoteService goodReceiptNoteService;

    @PostMapping("/list")
    @ApiOperation(value = "List good receipt note by filter", notes = "List good receipt note by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody GoodReceiptNoteFilter goodReceiptNoteFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return goodReceiptNoteService.getList(goodReceiptNoteFilter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find good receipt note by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return goodReceiptNoteService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new good receipt note", notes = "Add new meeting board", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody GoodReceiptNoteRequest goodReceiptNoteRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return goodReceiptNoteService.insert(goodReceiptNoteRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update good receipt note by id", notes = "Update meeting board", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody GoodReceiptNoteUpdateRequest goodReceiptNoteUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return goodReceiptNoteService.update(goodReceiptNoteUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete good receipt note by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return goodReceiptNoteService.delete(id, httpServletRequest);
    }

    @PostMapping("/update-approve/{id}")
    @ApiOperation(value = "update good receipt note by id", notes = "isApproved: 1: Disapproved, 2: Approved", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateApprove(@RequestBody StatusFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return goodReceiptNoteService.updateApprove(filter, httpServletRequest);
    }

    @PostMapping("/vendor-list")
    @ApiOperation(value = "List vendor good receipt note  type by filter", notes = "List good receipt note type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> vendorList(@RequestBody GoodReceiptNoteFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return goodReceiptNoteService.getListVendor(filter, httpServletRequest);
    }

}
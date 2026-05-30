package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PayBillPrintFilter;
import com.ut.nlSystemAPi.model.filter.PayBillsJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.PayBillJournal.PayBillJournalRequest;
import com.ut.nlSystemAPi.service.PayBillJournalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/pay-bill-journal")
@Api(tags = "27. Pay Bill Journal", description = "Pay Bill Journal Resource")
public class PayBillJournalController {

    @Autowired
    private PayBillJournalService payBillJournalService;

    @PostMapping("/list")
    @ApiOperation(value = "List Pay Bill Journal by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody PayBillsJournalFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return payBillJournalService.getList(filter, httpServletRequest);
    }

    @PostMapping("/save")
    @ApiOperation(value = "Save Pay Bill Journal", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> save(@RequestBody PayBillJournalRequest requestUpdate, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return payBillJournalService.save(requestUpdate, httpServletRequest);
    }
    @PostMapping("/list-print")
    @ApiOperation(value = "List Pay Bill Print by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListPrint(@RequestBody PayBillPrintFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return payBillJournalService.getListPrint(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Pay Bill Print by id", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return payBillJournalService.getOne(id, httpServletRequest);
    }

}

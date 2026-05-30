package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryStatusRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryUpdateRequest;
import com.ut.nlSystemAPi.service.JournalEntrySupervisorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/journal-entry-supervisor")
@Api(tags = "22. Journal Entry Supervisor", description = "Journal Entry Supervisor Resource")
public class JournalEntrySupervisorController {

    @Autowired
    private JournalEntrySupervisorService journalEntrySupervisorService;

    @PostMapping("/list")
    @ApiOperation(value = "List Journal Entry Supervisor by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntrySupervisorService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Journal Entry Supervisor by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntrySupervisorService.getOne(id, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add Journal Entry Supervisor setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody JournalEntryRequest journalEntryRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntrySupervisorService.insert(journalEntryRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Add Journal Entry Supervisor setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody JournalEntryUpdateRequest journalEntryUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        System.out.println(journalEntryUpdateRequest);
        return journalEntrySupervisorService.update(journalEntryUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete Journal Entry Supervisor by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntrySupervisorService.delete(id, httpServletRequest);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "Update status Journal Entry by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@RequestBody JournalEntryStatusRequest journalEntryStatusRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntrySupervisorService.updateStatus(journalEntryStatusRequest, httpServletRequest);
    }

}

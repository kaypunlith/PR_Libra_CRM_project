package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.filter.MakeDepositFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.*;
import com.ut.nlSystemAPi.service.JournalEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/journal-entry")
@Api(tags = "21. Journal Entry", description = "Currency Resource")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/list")
    @ApiOperation(value = "List Journal Entry by filter", notes = "Status 1: active, 2: inactive; adj 1: True, 0: False", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.getList(filter, httpServletRequest);
    }

    @PostMapping("/list-report")
    @ApiOperation(value = "List Journal Entry by filter for report", notes = "Follows report conditions (approved only, group-based amount/balance)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListReport(@RequestBody JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.getListReport(filter, httpServletRequest);
    }

    @PostMapping("/list-report-by-group")
    @ApiOperation(value = "List Journal Entry by chart account group (legacy group report)", notes = "Same payload as /list-report, matches PHP ajax_by_group conditions", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListReportByGroup(@RequestBody JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.getListReportByGroup(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Journal Entry by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.getOne(id, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add Journal Entry setting", notes = "Journal Entry: isApprove = 0, Journal Entry Supervisor: isApprove=1 ", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody JournalEntryRequest journalEntryRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.insert(journalEntryRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update Journal Entry setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody JournalEntryUpdateRequest journalEntryUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.update(journalEntryUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/write-checks")
    @ApiOperation(value = "Write Checks Entry setting", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> writeChecks(@RequestBody WriteChecksRequest writeChecksRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.writeChecks(writeChecksRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update-write-checks")
    @ApiOperation(value = "Write Checks Entry setting", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateWriteChecks(@RequestBody WriteChecksRequestUpdate requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.updateWriteChecks(requestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/find-check/{id}")
    @ApiOperation(value = "Find Check Journal Entry by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findCheckById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.findCheckById(id, httpServletRequest);
    }

    @PostMapping("/make-deposits")
    @ApiOperation(value = "Make Deposits Entry setting", notes = "depositType: 1, Purchase Order; 2, Purchase Bill; 3, Quotation; 4, Sales Invoice", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> makeDeposits(@RequestBody MakeDepositsRequest makeDepositsRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.makeDeposits(makeDepositsRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update-make-deposits")
    @ApiOperation(value = "Make Deposits Entry setting", notes = "ApplyToId: 1, Purchase Order; 2, Purchase Bill; 3, Quotation; 4, Sales Invoice", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateMakeDeposits(@RequestBody MakeDepositsRequestUpdate requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.updateMakeDeposits(requestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/find-deposits/{id}")
    @ApiOperation(value = "Find Deposits Journal Entry by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findDepositsById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.findDepositsById(id, httpServletRequest);
    }


    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete Journal Entry by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.delete(id, httpServletRequest);
    }

    @PostMapping("/closeRecurrence/{id}")
    @ApiOperation(value = "Close Recurrence Journal Entry by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> closeRecurrence(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.closeRecurrence(id, httpServletRequest);
    }

    @PostMapping("/addRecurrence/{id}")
    @ApiOperation(value = "Add Recurrence Journal Entry by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> addRecurrence(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.addRecurrence(id, httpServletRequest);
    }

    @PostMapping("/update-status")
    @ApiOperation(value = "Add Recurrence Journal Entry by id", notes = "Status 1:true, 0: false", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@RequestBody JournalEntryStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.updateStatus(request, httpServletRequest);
    }

    @PostMapping("/check-reference/{reference}")
    @ApiOperation(value = "Add Recurrence Journal Entry by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> checkReference(@PathVariable("reference") String reference, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.checkReference(reference, httpServletRequest);
    }

    @PostMapping("/make-deposit-apply-to-list")
    @ApiOperation(value = "List Journal Entry by filter", notes = "ApplyToId: 1, Purchase Order; 2, Purchase Bill; 3, Quotation; 4, Sales Invoice", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getMakeDepositApplyToList(@RequestBody MakeDepositFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.getMakeDepositApplyToList(filter, httpServletRequest);
    }

    @PostMapping("/note")
    @ApiOperation(value = "Add Note Journal Entry by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> note(@RequestBody JournalEntryNoteRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return journalEntryService.note(request, httpServletRequest);
    }
}

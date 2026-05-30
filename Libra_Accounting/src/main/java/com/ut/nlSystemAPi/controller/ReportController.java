package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/report")
@Api(tags = "40. Report", description = "Report Resource")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/generalLeger")
    @ApiOperation(value = "List generalLegerReport by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listGeneralLeger(@RequestBody generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListGeneralLeger(filter, httpServletRequest);
    }

    @PostMapping("/journal")
    @ApiOperation(value = "List journalReport by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listJournal(@RequestBody generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListJournal(filter, httpServletRequest);
    }

    @PostMapping("/trial-balance")
    @ApiOperation(value = "List trial balance by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> TrialBalance(@RequestBody generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.trialBalance(filter, httpServletRequest);
    }

    @PostMapping("/check")
    @ApiOperation(value = "List journalReport by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listCheck(@RequestBody generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListCheck(filter, httpServletRequest);
    }

    @PostMapping("/deposit")
    @ApiOperation(value = "List debit by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listDebit(@RequestBody generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListDebit(filter, httpServletRequest);
    }

    @PostMapping("/audit-Trail")
    @ApiOperation(value = "List debit by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listAuditTrail(@RequestBody AuditTrailReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListAuditTrail(filter, httpServletRequest);
    }

    @PostMapping("/AR-Detail")
    @ApiOperation(value = "List debit by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listARDetail(@RequestBody ARDetailReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListARDetail(filter, httpServletRequest);
    }

    @PostMapping("/AP-Detail")
    @ApiOperation(value = "List AP Detail by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listAPDetail(@RequestBody ARDetailReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListAPDetail(filter, httpServletRequest);
    }

    @PostMapping("/profitAndLosses")
    @ApiOperation(value = "List profit loss  by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> listProfitLoss(@RequestBody ProfitAndLossReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListProfitLoss(filter, httpServletRequest);
    }

    @PostMapping("/balance-sheet")
    @ApiOperation(value = "List profit loss  by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> balanceSheet(@RequestBody ProfitAndLossReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.getListBalanceSheet(filter, httpServletRequest);
    }

    @PostMapping("/cash-flow")
    @ApiOperation(value = "List profit loss  by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> cashFlow(@RequestBody cashFlowReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.cashFlow(filter, httpServletRequest);
    }

    @PostMapping("/Reconcile")
    @ApiOperation(value = "List profit loss  by filter", authorizations = {@Authorization(value = "Bearer") })
    public ResponseMessage<BaseResult> Reconcile(@RequestBody ReconcileReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reportService.Reconcile(filter, httpServletRequest);
    }
}

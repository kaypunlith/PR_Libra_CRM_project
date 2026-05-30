package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.valuation.InventoryValuationGateway;
import com.ut.nlSystemAPi.helper.valuation.InventoryValuationRunner;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportBalanceSheetMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ProfitLossReport;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.Report.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ReportBalanceSheetMapper reportBalanceSheetMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private InventoryValuationGateway inventoryValuationGateway;

    @Override
    public ResponseMessage<BaseResult> getListGeneralLeger(generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal((long) reportMapper.countList(filter).size());
            filter.setUserId(userId);
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<GeneralLedgerReportResponse> generalLedgerReportResponses = reportMapper.getChartAccountGl(filter);

            if(generalLedgerReportResponses.size() > 0){
                for (int i = 0; i < generalLedgerReportResponses.size(); i++) {
                    Long chartAccountId = generalLedgerReportResponses.get(i).getChartAccountId();
                    List<GeneralLedgerReportResponse> getDetail = reportMapper.getListGeneralLegerReport(filter, chartAccountId);

                    Double balance = generalLedgerReportResponses.get(i).getBeginBalance();
                    Double totalDebit = 0D;
                    Double totalCredit = 0D;

                    if(getDetail.size() > 0){
                        for (int j = 0; j < getDetail.size(); j++) {
                            if (getDetail.get(j).getDebit() > 0){
                                balance = balance + getDetail.get(j).getDebit();
                            } else if (getDetail.get(j).getCredit() > 0) {
                                balance = balance - getDetail.get(j).getCredit();
                            }
                            getDetail.get(j).setBalance(balance);

                            totalDebit = totalDebit + getDetail.get(j).getDebit();
                            totalCredit = totalCredit + getDetail.get(j).getCredit();
                        }
                        generalLedgerReportResponses.get(i).setBalance(getDetail.get(getDetail.size() - 1).getBalance());
                    }


                    // Set Data to main chart account
                    generalLedgerReportResponses.get(i).setDebit(totalDebit);
                    generalLedgerReportResponses.get(i).setCredit(totalCredit);
                    generalLedgerReportResponses.get(i).setDetailedReports(getDetail);
                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/generalLegerReport/list", null, null, "generalLegerReport", " generalLegerReport (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", generalLedgerReportResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/generalLegerReport/list", 1033L, error.toString(), "generalLegerReport", "generalLegerReport (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListJournal(generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            InventoryValuationRunner.runValuation(inventoryValuationGateway);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(reportMapper.countListJournal(filter));
            filter.setUserId(userId);
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<GeneralLedgerReportResponse> journalResponse = reportMapper.getListJournal(filter);
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal/list", null, null, "journal", " journal (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", journalResponse, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal/list", 1033L, error.toString(), "journal", "journal (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> trialBalance(generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(reportMapper.countListTrailBalance(filter));
            filter.setUserId(userId);
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<TrialBalanceResponse> trialBalanceResponses = new ArrayList<>();

            List<TrialBalanceResponse> trialBalance = reportMapper.getListTrailBalance(filter);

            List<String> months = getMonthList(filter.getDateFrom(), filter.getDateTo());

            for(int i=0;i<trialBalance.size();i++){

                Double debit = 0.0D;
                Double credit = 0.0D;

                List<TrialBalanceDetails> trialBalanceDetailsTemp = new ArrayList<>();
                for(int j=0;j<months.size();j++){
                    List<TrialBalanceDetails> trialBalanceDetails=reportMapper.getTrialBalanceDetails(filter, trialBalance.get(i).getId(), months.get(j));

                    debit += trialBalanceDetails.get(0).getDebit();
                    credit += trialBalanceDetails.get(0).getCredit();

                    trialBalanceDetailsTemp.addAll(trialBalanceDetails);
                }

                // Set data
                if(filter.getDisplayEmptyData() == 0 && filter.getType() != 2){
                    // Set month detailed
                    if(debit > 0 || credit > 0){
                        trialBalance.get(i).setTrialBalanceDetails(trialBalanceDetailsTemp);
                    }
                    if (trialBalance.get(i).getTrialBalanceDetails() != null && trialBalance.get(i).getTrialBalanceDetails().size() > 0) {
                        trialBalanceResponses.add(trialBalance.get(i));
                    }
                } else {
                    trialBalance.get(i).setTrialBalanceDetails(trialBalanceDetailsTemp);
                    trialBalanceResponses.add(trialBalance.get(i));
                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/trial balance/list", null, null, "trial balance", " trial balance (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", trialBalanceResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/trial balance/list", 1033L, error.toString(), "trial balance", "trial balance (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getListCheck(generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(reportMapper.countListCheck(filter));
            filter.setUserId(userId);
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
//
            List<CheckReportResponse> checkReportResponses = new ArrayList<>();

            List<CheckReportResponse> checkReports = reportMapper.getListCheck(filter);

            if(checkReports.size()>0){
                for (int i = 0; i < checkReports.size(); i++) {
                    Long journalEntryId = checkReports.get(i).getId();
                    List<CheckReportResponse> journalEntryDetail = reportMapper.getListCheckDetail(journalEntryId);

                    if(journalEntryDetail.size()>0){
                        journalEntryDetail.get(0).setNo((long) (i + 1));
                        journalEntryDetail.get(0).setDate(checkReports.get(i).getDate());
                        journalEntryDetail.get(0).setBranchId(checkReports.get(i).getBranchId());
                        journalEntryDetail.get(0).setBranchName(checkReports.get(i).getBranchName());
                    }

                    // add to response
                    checkReportResponses.addAll(journalEntryDetail);
                }

                List<CheckReportResponse> grandTotal = reportMapper.getListCheckGrandTotal(filter);


                checkReportResponses.get(0).setTotalCredit(grandTotal.get(0).getTotalCredit());
                checkReportResponses.get(0).setTotalDebit(grandTotal.get(0).getTotalDebit());
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/check/list", null, null, "check", " check (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", checkReportResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/check/list", 1033L, error.toString(), "check", "check (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getListDebit(generalLegerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(reportMapper.countListDebit(filter));
            filter.setUserId(userId);
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CheckReportResponse> checkReportResponses = new ArrayList<>();

            List<CheckReportResponse> checkReports = reportMapper.getListDebit(filter);

            if(checkReports.size()>0) {
                for (int i = 0; i < checkReports.size(); i++) {
                    Long journalEntryId = checkReports.get(i).getId();
                    List<CheckReportResponse> journalEntryDetail = reportMapper.getListDebitDetail(journalEntryId);

                    if (journalEntryDetail.size() > 0) {
                        journalEntryDetail.get(0).setNo((long) (i + 1));
                        journalEntryDetail.get(0).setDate(checkReports.get(i).getDate());
                        journalEntryDetail.get(0).setBranchId(checkReports.get(i).getBranchId());
                        journalEntryDetail.get(0).setBranchName(checkReports.get(i).getBranchName());
                    }

                    // add to response
                    checkReportResponses.addAll(journalEntryDetail);
                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/debit/list", null, null, "debit", " debit (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", checkReportResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/debit/list", 1033L, error.toString(), "dibit", "dibit (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListAuditTrail(AuditTrailReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            // Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(10L);
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<AuditTrailReportResponse> auditTrailReportResponses = new ArrayList<>();
            List<Long> typeSelectList = filter.getTypeSelect();
            if (typeSelectList != null) {
                for (Long typeSelect : typeSelectList) {
                    if (typeSelect == 1) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByQuotation(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 2) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailBySaleOrder(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 3) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailBySaleInvoice(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 4) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByPOS(filter);

                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 5) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByCreditMemo(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 6) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByDelivery(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 7) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByReceivePayment(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 8) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByAdjustment(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 9) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByTransferOrder(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 3L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                }else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 10) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByPurchaseOrder(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 3L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                } else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 11) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByPurchaseBill(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 3L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                } else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 12) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByPurchaseReceive(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 3L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                } else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 13) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByBill(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 1L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                } else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    } else if (typeSelect == 14) {
                        List<AuditTrailReportResponse> auditTrailReportResponsesTmp = reportMapper.getListAuditTrailByBillReturn(filter);
                        if(auditTrailReportResponsesTmp.size() > 0){
                            for (int i = 0; i < auditTrailReportResponsesTmp.size(); i++) {
                                if(auditTrailReportResponsesTmp.get(i).getStatusNum() == -1L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Modified");
                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 0L){
                                    auditTrailReportResponsesTmp.get(i).setStatus("Void");

                                } else if (auditTrailReportResponsesTmp.get(i).getStatusNum() == 2L || auditTrailReportResponsesTmp.get(i).getStatusNum() == 3L){
                                    if(i > 0){
                                        if(!auditTrailReportResponsesTmp.get(i).getCode().equals(auditTrailReportResponsesTmp.get(i-1).getCode())){
                                            auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                        } else{
                                            auditTrailReportResponsesTmp.get(i).setStatus("Latest");
                                        }
                                    } else {
                                        auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                    }
                                } else {
                                    auditTrailReportResponsesTmp.get(i).setStatus("Prior");
                                }
                            }
                        }
                        auditTrailReportResponses.addAll(auditTrailReportResponsesTmp);
                    }
                }
            }
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/auditTrail/list", null, null, "auditTrail", " auditTrail (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", auditTrailReportResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/auditTrail/list", 1033L, error.toString(), "auditTrail", "auditTrail (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getListARDetail(ARDetailReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            // Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(reportMapper.countListAR(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
//
            List<ARDetailReportResponse> arDetailReportResponses = new ArrayList<>();

            //! list for graph AR
            {
                List<ARListDetailReportResponse> customerTotals = reportMapper.getARGraphDetail(filter);

                // Build aging bucket summaries for the first list
                List<ARListDetailReportResponse> firstList = new ArrayList<>();

                // Current (0 day)
                List<ARListDetailReportResponse> bucket0 = reportMapper.getListARDetail(filter, 0L, 0L);
                double total0 = 0D;
                for (int i = 0; i < bucket0.size(); i++) {
                    total0 += bucket0.get(i).getOpenBalance();
                }
                ARListDetailReportResponse current = new ARListDetailReportResponse();
                current.setAmount(total0);
                current.setCustomerName("Current");
                firstList.add(current);

                // Interval buckets (1..interval), (interval+1..2*interval), ... up to throughDay
                if (filter.getThroughDay() != 0 && filter.getIntervalDay() != 0) {
                    long iterations = filter.getThroughDay() / filter.getIntervalDay();
                    for (int i = 0; i < iterations; i++) {
                        long fromValue = (i * filter.getIntervalDay()) + 1;
                        long toValue = (i + 1) * filter.getIntervalDay();
                        List<ARListDetailReportResponse> bucket = reportMapper.getListARDetail(filter, fromValue, toValue);
                        double total = 0D;
                        for (int k = 0; k < bucket.size(); k++) {
                            total += bucket.get(k).getOpenBalance();
                        }
                        ARListDetailReportResponse summary = new ARListDetailReportResponse();
                        summary.setAmount(total);
                        summary.setCustomerName(fromValue + "-" + toValue);
                        firstList.add(summary);
                    }

                    // Over bucket (> last toValue)
                    long lastTo = (iterations) * filter.getIntervalDay();
                    List<ARListDetailReportResponse> bucketOver = reportMapper.getListARDetail(filter, null, lastTo);
                    double totalOver = 0D;
                    for (int k = 0; k < bucketOver.size(); k++) {
                        totalOver += bucketOver.get(k).getOpenBalance();
                    }
                    ARListDetailReportResponse over = new ARListDetailReportResponse();
                    over.setAmount(totalOver);
                    over.setCustomerName(">" + lastTo);
                    firstList.add(over);
                }

                // Append customer totals after bucket summaries
                if (customerTotals != null && !customerTotals.isEmpty()) {
                    firstList.addAll(customerTotals);
                }

                ARDetailReportResponse response = new ARDetailReportResponse();
                response.setArListDetailReportResponses(firstList);

                arDetailReportResponses.add(response);
            }

            if (filter.getThroughDay() != 0) {
                Long iterations = filter.getThroughDay() / filter.getIntervalDay();
                Long fromValue = 0L;
                Long toValue = 0L;
                List<ARDetailReportResponse> arDetailReportResponseList = new ArrayList<>();

                // Include 0-day bucket (from = 0, to = 0)
                {
                    ARDetailReportResponse zeroBucket = new ARDetailReportResponse();
                    zeroBucket.setFrom(0L);
                    zeroBucket.setTo(0L);
                    List<ARListDetailReportResponse> zeroList = reportMapper.getListARDetail(filter, 0L, 0L);
                    zeroBucket.setArListDetailReportResponses(zeroList);
                    Double zeroTotal = 0D;
                    if (zeroList.size() > 0) {
                        for (int k = 0; k < zeroList.size(); k++) {
                            zeroTotal += zeroList.get(k).getOpenBalance();
                        }
                    }
                    zeroBucket.setGrandTotalBalance(zeroTotal);
                    arDetailReportResponseList.add(zeroBucket);
                }

                for (int i = 0; i < iterations; i++) {
                    fromValue = (i * filter.getIntervalDay()) + 1;
                    toValue = (i + 1) * filter.getIntervalDay();
                    ARDetailReportResponse response = new ARDetailReportResponse();
                    response.setFrom(fromValue);
                    response.setTo(toValue);
                    List<ARListDetailReportResponse> arListDetailReportResponses = reportMapper.getListARDetail(filter,fromValue,toValue);
                    response.setArListDetailReportResponses(arListDetailReportResponses);
                    // Find total
                    Double totalBalance = 0D;
                    if (arListDetailReportResponses.size() > 0) {
                        for(int k=0; k<arListDetailReportResponses.size(); k++){
                            totalBalance += arListDetailReportResponses.get(k).getOpenBalance();
                        }
                    }
                    response.setGrandTotalBalance(totalBalance);
                    arDetailReportResponseList.add(response);
                }

                arDetailReportResponses.addAll(arDetailReportResponseList);

                ARDetailReportResponse response = new ARDetailReportResponse();
                response.setTo(filter.getThroughDay());

                List<ARListDetailReportResponse> arListDetailReportResponses = reportMapper.getListARDetail(filter, null,toValue);
                response.setArListDetailReportResponses(arListDetailReportResponses);

                // Find total
                Double totalBalance = 0D;
                if (arListDetailReportResponses.size() > 0) {
                    for(int k=0; k<arListDetailReportResponses.size(); k++){
                        totalBalance += arListDetailReportResponses.get(k).getOpenBalance();
                    }
                }
                response.setGrandTotalBalance(totalBalance);

                arDetailReportResponses.add(response);
            }
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/AR-Detail/list", null, null, "AR-Detail", " AR-Detail (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", arDetailReportResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/AR-Detail/list", 1033L, error.toString(), "AR-Detail", "AR-Detail (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getListAPDetail(ARDetailReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            // Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//           pagination.setTotal(reportMapper.countListAR(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<APDetailReportResponse> apDetailReportResponse = new ArrayList<>();

            //! list for graph AP
            {
                List<ApListDetailReportResponse> vendorTotals = reportMapper.getAPGraphDetail(filter);

                // Build aging bucket summaries for the first list
                List<ApListDetailReportResponse> firstList = new ArrayList<>();

                // Current (0 day)
                List<ApListDetailReportResponse> bucket0 = reportMapper.getListAPDetail(filter, 0L, 0L);
                double total0 = 0D;
                for (int i = 0; i < bucket0.size(); i++) {
                    total0 += bucket0.get(i).getOpenBalance();
                }
                ApListDetailReportResponse current = new ApListDetailReportResponse();
                current.setAmount(total0);
                current.setVendorName("Current");
                firstList.add(current);

                // Interval buckets (1..interval), (interval+1..2*interval), ... up to throughDay
                if (filter.getThroughDay() != 0 && filter.getIntervalDay() != 0) {
                    long iterations = filter.getThroughDay() / filter.getIntervalDay();
                    for (int i = 0; i < iterations; i++) {
                        long fromValue = (i * filter.getIntervalDay()) + 1;
                        long toValue = (i + 1) * filter.getIntervalDay();
                        List<ApListDetailReportResponse> bucket = reportMapper.getListAPDetail(filter, fromValue, toValue);
                        double total = 0D;
                        for (int k = 0; k < bucket.size(); k++) {
                            total += bucket.get(k).getOpenBalance();
                        }
                        ApListDetailReportResponse summary = new ApListDetailReportResponse();
                        summary.setAmount(total);
                        summary.setVendorName(fromValue + "-" + toValue);
                        firstList.add(summary);
                    }

                    // Over bucket (> last toValue)
                    long lastTo = (iterations) * filter.getIntervalDay();
                    List<ApListDetailReportResponse> bucketOver = reportMapper.getListAPDetail(filter, null, lastTo);
                    double totalOver = 0D;
                    for (int k = 0; k < bucketOver.size(); k++) {
                        totalOver += bucketOver.get(k).getOpenBalance();
                    }
                    ApListDetailReportResponse over = new ApListDetailReportResponse();
                    over.setAmount(totalOver);
                    over.setVendorName(">" + lastTo);
                    firstList.add(over);
                }

                // Append vendor totals after bucket summaries
                if (vendorTotals != null && !vendorTotals.isEmpty()) {
                    firstList.addAll(vendorTotals);
                }

                APDetailReportResponse response = new APDetailReportResponse();
                response.setApListDetailReportResponses(firstList);
                apDetailReportResponse.add(response);
            }

            if(apDetailReportResponse.size() > 0){
                // Find data of list
                if (filter.getThroughDay() != 0) {
                    long iterations = filter.getThroughDay() / filter.getIntervalDay();
                    long fromValue = 0L;
                    long toValue = 0L;
                    List<APDetailReportResponse> apDetailReportResponseList = new ArrayList<>();

                    // Include 0-day bucket (from = 0, to = 0)
                    {
                        APDetailReportResponse zeroBucket = new APDetailReportResponse();
                        zeroBucket.setFrom(0L);
                        zeroBucket.setTo(0L);
                        List<ApListDetailReportResponse> zeroList = reportMapper.getListAPDetail(filter, 0L, 0L);
                        zeroBucket.setApListDetailReportResponses(zeroList);
                        Double zeroTotal = 0D;
                        if (zeroList.size() > 0) {
                            for (int k = 0; k < zeroList.size(); k++) {
                                zeroTotal += zeroList.get(k).getOpenBalance();
                            }
                        }
                        zeroBucket.setGrandTotalBalance(zeroTotal);
                        apDetailReportResponseList.add(zeroBucket);
                    }

                    for (int i = 0; i < iterations; i++) {
                        fromValue = i * filter.getIntervalDay() + 1;
                        toValue = (i + 1) * filter.getIntervalDay();
                        APDetailReportResponse response = new APDetailReportResponse();
                        response.setFrom(fromValue);
                        response.setTo(toValue);
                        List<ApListDetailReportResponse> apListDetailReportResponses = reportMapper.getListAPDetail(filter, fromValue, toValue);

                        response.setApListDetailReportResponses(apListDetailReportResponses);

                        // Find total
                        Double totalBalance = 0D;
                        if (apListDetailReportResponses.size() > 0) {
                            for(int k=0; k<apListDetailReportResponses.size(); k++){
                                totalBalance += apListDetailReportResponses.get(k).getOpenBalance();
                            }
                        }
                        response.setGrandTotalBalance(totalBalance);
                        apDetailReportResponseList.add(response);
                    }

                    apDetailReportResponse.addAll(apDetailReportResponseList);

                    APDetailReportResponse response = new APDetailReportResponse();
                    response.setTo(filter.getThroughDay());
                    apDetailReportResponseList.add(response);

                    List<ApListDetailReportResponse> apListDetailReportResponses = reportMapper.getListAPDetail(filter, null, toValue);

                    // Find total
                    Double totalBalance = 0D;
                    if (apListDetailReportResponses.size() > 0) {
                        for(int k=0; k<apListDetailReportResponses.size(); k++){
                            totalBalance += apListDetailReportResponses.get(k).getOpenBalance();
                        }
                    }
                    response.setGrandTotalBalance(totalBalance);
                    response.setApListDetailReportResponses(apListDetailReportResponses);

                    apDetailReportResponse.add(response);
                }
            }


            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/AP Detail/list", null, null, "AP Detail", " AP Detail (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apDetailReportResponse, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/AP Detail/list", 1033L, error.toString(), "AP Detail", "AP Detail (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListProfitLoss(ProfitAndLossReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            InventoryValuationRunner.runValuation(inventoryValuationGateway);

            // Ensure userId is sanitized before use
            List<String> tableNames = Arrays.asList("Total Revenue", "Cost of Goods Sold", "Gross Profit", "Total Expenses",
                    "Net Ordinary Income", "Total Other Revenue", "Total Other Expenses", "Net Other Income",
                    "Earnings Before Interest & Tax",  "Earnings Before Tax","Profit/Loss for the Year");
            String tableName = "general_ledger_detail_bl_" + userId;

            reportMapper.createTable(tableName);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<ProfitLossReportResponse> profitLossReportResponses = reportMapper.getListProfitLoss(filter);

            List<ProfitAndLostReport> profitAndLostReports =new ArrayList<>();

            if (profitLossReportResponses != null && !profitLossReportResponses.isEmpty()) {
                for (ProfitLossReportResponse response : profitLossReportResponses) {
                    ProfitLossReport profitLossReport = new ProfitLossReport();
                    profitLossReport.setDate(response.getDate());
                    profitLossReport.setChartAccountId(response.getChartAccountId());
                    profitLossReport.setCompanyId(response.getCompanyId());
                    profitLossReport.setLocationId(response.getLocationId());
                    profitLossReport.setCustomerId(response.getCustomerId());
                    profitLossReport.setVendorId(response.getVendorId());
                    profitLossReport.setEmployeeId(response.getEmployeeId());
                    profitLossReport.setOtherId(response.getOtherId());
                    profitLossReport.setClassId(response.getClassId());
                    profitLossReport.setDebit(response.getDebit());
                    profitLossReport.setCredit(response.getCredit());
                    // Insert into dynamically created table
                    reportMapper.insertGeneralLedgerDetail(profitLossReport, tableName);
                }
            }

            Double totalRevenue = 0D;
            Double totalCostOfGoodSold = 0D;
            Double totalGrossProfit = 0D;
            Double totalExpend = 0D;
            Double totalNetOrdinaryIncome = 0D;
            Double totalOtherRevenue = 0D;
            Double totalOtherExpenses = 0D;
            Double totalNetOtherIncome = 0D;
            Double totalEarningsBeforeInterestTax = 0D;
            Double totalEarningsBeforeTax = 0D;
            Double totalProfitLoss = 0D;



            if(filter.getColumn() != 0){
                //! BY Duration
                for(int i=0;i<tableNames.size();i++){
                    ProfitAndLostReport profitAndLostReport = new ProfitAndLostReport();
                    profitAndLostReport.setTitle(tableNames.get(i));

                    if(tableNames.get(i).equals("Total Revenue")){
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupDetailIncome(filter);

//                        // Total by column
//                        String idsString = accountGroupDetails.stream()
//                                .map(group -> String.valueOf(group.getAccountGroupId()))
//                                .collect(Collectors.joining(","));
//
//                        List<ChartAccountSubDetail> totalByColumn = new ArrayList<>();
//                        if(accountGroupDetails.size() > 0){
//                            totalByColumn = reportMapper.getBalanceByColumn(filter, idsString);
//                        }
//                        profitAndLostReport.setTotalByColumn(totalByColumn);


                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++){
                            List<chartAccountDetail> subDetail = reportMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = reportMapper.getChartAccountRevenueById(filter, chartAccountId);

                                    // Add to sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }

                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();

                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);

                        }
                        totalRevenue = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }else if(tableNames.get(i).equals("Cost of Goods Sold")) {
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupDetailCostOfGoodsSold(filter);
                        // Find detailed data
                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportMapper.getChartAccountCostByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = reportMapper.getChartAccountById(filter, chartAccountId);

                                    // Add to sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }

                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);

                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalCostOfGoodSold = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }else if(tableNames.get(i).equals("Gross Profit")) {
                        totalGrossProfit = totalRevenue - totalCostOfGoodSold;
                        profitAndLostReport.setTotalAmount(totalGrossProfit);
                    }
                    else if(tableNames.get(i).equals("Total Expenses")) {
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupDetailGrossProfit(filter);

//                        // Total by column
//                        String idsString = accountGroupDetails.stream()
//                                .map(group -> String.valueOf(group.getAccountGroupId()))
//                                .collect(Collectors.joining(","));
//
//
//                        List<ChartAccountSubDetail> totalByColumn = new ArrayList<>();
//                        if(accountGroupDetails.size() > 0){
//                            totalByColumn = reportMapper.getBalanceByColumn(filter, idsString);
//                        }
//                        profitAndLostReport.setTotalByColumn(totalByColumn);

                        Double grandTotalAmount = 0.0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportMapper.getChartAccountCostByGroupId(filter, accountGroupDetails.get(j).getAccountGroupId());

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = reportMapper.getChartAccountById(filter, chartAccountId);

                                    // Add to sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }

                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);

                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                        totalExpend = grandTotalAmount;

                    }
                    else if(tableNames.get(i).equals("Net Ordinary Income")) {
                        totalNetOrdinaryIncome = (totalGrossProfit) - totalExpend;
                        profitAndLostReport.setTotalAmount(totalNetOrdinaryIncome);

                    }
                    else if(tableNames.get(i).equals("Total Other Revenue")){
                        List<accountGroupDetail> accountGroupDetails=reportMapper.getAccountGroupTotalOtherRevenue(filter);

//                        // Total by column
//                        String idsString = accountGroupDetails.stream()
//                                .map(group -> String.valueOf(group.getAccountGroupId()))
//                                .collect(Collectors.joining(","));
//                        List<ChartAccountSubDetail> totalByColumn = new ArrayList<>();
//                        if(accountGroupDetails.size() > 0){
//                            totalByColumn = reportMapper.getBalanceByColumn(filter, idsString);
//                        }
//                        profitAndLostReport.setTotalByColumn(totalByColumn);

                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = reportMapper.getChartAccountById(filter, chartAccountId);

                                    // Add to sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }

                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);

                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                        totalOtherRevenue = grandTotalAmount;

                    }
                    else if(tableNames.get(i).equals("Total Other Expenses")){
                        List<accountGroupDetail> accountGroupDetails=reportMapper.getAccountGroupTotalOtherExpend(filter);

//                        // Total by column
//                        String idsString = accountGroupDetails.stream()
//                                .map(group -> String.valueOf(group.getAccountGroupId()))
//                                .collect(Collectors.joining(","));
//                        List<ChartAccountSubDetail> totalByColumn = new ArrayList<>();
//                        if(accountGroupDetails.size() > 0){
//                            totalByColumn = reportMapper.getBalanceByColumn(filter, idsString);
//                        }
//                        profitAndLostReport.setTotalByColumn(totalByColumn);


                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportMapper.getChartAccountCostByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = reportMapper.getChartAccountById(filter, chartAccountId);

                                    // Add to sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }

                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);

                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                        totalOtherExpenses = grandTotalAmount;

                    }else if(tableNames.get(i).equals("Net Other Income")){
                        totalNetOtherIncome = totalOtherRevenue - totalOtherExpenses;
                        profitAndLostReport.setTotalAmount(totalNetOtherIncome);

                    }else if(tableNames.get(i).equals("Earnings Before Interest & Tax")){
                        totalEarningsBeforeInterestTax = totalNetOrdinaryIncome + totalNetOtherIncome;
                        profitAndLostReport.setTotalAmount(totalEarningsBeforeInterestTax);

                    }else if(tableNames.get(i).equals("Earnings Before Tax")){
                        totalEarningsBeforeTax = totalEarningsBeforeInterestTax;
                        profitAndLostReport.setTotalAmount(totalEarningsBeforeTax);

                    }else if(tableNames.get(i).equals("Profit/Loss for the Year")){
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupTotalTaxExpend(filter);

//                        // Total by column
//                        String idsString = accountGroupDetails.stream()
//                                .map(group -> String.valueOf(group.getAccountGroupId()))
//                                .collect(Collectors.joining(","));
//
//                        List<ChartAccountSubDetail> totalByColumn = new ArrayList<>();
//                        if(accountGroupDetails.size() > 0){
//                            totalByColumn = reportMapper.getBalanceByColumn(filter, accountGroupDetails.get(0).getAccountGroupIds());
//                        }
//                        profitAndLostReport.setTotalByColumn(totalByColumn);

                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportMapper.getChartAccountCostByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));

                            List<chartAccountDetail> subDetail = reportMapper.getChartAccountCostByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = reportMapper.getChartAccountById(filter, chartAccountId);

                                    // Add to sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }
                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);

                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalProfitLoss = totalEarningsBeforeTax - grandTotalAmount;
                        profitAndLostReport.setTotalAmount(totalProfitLoss);

                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }
                    profitAndLostReports.add(profitAndLostReport);
                }
            } else {

                //! BY Total
                for(int i=0;i<tableNames.size();i++){
                    ProfitAndLostReport profitAndLostReport = new ProfitAndLostReport();
                    profitAndLostReport.setTitle(tableNames.get(i));

                    if(tableNames.get(i).equals("Total Revenue")){
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupDetailIncome(filter);

                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++){

                            List<chartAccountDetail> subDetail = reportMapper.getChartAccountByGroupId(filter, accountGroupDetails.get(j).getAccountGroupId());
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalRevenue = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }else if(tableNames.get(i).equals("Cost of Goods Sold")) {
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupDetailCostOfGoodsSold(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportMapper.getChartAccountCostByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalCostOfGoodSold = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }else if(tableNames.get(i).equals("Gross Profit")) {
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupDetailGrossProfit(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                        }
                        totalGrossProfit = totalRevenue - totalCostOfGoodSold;
                        profitAndLostReport.setTotalAmount(totalGrossProfit);
//                    profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }else if(tableNames.get(i).equals("Total Expenses")) {
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupDetailGrossProfit(filter);
                        Double grandTotalAmount = 0.0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportMapper.getChartAccountCostByGroupId(filter, accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                        totalExpend = grandTotalAmount;

                    }else if(tableNames.get(i).equals("Net Ordinary Income")) {
                        totalNetOrdinaryIncome = (totalGrossProfit) - totalExpend;
                        profitAndLostReport.setTotalAmount(totalNetOrdinaryIncome);
                    }else if(tableNames.get(i).equals("Total Other Revenue")){
                        List<accountGroupDetail> accountGroupDetails=reportMapper.getAccountGroupTotalOtherRevenue(filter);
                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                        totalOtherRevenue = grandTotalAmount;

                    } else if(tableNames.get(i).equals("Total Other Expenses")){
                        List<accountGroupDetail> accountGroupDetails=reportMapper.getAccountGroupTotalOtherExpend(filter);
                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportMapper.getChartAccountCostByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                        totalOtherExpenses = grandTotalAmount;

                    }else if(tableNames.get(i).equals("Net Other Income")){
                        totalNetOtherIncome = totalOtherRevenue - totalOtherExpenses;
                        profitAndLostReport.setTotalAmount(totalNetOtherIncome);

                    }else if(tableNames.get(i).equals("Earnings Before Interest & Tax")){
                        totalEarningsBeforeInterestTax = totalNetOrdinaryIncome + totalNetOtherIncome;
                        profitAndLostReport.setTotalAmount(totalEarningsBeforeInterestTax);

                    }else if(tableNames.get(i).equals("Earnings Before Tax")){
                        totalEarningsBeforeTax = totalEarningsBeforeInterestTax;
                        profitAndLostReport.setTotalAmount(totalEarningsBeforeTax);

                    }else if(tableNames.get(i).equals("Profit/Loss for the Year")){
                        List<accountGroupDetail> accountGroupDetails = reportMapper.getAccountGroupTotalTaxExpend(filter);

                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportMapper.getChartAccountCostByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalProfitLoss = totalEarningsBeforeTax - grandTotalAmount;
                        profitAndLostReport.setTotalAmount(totalProfitLoss);

                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }
                    profitAndLostReports.add(profitAndLostReport);
                }
            }

            //! Delete temporary table
            reportMapper.dropTable(tableName);
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/get profit and loss/list", null, null, "get prof it  and lost", "  (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", profitAndLostReports, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/get profit and lost/list", 1033L, error.toString(), "get profit and lost", "get profit and lost (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListBalanceSheet(ProfitAndLossReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        boolean tempTableCreated = false;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            // Ensure userId is sanitized before use
            List<String> tableNames = Arrays.asList("Total Current Asset",
                    "Total Fixed Asset", "Total Other Asset", "Total Asset",
                    "Total Current Liability", "Total Long Term Liability",
                    "Total Liability", "Total Equity", "Total Liability & Equity");

            String tableName = "general_ledger_detail_ps_"+userId;

            System.out.println("tableName: " + tableName);

            // Always recreate temp table to avoid stale/oversized data
            reportMapper.dropTable(tableName);
            reportMapper.createTable(tableName);
            tempTableCreated = true;
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProfitLossReportResponse> profitLossReportResponses = reportBalanceSheetMapper.getListProfitLoss(filter);

            List<ProfitAndLostReport> profitAndLostReports =new ArrayList<>();

            if (profitLossReportResponses != null && !profitLossReportResponses.isEmpty()) {
                List<ProfitLossReport> profitLossReports = new ArrayList<>(profitLossReportResponses.size());
                for (ProfitLossReportResponse response : profitLossReportResponses) {
                    ProfitLossReport profitLossReport = new ProfitLossReport();
                    profitLossReport.setDate(response.getDate());
                    profitLossReport.setChartAccountId(response.getChartAccountId());
                    profitLossReport.setCompanyId(response.getCompanyId());
                    profitLossReport.setLocationId(response.getLocationId());
                    profitLossReport.setCustomerId(response.getCustomerId());
                    profitLossReport.setVendorId(response.getVendorId());
                    profitLossReport.setEmployeeId(response.getEmployeeId());
                    profitLossReport.setOtherId(response.getOtherId());
                    profitLossReport.setClassId(response.getClassId());
                    profitLossReport.setDebit(response.getDebit());
                    profitLossReport.setCredit(response.getCredit());
                    profitLossReports.add(profitLossReport);
                }
                reportBalanceSheetMapper.insertGeneralLedgerDetailBatch(profitLossReports, tableName);
            }


            // Declare variable
            Double totalCurrentAsset = 0D;
            Double totalFixAsset = 0D;
            Double totalOtherAsset = 0D;
            Double totalAsset = 0D;
            Double totalCurrentLiability = 0D;
            Double totalLongTermLiability = 0D;
            Double totalLiability = 0D;
            Double totalEquity = 0D;
            Double totalLiabilityEquity = 0D;

            if(filter.getColumn() != 0){

                List<String> dates = getDateList(filter.getDateFrom(), filter.getDateTo(), filter.getColumn());

                //! ============================= By Column of Report ===============================
                for(int i=0;i<tableNames.size();i++){
                    ProfitAndLostReport profitAndLostReport = new ProfitAndLostReport();
                    profitAndLostReport.setTitle(tableNames.get(i));
                    profitAndLostReport.setTotalAmount(10D);
                    filter.setTableName(tableName);

                    if(tableNames.get(i).equals("Total Current Asset")){
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalCurrentAsset(filter);

                        for (int j = 0; j < accountGroupDetails.size(); j++){
                            List<chartAccountDetail> subDetail = reportBalanceSheetMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());

                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailByOne = reportBalanceSheetMapper.getChartAccountBalanceSheetById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailByOne);
                                    }

                                    // Add to the last sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }

                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);
                        }
                        totalCurrentAsset = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }

                    else if(tableNames.get(i).equals("Total Fixed Asset")) {
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalFixedAsset(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportBalanceSheetMapper.getChartAccountByGroupIdAndParent(filter, accountGroupDetails.get(j).getAccountGroupId());
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailByOne = reportBalanceSheetMapper.getChartAccountBalanceSheetById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailByOne);
                                    }

                                    // Add to the last sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }
                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);
                        }
                        totalFixAsset = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }
                    else if(tableNames.get(i).equals("Total Other Asset")){
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalOtherAsset(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++){
                            List<chartAccountDetail> subDetail = reportBalanceSheetMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailByOne = reportBalanceSheetMapper.getChartAccountBalanceSheetById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailByOne);
                                    }

                                    // Add to the last sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }
                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);
                        }
                        totalOtherAsset = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }
                    else if(tableNames.get(i).equals("Total Asset")) {

                        totalAsset = totalCurrentAsset + totalFixAsset + totalOtherAsset;

                        profitAndLostReport.setTotalAmount(totalAsset);

                    }else if(tableNames.get(i).equals("Total Current Liability")) {
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalCurrentLiability(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportBalanceSheetMapper.getChartAccountCreditByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailByOne = reportBalanceSheetMapper.getChartAccountBalanceSheetPayableById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailByOne);
                                    }

                                    // Add to the last sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }
                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);
                        }
                        totalCurrentLiability = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }else if(tableNames.get(i).equals("Total Long Term Liability")){
                        List<accountGroupDetail> accountGroupDetails=reportBalanceSheetMapper.getAccountGroupTotalLongTermLiability(filter);
                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportBalanceSheetMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailByOne = reportBalanceSheetMapper.getChartAccountBalanceSheetPayableById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailByOne);
                                    }

                                    // Add to the last sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }
                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);

                        }
                        totalLongTermLiability = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }
                    else if(tableNames.get(i).equals("Total Liability")) {

                        totalLiability = totalCurrentLiability + totalLongTermLiability;

                        profitAndLostReport.setTotalAmount(totalLiability);

                    }
                    else if(tableNames.get(i).equals("Total Equity")){
                        List<accountGroupDetail> accountGroupDetails=reportBalanceSheetMapper.getAccountGroupTotalEquity(filter);
                        Double grandTotalAmount = 0D;

                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            List<chartAccountDetail> subDetail = reportBalanceSheetMapper.getChartAccountCreditByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId());
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();

                            if(subDetail.size() > 0){
                                for (int k = 0; k < subDetail.size(); k++){
                                    Long chartAccountId = subDetail.get(k).getCharAccountId();

                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailByOne = reportBalanceSheetMapper.getChartAccountBalanceSheetPayableById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailByOne);
                                    }

                                    // Add to the last sub total
                                    subDetail.get(k).setChartAccountSubDetails(chartAccountSubDetails);
                                }
                            }
                            // Add to total
                            accountGroupDetails.get(j).setChartAccountDetails(subDetail);
                        }

                        //! Find profit loss by period
                        accountGroupDetail profidLoss = new accountGroupDetail();
                        {
                            //1. Create main
                            profidLoss.setAccountGroupId(0L);
                            profidLoss.setAccountGroupName("Profit/Loss for the Period");

                            // Find Profit loss of year
                            Double profitLossOfYearAmount = findProfitLossOfYear(filter, filter.getColumn());
                            profidLoss.setTotalAmount(profitLossOfYearAmount);

                            List<chartAccountDetail> detail = new ArrayList<>();

                            chartAccountDetail chartAccountDetail = new chartAccountDetail();
                            chartAccountDetail.setCharAccountId(0L);
                            chartAccountDetail.setChartAccountName("Profit/Loss for the Period");
                            chartAccountDetail.setTotalAmount(profitLossOfYearAmount);

                            // Find profit loss by dates
                            List<ChartAccountSubDetail> chartAccountSubDetails1 = new ArrayList<>();
                            for (int l = 0; l < dates.size(); l++){
                                ChartAccountSubDetail chartAccountSubDetail = new ChartAccountSubDetail();
                                chartAccountSubDetail.setDate(dates.get(l));

                                ProfitAndLossReportFilter filter1 = new ProfitAndLossReportFilter();
                                filter1.setDateTo(dates.get(l));
                                Double profitLossOfYearAmountByDate = findProfitLossOfYear(filter1, filter.getColumn());
                                chartAccountSubDetail.setTotalAmount(profitLossOfYearAmountByDate);
                                chartAccountSubDetails1.add(chartAccountSubDetail);
                            }
                            chartAccountDetail.setChartAccountSubDetails(chartAccountSubDetails1);

                            detail.add(chartAccountDetail);

                            profidLoss.setChartAccountDetails(detail);
                        }


                        // Set to main
                        totalEquity = grandTotalAmount + profidLoss.getTotalAmount();

                        accountGroupDetails.add(profidLoss);
                        profitAndLostReport.setTotalAmount(totalEquity);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }else if(tableNames.get(i).equals("Total Liability & Equity")){

                        totalLiabilityEquity = totalLiability + totalEquity;
                        profitAndLostReport.setTotalAmount(totalLiabilityEquity);

                    }
                    profitAndLostReports.add(profitAndLostReport);
                }
            } else {
                //! =========================== By Total of Report ===============================
                for(int i=0;i<tableNames.size();i++){
                    ProfitAndLostReport profitAndLostReport = new ProfitAndLostReport();
                    profitAndLostReport.setTitle(tableNames.get(i));
                    profitAndLostReport.setTotalAmount(10D);
                    filter.setTableName(tableName);

                    if(tableNames.get(i).equals("Total Current Asset")){
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalCurrentAsset(filter);

                        for (int j = 0; j < accountGroupDetails.size(); j++){
                            accountGroupDetails.get(j).setChartAccountDetails(reportBalanceSheetMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalCurrentAsset = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }

                    else if(tableNames.get(i).equals("Total Fixed Asset")) {
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalFixedAsset(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportBalanceSheetMapper.getChartAccountByGroupIdAndParent(filter, accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalFixAsset = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }
                    else if(tableNames.get(i).equals("Total Other Asset")){
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalOtherAsset(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++){
                            accountGroupDetails.get(j).setChartAccountDetails(reportBalanceSheetMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalOtherAsset = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);
                    }
                    else if(tableNames.get(i).equals("Total Asset")) {

                        totalAsset = totalCurrentAsset + totalFixAsset + totalOtherAsset;

                        profitAndLostReport.setTotalAmount(totalAsset);

                    }else if(tableNames.get(i).equals("Total Current Liability")) {
                        Double grandTotalAmount = 0D;
                        List<accountGroupDetail> accountGroupDetails = reportBalanceSheetMapper.getAccountGroupTotalCurrentLiability(filter);
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportBalanceSheetMapper.getChartAccountCreditByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalCurrentLiability = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }else if(tableNames.get(i).equals("Total Long Term Liability")){
                        List<accountGroupDetail> accountGroupDetails=reportBalanceSheetMapper.getAccountGroupTotalLongTermLiability(filter);
                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportBalanceSheetMapper.getChartAccountByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }
                        totalLongTermLiability = grandTotalAmount;
                        profitAndLostReport.setTotalAmount(grandTotalAmount);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }
                    else if(tableNames.get(i).equals("Total Liability")) {

                        totalLiability = totalCurrentLiability + totalLongTermLiability;

                        profitAndLostReport.setTotalAmount(totalLiability);

                    }
                    else if(tableNames.get(i).equals("Total Equity")){
                        List<accountGroupDetail> accountGroupDetails=reportBalanceSheetMapper.getAccountGroupTotalEquity(filter);
                        Double grandTotalAmount = 0D;
                        for (int j = 0; j < accountGroupDetails.size(); j++) {
                            accountGroupDetails.get(j).setChartAccountDetails(reportBalanceSheetMapper.getChartAccountCreditByGroupId(filter,accountGroupDetails.get(j).getAccountGroupId()));
                            grandTotalAmount += accountGroupDetails.get(j).getTotalAmount();
                        }

                        //! Find profit loss by period
                        accountGroupDetail profidLoss = new accountGroupDetail();

                        List<chartAccountDetail> detail = new ArrayList<>();

                        profidLoss.setAccountGroupId(0L);
                        profidLoss.setAccountGroupName("Profit/Loss for the Period");
                        profidLoss.setChartAccountDetails(detail);

                        // Find Profit loss of year
                        Double profitLossOfYearAmount = findProfitLossOfYear(filter, filter.getColumn());

                        profidLoss.setTotalAmount(profitLossOfYearAmount);

                        // Set to main
                        totalEquity = grandTotalAmount + profidLoss.getTotalAmount();

                        accountGroupDetails.add(profidLoss);
                        profitAndLostReport.setTotalAmount(totalEquity);
                        profitAndLostReport.setAccountGroupDetals(accountGroupDetails);

                    }else if(tableNames.get(i).equals("Total Liability & Equity")){

                        totalLiabilityEquity = totalLiability + totalEquity;
                        profitAndLostReport.setTotalAmount(totalLiabilityEquity);

                    }
                    profitAndLostReports.add(profitAndLostReport);
                }
            }

            reportMapper.dropTable(tableName);

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/balance sheet/list", null, null, "balance sheet", " balance sheet (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", profitAndLostReports, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/balance sheet/list", 1033L, error.toString(), "balance sheet", "balance sheet (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        } finally {
            if (tempTableCreated) {
                try {
                    reportMapper.dropTable("general_ledger_detail_ps_"+userService.getUserAuth().getId());
                } catch (Exception ignored) { }
            }
        }
    }
    @Override
    public ResponseMessage<BaseResult> cashFlow(cashFlowReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            // Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<String> tableNames = Arrays.asList(
                    "Net Cash from Operating Activities",
                    "Net Cash from Investing Activities",
                    "Net Cash from Financing Activities",
                    "Net Cash Increase/(Decrease) for Period",
                    "Cash & Bank at Beginning of Period",
                    "Cash & Bank at End of Period",
                    "Total per Statement of Financial Position"
            );

            List<CashFlowReportResponse> cashFlowReportResponses = new ArrayList<>();

            Double totalOperatingActivity = 0D;
            Double totalInvestingActivity = 0D;
            Double totalFinancingActivity = 0D;
            Double totalIncreaseDecrease = 0D;
            Double totalEndofPeriod = 0D;

            if(filter.getColumns() != 0){

                List<String> dates = getDateList(filter.getDateFrom(), filter.getDateTo(), 3L);

                // ========================================== BY MONTH============================================
                for (int i = 0; i < tableNames.size(); i++) {
                    CashFlowReportResponse cashFlowReportResponse = new CashFlowReportResponse();

                    if (tableNames.get(i).equals("Net Cash from Operating Activities")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));

                        // Net Income
                        Double netIncome = reportMapper.getNetInCom(filter);
                        cashFlowReportResponse.setNetIncome(netIncome);
                        List<ChartAccountSubDetail> chartAccountSubDetailNetIncome = reportMapper.getNetInComeByMonth(filter);
                        cashFlowReportResponse.setNetIncomeByMonth(chartAccountSubDetailNetIncome);

                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListCashFlowOperating(filter);
                        cashFlowReportResponse.setCashFlowGroupResponses(cashFlowGroupResponses);
                        Double grandTotalAmount = 0D;

                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListOperationChartAccount(cashFlowGroupResponses.get(j).getGroupId(), filter);

                            if(chartAccountResponses.size() > 0){
                                for (int k = 0; k < chartAccountResponses.size(); k++){
                                    Long chartAccountId = chartAccountResponses.get(k).getChartAccountId();

                                    // Find by month
                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailTmp = reportMapper.getChartAccountCashFlowMonthById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailTmp);
                                    }

                                    if (chartAccountSubDetails.size() > 0){
                                        for (int l = 0; l < chartAccountSubDetails.size(); l++){
                                            Double totalAmount = chartAccountSubDetails.get(l).getCredit() - chartAccountSubDetails.get(l).getDebit();
                                            chartAccountSubDetails.get(l).setTotalAmount(totalAmount);
                                        }
                                    }

                                    // Add to sub total
                                    chartAccountResponses.get(k).setChartAccountSubDetails(chartAccountSubDetails);

                                    grandTotalAmount += chartAccountResponses.get(k).getTotalAmount() != null ? chartAccountResponses.get(k).getTotalAmount() : 0D;
                                }
                            }
                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                        }

                        // Find Total Case From Operation
                        Double totalCaseFromOperation = grandTotalAmount + netIncome;

                        totalOperatingActivity = totalCaseFromOperation;

                        cashFlowReportResponse.setTotalAmount(totalCaseFromOperation);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }

                    if(tableNames.get(i).equals("Net Cash from Investing Activities")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));

                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListCashFlowInvesting(filter);

                        Double grandTotalAmount=0D;
                        List<CashFlowGroupResponse> filteredInvestingGroups = new ArrayList<>();
                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            double groupTotal = 0D;
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListOperationChartAccount(cashFlowGroupResponses.get(j).getGroupId(), filter);

                            if(chartAccountResponses.size() > 0){
                                for (int k = 0; k < chartAccountResponses.size(); k++){
                                    Long chartAccountId = chartAccountResponses.get(k).getChartAccountId();

                                    // Find by month
                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailTmp = reportMapper.getChartAccountCashFlowMonthById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailTmp);
                                    }

                                    if (chartAccountSubDetails.size() > 0){
                                        for (int l = 0; l < chartAccountSubDetails.size(); l++){
                                            Double totalAmount = chartAccountSubDetails.get(l).getCredit() - chartAccountSubDetails.get(l).getDebit();
                                            chartAccountSubDetails.get(l).setTotalAmount(totalAmount);
                                        }
                                    }

                                    // Add to sub total
                                    chartAccountResponses.get(k).setChartAccountSubDetails(chartAccountSubDetails);

                                    groupTotal += chartAccountResponses.get(k).getTotalAmount() != null ? chartAccountResponses.get(k).getTotalAmount() : 0D;
                                }
                            }

                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                            cashFlowGroupResponses.get(j).setTotalAmount(groupTotal);
                            if (groupTotal != 0D) {
                                filteredInvestingGroups.add(cashFlowGroupResponses.get(j));
                                grandTotalAmount += groupTotal;
                            }
                        }
                        totalInvestingActivity = grandTotalAmount;
                        cashFlowReportResponse.setCashFlowGroupResponses(filteredInvestingGroups);
                        cashFlowReportResponse.setTotalAmount(grandTotalAmount);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }
                    if (tableNames.get(i).equals("Net Cash from Financing Activities")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListCashFlowFinancing(filter);

                        Double grandTotalAmount=0D;
                        List<CashFlowGroupResponse> filteredFinancingGroups = new ArrayList<>();
                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            double groupTotal = 0D;
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListChartAccountCredit(cashFlowGroupResponses.get(j).getGroupId(), filter);
                            if(chartAccountResponses.size() > 0){
                                for (int k = 0; k < chartAccountResponses.size(); k++){
                                    Long chartAccountId = chartAccountResponses.get(k).getChartAccountId();

                                    // Find by month
//                                    List<ChartAccountSubDetail> chartAccountSubDetails = reportMapper.getChartAccountCashFlowMonthById(filter, chartAccountId);
                                    // Find by month
                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailTmp = reportMapper.getChartAccountCashFlowMonthById(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailTmp);
                                    }
                                    if (chartAccountSubDetails.size() > 0){
                                        for (int l = 0; l < chartAccountSubDetails.size(); l++){
                                            Double totalAmount = chartAccountSubDetails.get(l).getCredit() - chartAccountSubDetails.get(l).getDebit();
                                            chartAccountSubDetails.get(l).setTotalAmount(totalAmount);
                                        }
                                    }

                                    // Add to sub total
                                    chartAccountResponses.get(k).setChartAccountSubDetails(chartAccountSubDetails);

                                    groupTotal += chartAccountResponses.get(k).getTotalAmount() != null ? chartAccountResponses.get(k).getTotalAmount() : 0D;
                                }
                            }
                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                            cashFlowGroupResponses.get(j).setTotalAmount(groupTotal);
                            if (groupTotal != 0D) {
                                filteredFinancingGroups.add(cashFlowGroupResponses.get(j));
                                grandTotalAmount += groupTotal;
                            }
                        }
                        totalFinancingActivity = grandTotalAmount;
                        cashFlowReportResponse.setCashFlowGroupResponses(filteredFinancingGroups);
                        cashFlowReportResponse.setTotalAmount(grandTotalAmount);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }
                    if (tableNames.get(i).equals("Net Cash Increase/(Decrease) for Period")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        totalIncreaseDecrease = totalOperatingActivity + totalInvestingActivity + totalFinancingActivity;
                        cashFlowReportResponse.setTotalAmount(totalIncreaseDecrease);

                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }

                    if (tableNames.get(i).equals("Cash & Bank at End of Period")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        List<CashFlowGroupResponse> cashFlowGroupResponses = new ArrayList<>();

                        CashFlowGroupResponse cashFlowGroupResponse = new CashFlowGroupResponse();
                        cashFlowGroupResponse.setGroupName("Cash & Bank at Beginning of Period");
                        cashFlowGroupResponse.setGroupId(0L);
                        cashFlowGroupResponse.setChartAccountResponses(new ArrayList<>());

                        // Find Cash & Bank at End of Period Amount
                        List<CashFlowGroupResponse> cashFlowGroupResponsesCashBank = reportMapper.getListTotalPerStatement(filter);
                        Double grandTotalAmount=0D;
                        // Aggregate beginning balances by month across all Cash & Bank accounts

                        // Map<String, Double> beginByMonth = new LinkedHashMap<>();
                        for (int j = 0; j < cashFlowGroupResponsesCashBank.size(); j++) {
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListChartAccountCashBankPeriod(cashFlowGroupResponsesCashBank.get(j).getGroupId(), filter);

                            if(chartAccountResponses.size() > 0){
                                for (int k = 0; k < chartAccountResponses.size(); k++){
                                    // Find by month
                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailTmp = reportMapper.getChartAccountCashFlowMonthStatementById(filter, cashFlowGroupResponsesCashBank.get(j).getGroupId(), dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailTmp);
                                    }

                                    // Attach per-account details
                                    chartAccountResponses.get(k).setChartAccountSubDetails(chartAccountSubDetails);

                                    // Add to sub total
                                    grandTotalAmount += chartAccountResponses.get(k).getTotalAmount() != null ? chartAccountResponses.get(k).getTotalAmount() : 0D;

                                    cashFlowGroupResponse.setChartAccountSubDetails(chartAccountSubDetails);
                                }

                            }
                        }

                        cashFlowGroupResponse.setTotalAmount(grandTotalAmount);
                        cashFlowGroupResponses.add(cashFlowGroupResponse);

                        totalEndofPeriod = totalIncreaseDecrease + cashFlowGroupResponse.getTotalAmount();
                        cashFlowReportResponse.setTotalAmount(totalEndofPeriod);

                        cashFlowReportResponse.setCashFlowGroupResponses(cashFlowGroupResponses);

                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }

                    if (tableNames.get(i).equals("Total per Statement of Financial Position")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListTotalPerStatement(filter);
                        cashFlowReportResponse.setCashFlowGroupResponses(cashFlowGroupResponses);
                        Double grandTotalAmount=0D;
                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListChartAccountCashBank(cashFlowGroupResponses.get(j).getGroupId(), filter);

                            if(chartAccountResponses.size() > 0){
                                for (int k = 0; k < chartAccountResponses.size(); k++){
                                    Long chartAccountId = chartAccountResponses.get(k).getChartAccountId();

                                    // Find by month
                                    List<ChartAccountSubDetail> chartAccountSubDetails = new ArrayList<>();

                                    for (int l = 0; l < dates.size(); l++){
                                        List<ChartAccountSubDetail> chartAccountSubDetailTmp = reportMapper.getChartAccountCashFlowMonthStatementByChartAccountId(filter, chartAccountId, dates.get(l));

                                        chartAccountSubDetails.addAll(chartAccountSubDetailTmp);
                                    }

                                    chartAccountResponses.get(k).setChartAccountSubDetails(chartAccountSubDetails);

                                    grandTotalAmount += chartAccountResponses.get(k).getTotalAmount() != null ? chartAccountResponses.get(k).getTotalAmount() : 0D;
                                }
                            }

                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                        }
                        cashFlowReportResponse.setTotalAmount(grandTotalAmount);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }
                }

            } else {
                // ========================================== BY TOTAL============================================
                for (int i = 0; i < tableNames.size(); i++) {
                    CashFlowReportResponse cashFlowReportResponse = new CashFlowReportResponse();

                    if (tableNames.get(i).equals("Net Cash from Operating Activities")) {
                        // Net Income
                        Double netIncome = reportMapper.getNetInCom(filter);
                        cashFlowReportResponse.setNetIncome(netIncome);
                        cashFlowReportResponse.setTitle(tableNames.get(i));

                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListCashFlowOperating(filter);
                        cashFlowReportResponse.setCashFlowGroupResponses(cashFlowGroupResponses);
                        Double grandTotalAmount = 0D;

                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListOperationChartAccount(cashFlowGroupResponses.get(j).getGroupId(), filter);
                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                            for (ChartAccountResponse chartAccountResponse : chartAccountResponses) {
                                grandTotalAmount += chartAccountResponse.getTotalAmount() != null ? chartAccountResponse.getTotalAmount() : 0D;
                            }
                        }

                        // Find Total Case From Operation
                        Double totalCaseFromOperation = grandTotalAmount + netIncome;

                        totalOperatingActivity = totalCaseFromOperation;

                        cashFlowReportResponse.setTotalAmount(totalCaseFromOperation);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }

                    if(tableNames.get(i).equals("Net Cash from Investing Activities")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));

                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListCashFlowInvesting(filter);
                        Double grandTotalAmount=0D;
                        List<CashFlowGroupResponse> filteredInvestingGroups = new ArrayList<>();
                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            double groupTotal = 0D;
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListOperationChartAccount(cashFlowGroupResponses.get(j).getGroupId(), filter);
                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                            for (ChartAccountResponse chartAccountResponse : chartAccountResponses) {
                                groupTotal += chartAccountResponse.getTotalAmount() != null ? chartAccountResponse.getTotalAmount() : 0D;
                            }
                            cashFlowGroupResponses.get(j).setTotalAmount(groupTotal);
                            if (groupTotal != 0D) {
                                filteredInvestingGroups.add(cashFlowGroupResponses.get(j));
                                grandTotalAmount += groupTotal;
                            }
                        }
                        cashFlowReportResponse.setCashFlowGroupResponses(filteredInvestingGroups);
                        totalInvestingActivity = grandTotalAmount;
                        cashFlowReportResponse.setTotalAmount(grandTotalAmount);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }
                    if (tableNames.get(i).equals("Net Cash from Financing Activities")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListCashFlowFinancing(filter);
                        Double grandTotalAmount=0D;
                        List<CashFlowGroupResponse> filteredFinancingGroups = new ArrayList<>();
                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            double groupTotal = 0D;
                            List<ChartAccountResponse> chartAccountResponses;
                            if ("Long Term Liability".equalsIgnoreCase(cashFlowGroupResponses.get(j).getTypeName())) {
                                chartAccountResponses = reportMapper.getListChartAccountDebit(cashFlowGroupResponses.get(j).getGroupId(), filter);
                            } else {
                                chartAccountResponses = reportMapper.getListChartAccountCredit(cashFlowGroupResponses.get(j).getGroupId(), filter);
                            }
                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                            for (ChartAccountResponse chartAccountResponse : chartAccountResponses) {
                                groupTotal += chartAccountResponse.getTotalAmount() != null ? chartAccountResponse.getTotalAmount() : 0D;
                            }
                            cashFlowGroupResponses.get(j).setTotalAmount(groupTotal);
                            if (groupTotal != 0D) {
                                filteredFinancingGroups.add(cashFlowGroupResponses.get(j));
                                grandTotalAmount += groupTotal;
                            }
                        }
                        cashFlowReportResponse.setCashFlowGroupResponses(filteredFinancingGroups);
                        totalFinancingActivity = grandTotalAmount;
                        cashFlowReportResponse.setTotalAmount(grandTotalAmount);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }
                    if (tableNames.get(i).equals("Net Cash Increase/(Decrease) for Period")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        totalIncreaseDecrease = totalOperatingActivity + totalInvestingActivity + totalFinancingActivity;
                        cashFlowReportResponse.setTotalAmount(totalIncreaseDecrease);

                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }

                    if (tableNames.get(i).equals("Cash & Bank at End of Period")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        List<CashFlowGroupResponse> cashFlowGroupResponses = new ArrayList<>();

                        CashFlowGroupResponse cashFlowGroupResponse = new CashFlowGroupResponse();
                        cashFlowGroupResponse.setGroupName("Cash & Bank at End of Period");
                        cashFlowGroupResponse.setGroupId(0L);
                        cashFlowGroupResponse.setChartAccountResponses(new ArrayList<>());

                        // Find Cash & Bank at End of Period Amount
                        List<CashFlowGroupResponse> cashFlowGroupResponsesCashBank = reportMapper.getListTotalPerStatement(filter);
                        Double grandTotalAmount=0D;
                        for (int j = 0; j < cashFlowGroupResponsesCashBank.size(); j++) {
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListChartAccountCashBankPeriod(cashFlowGroupResponsesCashBank.get(j).getGroupId(), filter);
                            for (ChartAccountResponse chartAccountResponse : chartAccountResponses) {
                                grandTotalAmount += chartAccountResponse.getTotalAmount() != null ? chartAccountResponse.getTotalAmount() : 0D;
                            }
                        }

                        cashFlowGroupResponse.setTotalAmount(grandTotalAmount);
                        cashFlowGroupResponses.add(cashFlowGroupResponse);

                        totalEndofPeriod = totalIncreaseDecrease + cashFlowGroupResponse.getTotalAmount();
                        cashFlowReportResponse.setTotalAmount(totalEndofPeriod);

                        cashFlowReportResponse.setCashFlowGroupResponses(cashFlowGroupResponses);

                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }

                    if (tableNames.get(i).equals("Total per Statement of Financial Position")) {
                        cashFlowReportResponse.setTitle(tableNames.get(i));
                        List<CashFlowGroupResponse> cashFlowGroupResponses = reportMapper.getListTotalPerStatement(filter);
                        cashFlowReportResponse.setCashFlowGroupResponses(cashFlowGroupResponses);
                        Double grandTotalAmount=0D;
                        for (int j = 0; j < cashFlowGroupResponses.size(); j++) {
                            List<ChartAccountResponse> chartAccountResponses = reportMapper.getListChartAccountCashBank(cashFlowGroupResponses.get(j).getGroupId(), filter);
                            cashFlowGroupResponses.get(j).setChartAccountResponses(chartAccountResponses);
                            for (ChartAccountResponse chartAccountResponse : chartAccountResponses) {
                                grandTotalAmount += chartAccountResponse.getTotalAmount() != null ? chartAccountResponse.getTotalAmount() : 0D;
                            }
                        }
                        cashFlowReportResponse.setTotalAmount(grandTotalAmount);
                        cashFlowReportResponses.add(cashFlowReportResponse);
                    }
                }
            }



            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/cash flow/list", null, null, "cash flow", " cash flow (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", cashFlowReportResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/cash flow/list", 1033L, error.toString(), "cash flow", "cash flow (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> Reconcile(ReconcileReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            filter.setUserId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<String> titleName = Arrays.asList("Cleared Transactions", "Uncleared Transactions", "New Transactions");
            List<ReconcileReportResponse> reconcileReportResponses = new ArrayList<>();


            Double beginningBalance = 0D;
            Double clearedTotalAmount = 0D;
            Double clearedTotalBalance = 0D;
            Double UnclearedTotalAmount = 0D;
//            Double UnclearedTotalBalance = 0D;
            Double newTotalAmount = 0D;
//            Double newTotalBalance = 0D;


            for (int j = 0; j < titleName.size(); j++) {
                String title = titleName.get(j);

                try {

                    //! 1 --- Cleared Transactions Block ---
                    if ("Cleared Transactions".equals(title)) {
                        ReconcileReportResponse response = new ReconcileReportResponse();
                        response.setTitle(title);

                        // Fetch beginning balance
                        beginningBalance = reportMapper.findBeginningBalanceAmount(filter);
                        if(beginningBalance == null){
                            beginningBalance = 0D;
                        }
                        response.setBeginningBalanceAmount(beginningBalance);

                        // Fetch check payment details
                        List<ReconcileCheckPaymentDetail> checkDetails = reportMapper.findReconcileCheckPaymentDetails(filter);
                        if (checkDetails != null && !checkDetails.isEmpty()) {
                            Double balance = 0D;
                            for (ReconcileCheckPaymentDetail detail : checkDetails) {
                                balance += detail.getAmount() != null ? detail.getAmount() : 0D;
                                detail.setBalance(balance);
                            }
                            checkDetails.get(0).setTotalAmount(balance);
                            checkDetails.get(0).setTotalBalance(balance);
                        } else {
                            checkDetails = new ArrayList<>(); // Ensure non-null list
                        }
                        response.setReconcileCheckPaymentDetails(checkDetails);

                        // Fetch deposit details
                        List<ReconcileDepositsCreditsDetail> depositDetails = reportMapper.findReconcileDepositsCreditsDetails(filter);

                        if (depositDetails != null && !depositDetails.isEmpty()) {
                            Double balance = 0D;
                            for (ReconcileDepositsCreditsDetail detail : depositDetails) {
                                balance += detail.getAmount() != null ? detail.getAmount() : 0D;
                                detail.setBalance(balance);
                            }
                            depositDetails.get(0).setTotalAmount(balance);
                            depositDetails.get(0).setTotalBalance(balance);
                        } else {
                            depositDetails = new ArrayList<>(); // Ensure non-null list
                        }
                        response.setReconcileDepositsCreditsDetails(depositDetails);

                        // Calculate totals
                        Double totalAmount = 0D;
                        Double totalBalance = 0D;
                        if (!checkDetails.isEmpty()) {
                            totalAmount += checkDetails.get(0).getTotalBalance();
                        }
                        if (!depositDetails.isEmpty()) {
                            totalAmount += depositDetails.get(0).getTotalAmount();
                        }
                        totalBalance = totalAmount + response.getBeginningBalanceAmount();

                        response.setTotalAmount(totalAmount);
                        response.setTotalBalance(totalBalance);

                        clearedTotalAmount = totalAmount;
                        clearedTotalBalance = totalBalance;

                        reconcileReportResponses.add(response);
                    }


                    //! 2 ------- Uncleared Transactions -----------
                    if("Uncleared Transactions".equals(title)) {
                        ReconcileReportResponse response = new ReconcileReportResponse();
                        response.setTitle(title);

                        // Fetch check payment transaction details
                        List<ReconcileCheckPaymentTransactionDetail> checkTxDetails = reportMapper.findReconcileUnclearedTransactionsDetails(filter);
                        if (checkTxDetails != null && !checkTxDetails.isEmpty()) {
                            Double balance = 0D;
                            for (ReconcileCheckPaymentTransactionDetail detail : checkTxDetails) {
                                balance += detail.getAmount() != null ? detail.getAmount() : 0D;
                                detail.setBalance(balance);
                            }
                            checkTxDetails.get(0).setTotalAmount(balance);
                            checkTxDetails.get(0).setTotalBalance(balance);
                        } else {
                            checkTxDetails = new ArrayList<>(); // Ensure non-null list
                        }
                        response.setReconcileCheckPaymentTransactionsDetails(checkTxDetails);

                        // Calculate totals
                        Double totalAmount = 0D;

                        if (!checkTxDetails.isEmpty()) {
                            totalAmount += checkTxDetails.get(0).getTotalBalance();
                        }

                        UnclearedTotalAmount = totalAmount;
//                        UnclearedTotalBalance = totalAmount;

                        response.setTotalAmount(totalAmount + clearedTotalAmount);
                        response.setTotalBalance(totalAmount + clearedTotalBalance);

                        reconcileReportResponses.add(response);
                    }


                    //! 3 --- New Transactions Block ---
                    if ("New Transactions".equals(title)) {
                        ReconcileReportResponse response = new ReconcileReportResponse();
                        response.setTitle(title);

                        // Fetch check payment transaction details
                        List<ReconcileCheckPaymentTransactionDetail> checkTxDetails = reportMapper.findReconcileCheckPaymentTransactionsDetails(filter);

                        if (checkTxDetails != null && !checkTxDetails.isEmpty()) {
                            Double balance = 0D;
                            for (ReconcileCheckPaymentTransactionDetail detail : checkTxDetails) {
                                balance += detail.getAmount() != null ? detail.getAmount() : 0D;
                                detail.setBalance(balance);
                            }
                            checkTxDetails.get(0).setTotalAmount(balance);
                            checkTxDetails.get(0).setTotalBalance(balance);
                        } else {
                            checkTxDetails = new ArrayList<>(); // Ensure non-null list
                        }
                        response.setReconcileCheckPaymentTransactionsDetails(checkTxDetails);

                        // Fetch deposit transaction details
                        List<ReconcileDepositsCreditTransactionDetail> depositTxDetails = reportMapper.findReconcileDepositsCreditsTransactionsDetails(filter);
                        if (depositTxDetails != null && !depositTxDetails.isEmpty()) {
                            Double balance = 0D;
                            for (ReconcileDepositsCreditTransactionDetail detail : depositTxDetails) {
                                balance += detail.getAmount() != null ? detail.getAmount() : 0D;
                                detail.setBalance(balance);
                            }
                            depositTxDetails.get(0).setTotalAmount(balance);
                            depositTxDetails.get(0).setTotalBalance(balance);
                        } else {
                            depositTxDetails = new ArrayList<>(); // Ensure non-null list
                        }
                        response.setReconcileDepositsCreditTransactionDetails(depositTxDetails);

                        // Calculate totals
                        Double totalAmount = 0D;
                        if (!checkTxDetails.isEmpty()) {
                            totalAmount += checkTxDetails.get(0).getTotalBalance();
                        }
                        if (!depositTxDetails.isEmpty()) {
                            totalAmount += depositTxDetails.get(0).getTotalAmount();
                        }
                        response.setTotalAmount(totalAmount);
                        response.setTotalBalance(totalAmount); // New Transactions don't have beginning balance

                        newTotalAmount = totalAmount;
//                        newTotalBalance = totalAmount;

                        reconcileReportResponses.add(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // Set final totals if at least one response exists
            if (!reconcileReportResponses.isEmpty()) {
                Double endOfTotalAmount = clearedTotalAmount + UnclearedTotalAmount + newTotalAmount;
                Double endOfTotalBalance = beginningBalance + clearedTotalAmount + UnclearedTotalAmount + newTotalAmount;
                reconcileReportResponses.get(2).setEndOfTotalAmount(endOfTotalAmount);
                reconcileReportResponses.get(2).setEndOfTotalBalance(endOfTotalBalance);
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Reconcile Report/list", null, null, "Reconcile Report", "Reconcile Report (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", reconcileReportResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Reconcile Report/list", 1033L, error.toString(), "Reconcile Report", "Reconcile Report (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public static List<String> getMonthList(String startDate, String endDate) {
        // Define the date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the input dates
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        // Initialize the result list
        List<String> dateList = new ArrayList<>();

        // Loop through months
        LocalDate current = start;
        while (!current.isAfter(end)) {
            dateList.add(current.format(formatter));
            current = current.plusMonths(1); // Move to the next month
        }

        return dateList;
    }

    private Double findProfitLossOfYear(ProfitAndLossReportFilter filter, Long column) {

        Long userId = userService.getUserAuth().getId();

        ProfitAndLossReportFilter filter1 = new ProfitAndLossReportFilter();
        filter1.setUserId(userId);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(filter.getDateTo(), formatter);

        LocalDate dateFrom = null;
        LocalDate dateTo = date; // Given date is always the end date

        switch (column.intValue()) {
            case 0: // Daily
                dateFrom = LocalDate.parse(filter.getDateFrom());
                dateTo = LocalDate.parse(filter.getDateTo());
                break;

            case 1: // Daily
                dateFrom = date;
                dateTo = date;
                break;

            case 2: // Weekly - Start of week (Monday) to End of week (Sunday)
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                dateFrom = date.with(weekFields.dayOfWeek(), 1); // Monday
                dateTo = date.with(weekFields.dayOfWeek(), 7);   // Sunday
                break;

            case 3: // Monthly - First day to Last day of month
                dateFrom = date.with(TemporalAdjusters.firstDayOfMonth());
                dateTo = date.with(TemporalAdjusters.lastDayOfMonth());
                break;

            case 4: // Quarterly - First day to Last day of quarter
                int quarter = (date.getMonthValue() - 1) / 3;
                int firstMonthOfQuarter = (quarter * 3) + 1;
                int lastMonthOfQuarter = (quarter * 3) + 3;
                dateFrom = LocalDate.of(date.getYear(), firstMonthOfQuarter, 1);
                dateTo = LocalDate.of(date.getYear(), lastMonthOfQuarter, 1)
                        .with(TemporalAdjusters.lastDayOfMonth());
                break;

            case 5: // Semester - First day to Last day of semester
                int semester = (date.getMonthValue() - 1) / 6;
                int firstMonthOfSemester = (semester * 6) + 1;
                int lastMonthOfSemester = (semester * 6) + 6;
                dateFrom = LocalDate.of(date.getYear(), firstMonthOfSemester, 1);
                dateTo = LocalDate.of(date.getYear(), lastMonthOfSemester, 1)
                        .with(TemporalAdjusters.lastDayOfMonth());
                break;

            case 6: // Yearly - First day to Last day of year
                dateFrom = date.withDayOfYear(1);
                dateTo = date.withDayOfYear(date.lengthOfYear());
                break;
        }

        filter1.setDateFrom(null);
        filter1.setDateTo(dateTo.format(formatter));
        String tableName = "general_ledger_detail_bl_" + userId;

        filter1.setTableName(tableName);

//        reportMapper.dropTable(tableName);

        reportMapper.createTable(tableName);

        List<ProfitLossReportResponse> profitLossReportResponses = reportMapper.getListProfitLoss(filter1);

        if (profitLossReportResponses != null && !profitLossReportResponses.isEmpty()) {
            for (ProfitLossReportResponse response : profitLossReportResponses) {
                ProfitLossReport profitLossReport = new ProfitLossReport();
                profitLossReport.setDate(response.getDate());
                profitLossReport.setChartAccountId(response.getChartAccountId());
                profitLossReport.setCompanyId(response.getCompanyId());
                profitLossReport.setLocationId(response.getLocationId());
                profitLossReport.setCustomerId(response.getCustomerId());
                profitLossReport.setVendorId(response.getVendorId());
                profitLossReport.setEmployeeId(response.getEmployeeId());
                profitLossReport.setOtherId(response.getOtherId());
                profitLossReport.setClassId(response.getClassId());
                profitLossReport.setDebit(response.getDebit());
                profitLossReport.setCredit(response.getCredit());
                // Insert into dynamically created table
                reportMapper.insertGeneralLedgerDetail(profitLossReport, tableName);
            }
        }

        // Calculate Total Revenue
        List<accountGroupDetail> revenueDetails = reportMapper.getAccountGroupDetailIncome(filter1);
        Double totalRevenue = 0D;
        for (accountGroupDetail detail : revenueDetails) {
            totalRevenue += detail.getTotalAmount();
        }

        // Calculate Cost of Goods Sold
        List<accountGroupDetail> cogsDetails = reportMapper.getAccountGroupDetailCostOfGoodsSold(filter1);
        Double totalCostOfGoodsSold = 0D;
        for (accountGroupDetail detail : cogsDetails) {
            totalCostOfGoodsSold += detail.getTotalAmount();
        }

        // Calculate Gross Profit
        Double totalGrossProfit = totalRevenue - totalCostOfGoodsSold;

        // Calculate Total Expenses
        List<accountGroupDetail> expenseDetails = reportMapper.getAccountGroupDetailGrossProfit(filter1);
        Double totalExpenses = 0D;
        for (accountGroupDetail detail : expenseDetails) {
            totalExpenses += detail.getTotalAmount();
        }

        // Calculate Net Ordinary Income
        Double totalNetOrdinaryIncome = totalGrossProfit - totalExpenses;

        // Calculate Total Other Revenue
        List<accountGroupDetail> otherRevenueDetails = reportMapper.getAccountGroupTotalOtherRevenue(filter1);
        Double totalOtherRevenue = 0D;
        for (accountGroupDetail detail : otherRevenueDetails) {
            totalOtherRevenue += detail.getTotalAmount();
        }

        // Calculate Total Other Expenses
        List<accountGroupDetail> otherExpenseDetails = reportMapper.getAccountGroupTotalOtherExpend(filter1);
        Double totalOtherExpenses = 0D;
        for (accountGroupDetail detail : otherExpenseDetails) {
            totalOtherExpenses += detail.getTotalAmount();
        }

        // Calculate Net Other Income
        Double totalNetOtherIncome = totalOtherRevenue - totalOtherExpenses;

        // Calculate Earnings Before Interest & Tax
        Double totalEarningsBeforeInterestTax = totalNetOrdinaryIncome + totalNetOtherIncome;

        // Calculate Earnings Before Tax (same as EBIT in this case)
        Double totalEarningsBeforeTax = totalEarningsBeforeInterestTax;

        // Calculate Tax Expenses
        List<accountGroupDetail> taxDetails = reportMapper.getAccountGroupTotalTaxExpend(filter1);
        Double totalTaxExpenses = 0D;
        for (accountGroupDetail detail : taxDetails) {
            totalTaxExpenses += detail.getTotalAmount();
        }

        // Calculate Profit/Loss for the Year
        Double totalProfitLoss = totalEarningsBeforeTax - totalTaxExpenses;

        reportMapper.dropTable(tableName);

        return totalProfitLoss;
    }

    public List<String> getDateList(String dateFrom, String dateTo, Long column) {
        if (dateFrom == null || dateTo == null || column == null) {
            return new ArrayList<>();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dateFrom, formatter);
        LocalDate endDate = LocalDate.parse(dateTo, formatter);

        switch (column.intValue()) {
            case 1: // Daily
                return getDailyDates(startDate, endDate, formatter);
            case 2: // Weekly - last day of week
                return getWeeklyDates(startDate, endDate, formatter);
            case 3: // Monthly - last day of month
                return getMonthlyDates(startDate, endDate, formatter);
            case 4: // Quarterly - last day of quarter
                return getQuarterlyDates(startDate, endDate, formatter);
            case 5: // Semester - last day of semester
                return getSemesterDates(startDate, endDate, formatter);
            case 6: // Yearly - last day of year
                return getYearlyDates(startDate, endDate, formatter);
            default:
                return new ArrayList<>();
        }
    }

    // 1. Daily - returns each day
    private List<String> getDailyDates(LocalDate startDate, LocalDate endDate, DateTimeFormatter formatter) {
        List<String> dateList = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dateList.add(currentDate.format(formatter));
            currentDate = currentDate.plusDays(1);
        }

        return dateList;
    }

    // 2. Weekly - returns LAST day of each week (Sunday)
    private List<String> getWeeklyDates(LocalDate startDate, LocalDate endDate, DateTimeFormatter formatter) {
        Set<String> dateSet = new LinkedHashSet<>();
        LocalDate currentDate = startDate;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        while (!currentDate.isAfter(endDate)) {
            // Get the last day of the week (Sunday)
            LocalDate weekEnd = currentDate.with(weekFields.dayOfWeek(), 7);
            // If week end exceeds endDate, use endDate instead
            if (weekEnd.isAfter(endDate)) {
                weekEnd = endDate;
            }
            dateSet.add(weekEnd.format(formatter));
            currentDate = currentDate.plusDays(1);
        }

        return new ArrayList<>(dateSet);
    }

    // 3. Monthly - returns LAST day of each month
    private List<String> getMonthlyDates(LocalDate startDate, LocalDate endDate, DateTimeFormatter formatter) {
        Set<String> dateSet = new LinkedHashSet<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            LocalDate lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());
            // If last day exceeds endDate, use endDate instead
            if (lastDayOfMonth.isAfter(endDate)) {
                lastDayOfMonth = endDate;
            }
            dateSet.add(lastDayOfMonth.format(formatter));
            currentDate = currentDate.plusDays(1);
        }

        return new ArrayList<>(dateSet);
    }

    // 4. Quarterly - returns LAST day of each quarter
    private List<String> getQuarterlyDates(LocalDate startDate, LocalDate endDate, DateTimeFormatter formatter) {
        Set<String> dateSet = new LinkedHashSet<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            int quarter = (currentDate.getMonthValue() - 1) / 3;
            int lastMonthOfQuarter = (quarter * 3) + 3;
            LocalDate quarterEnd = LocalDate.of(currentDate.getYear(), lastMonthOfQuarter, 1)
                    .with(TemporalAdjusters.lastDayOfMonth());
            // If quarter end exceeds endDate, use endDate instead
            if (quarterEnd.isAfter(endDate)) {
                quarterEnd = endDate;
            }
            dateSet.add(quarterEnd.format(formatter));
            currentDate = currentDate.plusDays(1);
        }

        return new ArrayList<>(dateSet);
    }

    // 5. Semester - returns LAST day of each semester (June 30 and December 31)
    private List<String> getSemesterDates(LocalDate startDate, LocalDate endDate, DateTimeFormatter formatter) {
        Set<String> dateSet = new LinkedHashSet<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            int semester = (currentDate.getMonthValue() - 1) / 6;
            int lastMonthOfSemester = (semester * 6) + 6; // 6 = June, 12 = December
            LocalDate semesterEnd = LocalDate.of(currentDate.getYear(), lastMonthOfSemester, 1)
                    .with(TemporalAdjusters.lastDayOfMonth());
            // If semester end exceeds endDate, use endDate instead
            if (semesterEnd.isAfter(endDate)) {
                semesterEnd = endDate;
            }
            dateSet.add(semesterEnd.format(formatter));
            currentDate = currentDate.plusDays(1);
        }

        return new ArrayList<>(dateSet);
    }

    // 6. Yearly - returns LAST day of each year (December 31)
    private List<String> getYearlyDates(LocalDate startDate, LocalDate endDate, DateTimeFormatter formatter) {
        Set<String> dateSet = new LinkedHashSet<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            LocalDate yearEnd = LocalDate.of(currentDate.getYear(), 12, 31);
            // If year end exceeds endDate, use endDate instead
            if (yearEnd.isAfter(endDate)) {
                yearEnd = endDate;
            }
            dateSet.add(yearEnd.format(formatter));
            currentDate = currentDate.plusDays(1);
        }

        return new ArrayList<>(dateSet);
    }

}

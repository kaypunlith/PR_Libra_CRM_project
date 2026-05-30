package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReconcileMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ReconcileFilter;
import com.ut.nlSystemAPi.model.request.Login.Reconcile.ReconcileRequest;
import com.ut.nlSystemAPi.model.response.Reconcile.ReconcileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReconcileServiceImpl implements ReconcileService {

    @Autowired
    private ReconcileMapper reconcileMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    @Override
    public ResponseMessage<BaseResult> getListDebit(ReconcileFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Reconcile") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(reconcileMapper.countList(filter));
            System.out.println(filter);
            List<ReconcileResponse> responses = reconcileMapper.getReconcileDebitList(filter);

            if(responses.size() > 0){
                Double beginningBalance = reconcileMapper.getBeginingBalance(filter);
                if(beginningBalance == null) {
                    beginningBalance = 0D;
                }
                responses.get(0).setBeginningBalance(beginningBalance);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/reconcile/list",null,null,"Reconcile Debit","Reconcile Debit (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/reconcile/list",line, error.toString(),"Reconcile Debit","Reconcile Debit (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCredit(ReconcileFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Reconcile") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(reconcileMapper.countCreditList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ReconcileResponse> responses = reconcileMapper.getReconcileCreditList(filter);

            if(responses.size() > 0){
                Double beginningBalance = reconcileMapper.getBeginingBalance(filter);
                if(beginningBalance == null) {
                    beginningBalance = 0D;
                }
                responses.get(0).setBeginningBalance(beginningBalance);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/reconcile/list",null,null,"Reconcile Credit","Reconcile Credit (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/reconcile/list",line, error.toString(),"Reconcile Credit","Reconcile Credit (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ReconcileRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Reconcile") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Reconcile reconcile = new Reconcile();
            reconcile.setCompanyId(request.getCompanyId());
            reconcile.setBranchId(request.getBranchId());
            reconcile.setDateFrom(request.getDateFrom());
            reconcile.setDateTo(request.getDateTo());
            reconcile.setChartAccountId(request.getChartAccountId());

            reconcile.setCreatedBy(userId);
            reconcile.setIsActive(1);

            //! Insert reconcile
            Boolean isInsertRec = reconcileMapper.insert(reconcile);

            if(isInsertRec){
                //! Update is_reconcile general ledger
                for(int i = 0 ; i < request.getIds().size(); i++){
                    reconcileMapper.updateGeneralLedgerDetailIsReconcile(request.getIds().get(i), reconcile.getId());
                }
            }

            if(request.getServiceChargeAmount() != null && request.getServiceChargeAmount() != 0){
                // Set Date for general ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setDate(request.getServiceChargeDate());
                generalLedger.setReference("Service Charge");
                generalLedger.setIsSys(1);
                generalLedger.setIsAdj(0);

                generalLedger.setCreatedBy(userId);
                generalLedger.setIsActive(1);

                //! Insert general ledger
                Boolean isInsertGl = reconcileMapper.insertGeneralLedger(generalLedger);

                if(isInsertGl){
                    //! Update reconcile Table
                    reconcileMapper.updateReconcileServiceChargeGlId(generalLedger.getId(), reconcile.getId());

                    for(int i = 0; i < 2; i++){
                        // Set Date for general ledger detail
                        GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setCompanyId(request.getCompanyId());
                        generalLedgerDetail.setBranchId(request.getBranchId());
                        generalLedgerDetail.setType("Check");
                        generalLedgerDetail.setMemo("");
                        generalLedgerDetail.setClassId(request.getServiceChargeClassId());
                        generalLedgerDetail.setIsReconcile(1L);
                        generalLedgerDetail.setReconcileId(reconcile.getId());

                        if(i == 0){
                            generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(request.getServiceChargeAmount());
                        } else if(i == 1){
                            generalLedgerDetail.setChartAccountId(request.getServiceChargeAccountId());
                            generalLedgerDetail.setDebit(request.getServiceChargeAmount());
                            generalLedgerDetail.setCredit(0D);
                        }
                        //! Insert General Ledger Detail
                        reconcileMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                    }
                }
            }

            if(request.getInterestedEarnedAmount() != null && request.getInterestedEarnedAmount() != 0){
                // Set Date for general ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setDate(request.getInterestedEarnedDate());
                generalLedger.setReference("Interest Earned");
                generalLedger.setIsSys(1);
                generalLedger.setIsAdj(0);

                generalLedger.setCreatedBy(userId);
                generalLedger.setIsActive(1);

                //! Insert general ledger
                Boolean isInsertGl = reconcileMapper.insertGeneralLedger(generalLedger);

                if(isInsertGl){
                    //! Update reconcile Table
                    reconcileMapper.updateReconcileInterestedEarnedGlId(generalLedger.getId(), reconcile.getId());

                    for(int i = 0; i < 2; i++){
                        // Set Date for general ledger detail
                        GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setCompanyId(request.getCompanyId());
                        generalLedgerDetail.setBranchId(request.getBranchId());
                        generalLedgerDetail.setType("Deposit");
                        generalLedgerDetail.setMemo("");
                        generalLedgerDetail.setClassId(request.getInterestedEarnedClassId());
                        generalLedgerDetail.setIsReconcile(1L);
                        generalLedgerDetail.setReconcileId(reconcile.getId());

                        if(i == 0){
                            generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                            generalLedgerDetail.setDebit(request.getInterestedEarnedAmount());
                            generalLedgerDetail.setCredit(0D);
                        } else if(i == 1){
                            generalLedgerDetail.setChartAccountId(request.getInterestedEarnedAccountId());
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(request.getInterestedEarnedAmount());
                        }
                        //! Insert General Ledger Detail
                        reconcileMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                    }
                }
            }

            if(request.getDiff() != null && request.getDiff() != 0){
                // Set Date for general ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setDate(request.getDateFrom());
                generalLedger.setReference("Balance Adjustment");
                generalLedger.setIsSys(1);
                generalLedger.setIsAdj(0);

                generalLedger.setCreatedBy(userId);
                generalLedger.setIsActive(1);

                //! Insert general ledger
                Boolean isInsertGl = reconcileMapper.insertGeneralLedger(generalLedger);

                if(isInsertGl){
                    //! Update reconcile Table
                    reconcileMapper.updateReconcileDiffGlId(generalLedger.getId(), reconcile.getId());

                    if(request.getDiff() >= 0){
                        for(int i = 0; i < 2; i++){
                            // Set Date for general ledger detail
                            GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setBranchId(request.getBranchId());
                            generalLedgerDetail.setType("Reconcile");
                            generalLedgerDetail.setMemo("");
                            generalLedgerDetail.setClassId(request.getDiffClassId());
                            generalLedgerDetail.setIsReconcile(1L);
                            generalLedgerDetail.setReconcileId(reconcile.getId());

                            if(i == 0){
                                generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                                generalLedgerDetail.setDebit(request.getDiff());
                                generalLedgerDetail.setCredit(0D);
                            } else if(i == 1){
                                generalLedgerDetail.setChartAccountId(request.getDiffAccountId());
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(request.getDiff());
                            }

                            //! Insert General Ledger Detail
                            reconcileMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }
                    } else {
                        for(int i = 0; i < 2; i++){
                            // Set Date for general ledger detail
                            GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setBranchId(request.getBranchId());
                            generalLedgerDetail.setType("Reconcile");
                            generalLedgerDetail.setMemo("");
                            generalLedgerDetail.setClassId(request.getDiffClassId());
                            generalLedgerDetail.setIsReconcile(1L);
                            generalLedgerDetail.setReconcileId(reconcile.getId());

                            if(i == 0){
                                generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(Math.abs(request.getDiff()));
                            } else if(i == 1){
                                generalLedgerDetail.setChartAccountId(request.getDiffAccountId());
                                generalLedgerDetail.setDebit(Math.abs(request.getDiff()));
                                generalLedgerDetail.setCredit(0D);
                            }

                            //! Insert General Ledger Detail
                            reconcileMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }
                    }
                }

            }


            if (isInsertRec) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/reconcile/add",null,null,"Reconcile","Reconcile (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/reconcile/add",line, error.toString(),"Reconcile","Reconcile (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.AccountClosingDateSettingMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.AccountClosingDateSetting;
import com.ut.nlSystemAPi.model.AccountClosingDateSettingDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.filter.ChartAccountNetIncomeFilter;
import com.ut.nlSystemAPi.model.request.Login.AccountClosingDate.AccountClosingDateSettingRequest;
import com.ut.nlSystemAPi.model.response.ChartOfAccount.ChartOfAccountNetIncomeResponse;
import com.ut.nlSystemAPi.model.response.ChartOfAccount.ChartOfAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountClosingDateSettingServiceImpl implements AccountClosingDateSettingService {

    @Autowired
    private AccountClosingDateSettingMapper accountClosingDateSettingMapper;

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

    public ResponseMessage<BaseResult> getListChartOfAccount(ChartAccountFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(accountClosingDateSettingMapper.countList(filter))  ;
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            // Get Data
            List<ChartOfAccountResponse> chartOfAccountResponses = accountClosingDateSettingMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/account-closing-date-setting/list",null,null,"Account Closing Date Setting","Account Closing Date Setting (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartOfAccountResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/account-closing-date-setting/list",line, error.toString(),"Account Closing Date Setting","Account Closing Date Setting (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(AccountClosingDateSettingRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Account Closing Date") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            AccountClosingDateSetting accountClosingDateSetting = new AccountClosingDateSetting();

            accountClosingDateSetting.setDate(request.getDate());
            accountClosingDateSetting.setCompanyId(request.getCompanyId());
            accountClosingDateSetting.setReference(request.getReference());
            accountClosingDateSetting.setIsRetainedEarnings(1L);

            accountClosingDateSetting.setCreatedBy(userId);
            accountClosingDateSetting.setIsActive(1);

            //! Insert General Ledger
            Boolean isInsertGl = accountClosingDateSettingMapper.insertGeneralLedger(accountClosingDateSetting);

            Boolean result = false;
            //! Insert General Ledger Detail
            if(isInsertGl){
                for (int i = 0; i < request.getDetailRequests().size(); i++){
                    System.out.println(request.getDetailRequests());
                    AccountClosingDateSettingDetail accountClosingDateSettingDetail = new AccountClosingDateSettingDetail();
                    accountClosingDateSettingDetail.setGeneralLedgerId(accountClosingDateSetting.getId());
                    accountClosingDateSettingDetail.setCompanyId(accountClosingDateSetting.getCompanyId());
                    accountClosingDateSettingDetail.setBranchId(request.getBranchId());
                    System.out.println(accountClosingDateSettingDetail.getBranchId());
                    accountClosingDateSettingDetail.setChartAccountId(request.getDetailRequests().get(i).getChartAccountId());
                    accountClosingDateSettingDetail.setType("Closing Entries");

                    if (request.getDetailRequests().get(i).getAccountType() == 1){
                        accountClosingDateSettingDetail.setDebit(BigDecimal.valueOf(Math.abs(request.getDetailRequests().get(i).getDebit().doubleValue())));
                        accountClosingDateSettingDetail.setCredit(BigDecimal.valueOf(Math.abs(request.getDetailRequests().get(i).getCredit().doubleValue())));
                    } else {
                        accountClosingDateSettingDetail.setDebit(BigDecimal.valueOf(Math.abs(request.getDetailRequests().get(i).getDebit().doubleValue())));
                        accountClosingDateSettingDetail.setCredit(BigDecimal.valueOf(Math.abs(request.getDetailRequests().get(i).getCredit().doubleValue())));
                    }

                    accountClosingDateSettingDetail.setMemo(request.getDetailRequests().get(i).getMemo());
                    accountClosingDateSettingDetail.setClassId(request.getDetailRequests().get(i).getClassId());

                    System.out.println(accountClosingDateSettingDetail);

                    result = accountClosingDateSettingMapper.insertGeneralLedgerDetail(accountClosingDateSettingDetail);
                }
            }

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/account-closing-date-setting/add",null,null,"Account Closing Date","Account Closing Date (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/account-closing-date-setting/add",line, error.toString(),"Account Closing Date","Account Closing Date (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    public ResponseMessage<BaseResult> getNetIncome(ChartAccountNetIncomeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        System.out.println(filter);
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Double dataIncome = accountClosingDateSettingMapper.getDataIncome(filter);
            Double dataCogs = accountClosingDateSettingMapper.getDataCogs(filter);
            Double dataExpense = accountClosingDateSettingMapper.getDataExpense(filter);

            double income = dataIncome != null ? dataIncome : 0D;
            double cogs = dataCogs != null ? dataCogs : 0D;
            double expense = dataExpense != null ? dataExpense : 0D;
            double netIncome = income - cogs + expense;

            // Get Data
            List<ChartOfAccountNetIncomeResponse> responses = new ArrayList<>();

            responses.add(new ChartOfAccountNetIncomeResponse());

            if(netIncome < 0){
                responses.get(0).setRetainedEarningDebit(Math.abs(netIncome));
                responses.get(0).setRetainedEarningCredit(0D);
                responses.get(0).setIncomeSummaryDebit(0D);
                responses.get(0).setIncomeSummaryCredit(Math.abs(netIncome));
            } else {
                responses.get(0).setRetainedEarningDebit(0D);
                responses.get(0).setRetainedEarningCredit(Math.abs(netIncome));
                responses.get(0).setIncomeSummaryDebit(Math.abs(netIncome));
                responses.get(0).setIncomeSummaryCredit(0D);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/account-closing-date-setting/list",null,null,"Account Closing Date Setting","Account Closing Date Setting (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/account-closing-date-setting/list",line, error.toString(),"Account Closing Date Setting","Account Closing Date Setting (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}

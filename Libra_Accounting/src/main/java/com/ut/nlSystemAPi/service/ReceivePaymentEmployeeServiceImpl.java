package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.JournalEntryMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReceivePaymentEmployeeMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentEmployeeFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentEmployee.ReceivePaymentEmployeeUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentCreditResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentEmployee.ReceivePaymentEmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReceivePaymentEmployeeServiceImpl implements ReceivePaymentEmployeeService {

    @Autowired
    private ReceivePaymentEmployeeMapper receivePaymentEmployeeMapper;

    @Autowired
    private JournalEntryMapper journalEntryMapper;

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

    public ResponseMessage<BaseResult> getList(ReceivePaymentEmployeeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Receive Payments") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Create table
            String tableName = "general_ledger_detail_ar_emp" + userId;

//            receivePaymentEmployeeMapper.dropTable(tableName);

            receivePaymentEmployeeMapper.createTable(tableName);

            //Get Data
            List<ReceivePaymentCreditResponse> creditData = receivePaymentEmployeeMapper.getListGlData(filter);

            //Inert Debit Data
            if(creditData.size() > 0){
                for (ReceivePaymentCreditResponse debitDataResponse : creditData) {
                    ReceivePaymentCreditResponse receivePaymentCreditResponse = debitDataResponse;

                    Boolean isInsertData = receivePaymentEmployeeMapper.insertDebitData(receivePaymentCreditResponse, tableName);
                }
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(receivePaymentEmployeeMapper.countList(filter, tableName));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ReceivePaymentEmployeeResponse> listDataResponse = receivePaymentEmployeeMapper.getList(filter, tableName);

            receivePaymentEmployeeMapper.dropTable(tableName);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment-employee/list",null,null,"Receive Payment Employee","Receive Payment Employee (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment-employee/list",line, error.toString(),"Receive Payment Employee","Receive Payment Employee (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> save(ReceivePaymentEmployeeUpdateRequest updateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Receive Payments") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Set Data
            ReceivePaymentEmployee receivePaymentEmployee = new ReceivePaymentEmployee();
            receivePaymentEmployee.setId(updateRequest.getId());
            receivePaymentEmployee.setCompanyId(updateRequest.getCompanyId());
            receivePaymentEmployee.setBranchId(updateRequest.getBranchId());
            receivePaymentEmployee.setDate(updateRequest.getDate());
            receivePaymentEmployee.setDepositTo(updateRequest.getChartAccountId());
            receivePaymentEmployee.setChartAccountId(updateRequest.getChartAccountId());
            receivePaymentEmployee.setNote(updateRequest.getNote());
            receivePaymentEmployee.setChequeNumber(updateRequest.getChequeNumber());
            receivePaymentEmployee.setExchangeRate(updateRequest.getExchangeRate());
            receivePaymentEmployee.setCreatedBy(userId);
            receivePaymentEmployee.setIsActive(1);

            List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(receivePaymentEmployee.getChartAccountId());

            receivePaymentEmployee.setAccountCode(chartAccount.get(0).getCode());
            receivePaymentEmployee.setAccountDescription(chartAccount.get(0).getName());

            //! Get the reference code
            {
                String receivePaymentCode = receivePaymentEmployeeMapper.getReLastReceivePaymentCode();
                System.out.println("receivePaymentCode Employee: " + receivePaymentCode);
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                receivePaymentEmployee.setReference(incrementedString);
            }


            Boolean insertAr = receivePaymentEmployeeMapper.insertArAging(receivePaymentEmployee);

            Boolean insertGl = false;

            if(insertAr == true) {
                System.out.println(updateRequest.getReceivePaymentEmployeeDetail().size());
                if(updateRequest.getReceivePaymentEmployeeDetail().size() > 0){
                    for(int i = 0; i < updateRequest.getReceivePaymentEmployeeDetail().size(); i++){
                        ReceivePaymentEmployeeDetail receiveDetailPayment = new ReceivePaymentEmployeeDetail();
                        receiveDetailPayment.setGlId(updateRequest.getReceivePaymentEmployeeDetail().get(i).getId());
                        receiveDetailPayment.setArId(receivePaymentEmployee.getArId());
                        receiveDetailPayment.setCustomerId(updateRequest.getReceivePaymentEmployeeDetail().get(i).getCustomerId());
                        receiveDetailPayment.setEmployeeId(updateRequest.getReceivePaymentEmployeeDetail().get(i).getEmployeeId());
                        receiveDetailPayment.setAmountDue(updateRequest.getReceivePaymentEmployeeDetail().get(i).getAmountDue());
                        receiveDetailPayment.setAmountPaid(updateRequest.getReceivePaymentEmployeeDetail().get(i).getAmountPaid());
                        receiveDetailPayment.setBalance(updateRequest.getReceivePaymentEmployeeDetail().get(i).getBalance());
                        receiveDetailPayment.setMemo(updateRequest.getReceivePaymentEmployeeDetail().get(i).getMemo());

                        receivePaymentEmployeeMapper.insertArAgingDetail(receiveDetailPayment);

                        // Insert General Ledger
                        insertGl = receivePaymentEmployeeMapper.insertGeneralLedger(receivePaymentEmployee);

                        for (int j = 0; j < 2; j++){
                            JournalEntryDetail generalLedgerDetail = new JournalEntryDetail();
                            generalLedgerDetail.setGeneralLedgerId(receivePaymentEmployee.getGlId());
                            generalLedgerDetail.setCompanyId(receivePaymentEmployee.getCompanyId());
                            generalLedgerDetail.setBranchId(receivePaymentEmployee.getBranchId());
                            generalLedgerDetail.setCustomerId(receiveDetailPayment.getCustomerId());
                            generalLedgerDetail.setEmployeeId(receiveDetailPayment.getEmployeeId());
                            generalLedgerDetail.setMemo(receiveDetailPayment.getMemo());
                            generalLedgerDetail.setClassId(1L);
                            generalLedgerDetail.setType("Payment");
                            if(j == 0){
                                generalLedgerDetail.setChartAccountId(receivePaymentEmployee.getChartAccountId());
                                generalLedgerDetail.setDebit(receiveDetailPayment.getAmountPaid());
                                generalLedgerDetail.setCredit(0D);
                            } else{
                                generalLedgerDetail.setMainGlId(receiveDetailPayment.getGlId());
                                generalLedgerDetail.setChartAccountId(updateRequest.getReceivePaymentEmployeeDetail().get(i).getChartAccountId());       // Account Receivable
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(receiveDetailPayment.getAmountPaid());
                            }

                            receivePaymentEmployeeMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }

                        // Main GL

                        Long mainGlId = receivePaymentEmployeeMapper.getMainGlId(updateRequest.getReceivePaymentEmployeeDetail().get(i).getId());
                        System.out.println("mainGlId: " + mainGlId);

                        if(mainGlId == null){
                            System.out.println("Testing!" + updateRequest.getReceivePaymentEmployeeDetail().get(i).getId());
                            receivePaymentEmployeeMapper.updateMainGlToGld(updateRequest.getReceivePaymentEmployeeDetail().get(i).getId());
                        } else {
                            receivePaymentEmployeeMapper.updateMainGl(updateRequest.getReceivePaymentEmployeeDetail().get(i).getId(), mainGlId);
                        }
                    }
                }
            }

            if (insertGl) {
                //! Insert Journal Entry File
                if(updateRequest.getFiles().size() > 0) {
                    for(int i = 0; i < updateRequest.getFiles().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(updateRequest.getFiles().get(i), receivePaymentEmployee.getGlId());
                    }
                }
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/receive-payment-employee/save", null, null, "Receive Payments", "Receive Payments Employee (Save)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, receivePaymentEmployee.getArId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment-employee/save", 1033L, error.toString(), "Receive Payments", "Receive Payments Employee (Save)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error occurred during update", false));
        }
    }

    public ResponseMessage<BaseResult> getListPrint(ReceivePaymentPrintFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Receive Payments") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            pagination.setTotal(receivePaymentEmployeeMapper.countListPrint(filter));

            List<ReceivePaymentResponse> listDataResponse = receivePaymentEmployeeMapper.getListPrint(filter);

            System.out.println("listDataResponse: " + listDataResponse);
            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setFiles(receivePaymentEmployeeMapper.getGlFile(listDataResponse.get(i).getGlId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",null,null,"Receive Payment Print Employee","Receive Payment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",line, error.toString(),"Receive Payment Print Employee","Receive Payment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Receive Payments") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ReceivePaymentResponse> listDataResponse = receivePaymentEmployeeMapper.find(id);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setDetailResponses(receivePaymentEmployeeMapper.getGeneralLedgerDetail(listDataResponse.get(i).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",null,null,"Receive Payment Print Employee","Receive Payment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",line, error.toString(),"Receive Payment Print Employee","Receive Payment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public static String incrementNumericPart(String input) {
        // Use a regular expression to split the input into prefix and numeric parts
        String prefix = input.replaceAll("\\d+$", ""); // Extract prefix part
        String numericPart = input.substring(prefix.length()); // Extract numeric part

        // Convert numeric part to an integer
        int number = Integer.parseInt(numericPart);

        // Increment the number
        number += 1;

        // Determine the number of digits in the original numeric part
        int numericPartLength = numericPart.length();

        // Format the incremented number with leading zeros
        String incrementedNumericPart = String.format("%0" + numericPartLength + "d", number);

        // Concatenate the prefix and the incremented numeric part
        return prefix + incrementedNumericPart;
    }

    public static String generateReference(String input) {
        LocalDate currentDate = LocalDate.now();
        String yearLastTwoDigits = String.valueOf(currentDate.getYear()).substring(2);
        String result = yearLastTwoDigits + input + "0000001";
        return result;
    }

}
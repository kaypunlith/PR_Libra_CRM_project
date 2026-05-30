package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.JournalEntryMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReceivePaymentOrganizationMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentOrganizationFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentOrganization.ReceivePaymentOrganizationUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentCreditResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentOrganization.ReceivePaymentOrganizationResponse;
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
public class ReceivePaymentOrganizationServiceImpl implements ReceivePaymentOrganizationService {

    @Autowired
    private ReceivePaymentOrganizationMapper receivePaymentOrganizationMapper;

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

    public ResponseMessage<BaseResult> getList(ReceivePaymentOrganizationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Receive Payments (Journal)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Create table
            String tableName = "general_ledger_detail_ar" + userId;

            System.out.println("general_ledger_detail_ar: " + tableName);

//            receivePaymentOrganizationMapper.dropTable(tableName);

            Boolean createTable = receivePaymentOrganizationMapper.createTable(tableName);

            System.out.println("createTable: " + createTable);
            //Get Data
            List<ReceivePaymentCreditResponse> creditData = receivePaymentOrganizationMapper.getListCredit(filter);

            System.out.println("debitData: " + creditData);

            //Inert Debit Data
            if(creditData.size() > 0){
                for (ReceivePaymentCreditResponse debitDataResponse : creditData) {
                    ReceivePaymentCreditResponse receivePaymentCreditResponse = debitDataResponse;

                    Boolean isInsertData = receivePaymentOrganizationMapper.insertDebitData(receivePaymentCreditResponse, tableName);
                }
            }

            System.out.println("Testing: ");
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            pagination.setTotal(receivePaymentOrganizationMapper.countList(filter, tableName));

            List<ReceivePaymentOrganizationResponse> responses = receivePaymentOrganizationMapper.getList(filter, tableName);


            receivePaymentOrganizationMapper.dropTable(tableName);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment-organization/list",null,null,"Receive Payment Organization","Receive Payment Organization (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment-organization/list",line, error.toString(),"Receive Payment Organization","Receive Payment Organization (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> save(ReceivePaymentOrganizationUpdateRequest updateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Receive Payments (Journal)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Set Data
            ReceivePaymentOrganization receivePaymentOrganization = new ReceivePaymentOrganization();
            receivePaymentOrganization.setId(updateRequest.getId());
            receivePaymentOrganization.setCustomerId(updateRequest.getReceivePaymentOrganizationDetail().get(0).getCustomerId());
            receivePaymentOrganization.setCompanyId(updateRequest.getCompanyId());
            receivePaymentOrganization.setBranchId(updateRequest.getBranchId());
            receivePaymentOrganization.setDate(updateRequest.getDate());
            receivePaymentOrganization.setDepositTo(updateRequest.getChartAccountId());
            receivePaymentOrganization.setChartAccountId(updateRequest.getChartAccountId());
            receivePaymentOrganization.setNote(updateRequest.getNote());
            receivePaymentOrganization.setChequeNumber(updateRequest.getChequeNumber());
            receivePaymentOrganization.setExchangeRate(updateRequest.getExchangeRate());

            receivePaymentOrganization.setCreatedBy(userId);
            receivePaymentOrganization.setIsActive(1);

            List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(receivePaymentOrganization.getChartAccountId());

            receivePaymentOrganization.setAccountCode(chartAccount.get(0).getCode());
            receivePaymentOrganization.setAccountDescription(chartAccount.get(0).getName());

            //! Get the reference code
            {
                String receivePaymentCode = receivePaymentOrganizationMapper.getReLastReceivePaymentCode();
                System.out.println("receivePaymentCode ORG: " + receivePaymentCode);
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                receivePaymentOrganization.setReference(incrementedString);
            }


            Boolean insertAr = receivePaymentOrganizationMapper.insertArAging(receivePaymentOrganization);

            Boolean insertGl = false;
            if(insertAr){
                insertGl = receivePaymentOrganizationMapper.insertGeneralLedger(receivePaymentOrganization);
            }

            if(insertGl == true) {

                if(updateRequest.getReceivePaymentOrganizationDetail().size() > 0){
                    for(int i = 0; i < updateRequest.getReceivePaymentOrganizationDetail().size(); i++){
                        ReceivePaymentOrganizationDetail receiveDetailPayment = new ReceivePaymentOrganizationDetail();

                        receiveDetailPayment.setGlId(updateRequest.getReceivePaymentOrganizationDetail().get(i).getId());
                        receiveDetailPayment.setArId(receivePaymentOrganization.getArId());
                        receiveDetailPayment.setCustomerId(updateRequest.getReceivePaymentOrganizationDetail().get(i).getCustomerId());
                        receiveDetailPayment.setEmployeeId(updateRequest.getReceivePaymentOrganizationDetail().get(i).getEmployeeId());
                        receiveDetailPayment.setAmountDue(updateRequest.getReceivePaymentOrganizationDetail().get(i).getAmountDue());
                        receiveDetailPayment.setAmountPaid(updateRequest.getReceivePaymentOrganizationDetail().get(i).getAmountPaid());
                        receiveDetailPayment.setBalance(updateRequest.getReceivePaymentOrganizationDetail().get(i).getBalance());
                        receiveDetailPayment.setMemo(updateRequest.getReceivePaymentOrganizationDetail().get(i).getMemo());

                        receivePaymentOrganizationMapper.insertArAgingDetail(receiveDetailPayment);

                        for (int j = 0; j < 2; j++){
                            JournalEntryDetail generalLedgerDetail = new JournalEntryDetail();
                            generalLedgerDetail.setGeneralLedgerId(receivePaymentOrganization.getGlId());
                            generalLedgerDetail.setCompanyId(receivePaymentOrganization.getCompanyId());
                            generalLedgerDetail.setBranchId(receivePaymentOrganization.getBranchId());
                            generalLedgerDetail.setCustomerId(receiveDetailPayment.getCustomerId());
                            generalLedgerDetail.setEmployeeId(receiveDetailPayment.getEmployeeId());
                            generalLedgerDetail.setMemo(receiveDetailPayment.getMemo());
                            generalLedgerDetail.setClassId(1L);
                            generalLedgerDetail.setType("Payment");
                            if(j == 0){
                                generalLedgerDetail.setChartAccountId(receivePaymentOrganization.getChartAccountId());
                                generalLedgerDetail.setDebit(receiveDetailPayment.getAmountPaid());
                                generalLedgerDetail.setCredit(0D);
                            } else{
                                generalLedgerDetail.setMainGlId(receiveDetailPayment.getGlId());
                                generalLedgerDetail.setChartAccountId(updateRequest.getReceivePaymentOrganizationDetail().get(i).getChartAccountId());
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(receiveDetailPayment.getAmountPaid());
                            }

                            receivePaymentOrganizationMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }

                        Long mainGlId = receivePaymentOrganizationMapper.getMainGlId(updateRequest.getReceivePaymentOrganizationDetail().get(i).getId());
                        System.out.println("mainGlId: " + mainGlId);

                        // Main GL
                        if(mainGlId == null){
                            System.out.println("Testing!" + updateRequest.getReceivePaymentOrganizationDetail().get(i).getId());
                            receivePaymentOrganizationMapper.updateMainGlToGld(updateRequest.getReceivePaymentOrganizationDetail().get(i).getId());
                        } else {
                            receivePaymentOrganizationMapper.updateMainGl(updateRequest.getReceivePaymentOrganizationDetail().get(i).getId(), mainGlId);
                        }
                    }
                }
            }

            if (insertGl) {

                //! Insert Journal Entry File
                if(updateRequest.getFiles().size() > 0) {
                    for(int i = 0; i < updateRequest.getFiles().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(updateRequest.getFiles().get(i), receivePaymentOrganization.getGlId());
                    }
                }

                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/receive-payment-organization/save", null, null, "Receive Payment Organization", "Receive Payment Organization (save)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, receivePaymentOrganization.getArId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment-organization/save", 1033L, error.toString(), "Receive Payment Organization", "Receive Payment Organization (save)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            pagination.setTotal(receivePaymentOrganizationMapper.countListPrint(filter));

            List<ReceivePaymentResponse> listDataResponse = receivePaymentOrganizationMapper.getListPrint(filter);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setFiles(receivePaymentOrganizationMapper.getGlFile(listDataResponse.get(i).getGlId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",null,null,"Receive Organization Payment Print","Receive Payment Organization (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",line, error.toString(),"Receive Payment Organization Print","Receive Payment Organization (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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

            List<ReceivePaymentResponse> listDataResponse = receivePaymentOrganizationMapper.find(id);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setDetailResponses(receivePaymentOrganizationMapper.getGeneralLedgerDetail(listDataResponse.get(i).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",null,null,"Receive Payment Print","Receive Payment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",line, error.toString(),"Receive Payment Print","Receive Payment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
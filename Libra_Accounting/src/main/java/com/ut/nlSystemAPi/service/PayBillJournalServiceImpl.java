package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.JournalEntryMapper;
import com.ut.nlSystemAPi.mapper.primary.PayBillJournalMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PayBillPrintFilter;
import com.ut.nlSystemAPi.model.filter.PayBillsJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.PayBillJournal.PayBillJournalRequest;
import com.ut.nlSystemAPi.model.response.PayBillJournal.PayBillsJournalResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsPrintResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PayBillJournalServiceImpl implements PayBillJournalService {

    @Autowired
    private PayBillJournalMapper payBillJournalMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private JournalEntryMapper journalEntryMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    public ResponseMessage<BaseResult> getList(PayBillsJournalFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Pay Bills (Journal)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Create table
            String tableName = "general_ledger_detail_ap" + userId;

            System.out.println("general_ledger_detail_ap: " + tableName);

            payBillJournalMapper.createTable(tableName);

            //Get Data
            List<PayBillsDebitDataResponse> debitData = payBillJournalMapper.getDebitData(filter);

            System.out.println("debitData: " + debitData);
            //Inert Debit Data
            if(debitData.size() > 0){
                for (PayBillsDebitDataResponse debitDataResponse : debitData) {
                    PayBillsDebitDataResponse payBillsDebitDataResponse = debitDataResponse;
                    payBillJournalMapper.insertDebitData(payBillsDebitDataResponse, tableName);
                }
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(payBillJournalMapper.countList(filter, tableName));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PayBillsJournalResponse> responses = payBillJournalMapper.getList(filter, tableName);

            payBillJournalMapper.dropTable(tableName);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pay-bill-journal/list",null,null,"Pay Bills (Journal)","Pay Bill Journal (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pay-bill-journal/list",line, error.toString(),"Pay Bills (Journal)","Pay Bills Journal (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> save(PayBillJournalRequest updateRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Pay Bills") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }


            //! Set Data
            PayBills apAging = new PayBills();
            apAging.setCompanyId(updateRequest.getCompanyId());
            apAging.setBranchId(updateRequest.getBranchId());
            apAging.setDate(updateRequest.getDate());
            apAging.setLocationId(updateRequest.getLocationId());
            apAging.setVendorId(updateRequest.getPayBillDetailRequests().get(0).getVendorId());
            apAging.setDepositTo(updateRequest.getChartAccountId());
            apAging.setNote(updateRequest.getNote());
            apAging.setChequeNumber(updateRequest.getChequeNumber());
            apAging.setBankNo(updateRequest.getBankNo());
            apAging.setCreatedBy(userId);
            apAging.setIsActive(1);

            //! Get the reference code
            {
                String receivePaymentCode = payBillJournalMapper.getReLastPayBillCode();
                String incrementedString;

                //If receive Payment not null we have to increase them else generate the new one
                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                } else {
                    incrementedString = generateReference("PV");
                }
                apAging.setReference(incrementedString);
            }


            //! Insert Pay bill
            Boolean insertAr = payBillJournalMapper.insertApAgings(apAging);

            //! Insert general Ledger
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setCompanyId(apAging.getCompanyId());
            journalEntry.setApAgingId(apAging.getId());
            journalEntry.setDate(updateRequest.getDate());
            journalEntry.setReference(apAging.getReference());
            journalEntry.setBranchId(apAging.getBranchId());
            journalEntry.setCreatedBy(userId);
            journalEntry.setIsSys(0L);
            journalEntry.setAdj(0L);
            journalEntry.setIsActive(1);

            Boolean isInsertGl = payBillJournalMapper.insertGeneralLedger(journalEntry);

            if(insertAr && isInsertGl){
                if(updateRequest.getPayBillDetailRequests().size() > 0){
                    for(int i = 0; i < updateRequest.getPayBillDetailRequests().size(); i++){
                        //! Insert Ap Aging Detail
                        PayBillsDetail apAgingDetail = new PayBillsDetail();
                        apAgingDetail.setApAgingId(apAging.getId());
                        apAgingDetail.setGlId(updateRequest.getPayBillDetailRequests().get(i).getId());
                        apAgingDetail.setVendorId(updateRequest.getPayBillDetailRequests().get(i).getVendorId());
                        apAgingDetail.setAmountDue(updateRequest.getPayBillDetailRequests().get(i).getAmountDue());
                        apAgingDetail.setAmountPaid(updateRequest.getPayBillDetailRequests().get(i).getAmountPaid());
                        apAgingDetail.setBalance(updateRequest.getPayBillDetailRequests().get(i).getBalance());
                        apAgingDetail.setMemo(updateRequest.getPayBillDetailRequests().get(i).getMemo());

                        payBillJournalMapper.insertApAgingDetail(apAgingDetail);
                        //! Insert General Ledger Detail

                        for (int j = 0; j < 2; j++){

                            Long classId = payBillJournalMapper.getJournalClassId(apAgingDetail.getGlId());

                            JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                            journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                            journalEntryDetail.setCompanyId(journalEntry.getCompanyId());
                            journalEntryDetail.setBranchId(journalEntry.getBranchId());
                            journalEntryDetail.setType("Pay Bill");
                            journalEntryDetail.setMemo(updateRequest.getPayBillDetailRequests().get(i).getMemo());
                            journalEntryDetail.setVendorId(updateRequest.getPayBillDetailRequests().get(i).getVendorId());
                            journalEntryDetail.setClassId(classId);

                            if(j == 0){
                                journalEntryDetail.setChartAccountId(updateRequest.getChartAccountId());
                                journalEntryDetail.setDebit(0D);
                                journalEntryDetail.setCredit(updateRequest.getPayBillDetailRequests().get(i).getAmountPaid());
                            } else{
                                journalEntryDetail.setMainGlId(updateRequest.getPayBillDetailRequests().get(i).getId());
                                journalEntryDetail.setChartAccountId(updateRequest.getPayBillDetailRequests().get(i).getChartAccountId());
                                journalEntryDetail.setDebit(updateRequest.getPayBillDetailRequests().get(i).getAmountPaid());
                                journalEntryDetail.setCredit(0D);
                            }

                            payBillJournalMapper.insertGeneralLedgerDetail(journalEntryDetail);
                        }

                        Long mainGlId = payBillJournalMapper.getMainGlId(updateRequest.getPayBillDetailRequests().get(i).getId());
                        System.out.println("mainGlId: " + mainGlId);

                        // Main GL
                        if(mainGlId == null){
                            System.out.println("Testing!" + updateRequest.getPayBillDetailRequests().get(i).getId());
                            payBillJournalMapper.updateMainGlToGld(updateRequest.getPayBillDetailRequests().get(i).getId());
                        } else {
                            payBillJournalMapper.updateMainGl(updateRequest.getPayBillDetailRequests().get(i).getId(), mainGlId);
                        }
                    }
                }
            }

            if (insertAr) {
                //! Insert Journal Entry File
                if(updateRequest.getPayBillFileRequests()!= null && !updateRequest.getPayBillFileRequests().isEmpty()) {
                    for(int i = 0; i < updateRequest.getPayBillFileRequests().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(updateRequest.getPayBillFileRequests().get(i), journalEntry.getId());
                    }
                }
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/pay-bill-journal/save", null, null, "Pay Bills Journal", "Pay Bills Journal(save)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, apAging.getId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pay-bill-journal/save", 1033L, error.toString(), "Pay Bills Journal", "Pay Bills Journal (save)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error occurred during update", false));
        }
    }


    public ResponseMessage<BaseResult> getListPrint(PayBillPrintFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
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

            pagination.setTotal(payBillJournalMapper.countListPrint(filter));

            List<PayBillsPrintResponse> listDataResponse = payBillJournalMapper.getListPrint(filter);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setFiles(payBillJournalMapper.getGlFile(listDataResponse.get(i).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",null,null,"Paybill Journal Print Employee","Paybill Journal (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",line, error.toString(),"Paybill Journal Print Print Employee","Paybill Journal (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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

            List<PayBillsPrintResponse> listDataResponse = payBillJournalMapper.find(id);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setDetailResponses(payBillJournalMapper.gePayBillNormalDetail(listDataResponse.get(i).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",null,null,"Paybill Journal Print Employee","Paybill Journal (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",line, error.toString(),"Paybill Journal Print Employee","Paybill Journal (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
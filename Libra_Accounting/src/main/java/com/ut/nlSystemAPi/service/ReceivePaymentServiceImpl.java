package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.JournalEntryMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReceivePaymentMapper;
import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ReceiveDetailPayment;
import com.ut.nlSystemAPi.model.ReceivePayment;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentFilter;
import com.ut.nlSystemAPi.model.filter.ReceivePaymentPrintFilter;
import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePayment.ReceivePaymentUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentCreditStatementResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentResponse;
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
public class ReceivePaymentServiceImpl implements ReceivePaymentService {

    @Autowired
    private ReceivePaymentMapper receivePaymentMapper;

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

    public ResponseMessage<BaseResult> getList(ReceivePaymentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
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
            filter.setUserId(userId);

            pagination.setTotal(receivePaymentMapper.countList(filter));

            List<ReceivePaymentResponse> listDataResponse = receivePaymentMapper.getList(filter);

            if(listDataResponse.size()>0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    if(listDataResponse.get(i).getPaymentTermId() != null && listDataResponse.get(i).getPaymentTermId() == 7){
                        if (listDataResponse.get(i).getDate() != null){
                            String expiredDate = listDataResponse.get(i).getDate();
                            listDataResponse.get(i).setNextExpired(expiredDate);
                        }
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list",null,null,"Receive Payment","Receive Payment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list",line, error.toString(),"Receive Payment","Receive Payment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    //   not yet impl
    @Override
    public ResponseMessage<BaseResult> save(ReceivePaymentUpdateRequest updateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Receive Payments") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Set Data
            ReceivePayment receivePayment = new ReceivePayment();
            receivePayment.setId(updateRequest.getId());
            receivePayment.setCompanyId(updateRequest.getCompanyId());
            receivePayment.setBranchId(updateRequest.getBranchId());
            receivePayment.setDate(updateRequest.getDate());
            receivePayment.setDepositTo(updateRequest.getChartAccountId());
            receivePayment.setChartAccountId(updateRequest.getChartAccountId());
            receivePayment.setNote(updateRequest.getNote());
            receivePayment.setChequeNumber(updateRequest.getChequeNumber());
            receivePayment.setExchangeRate(updateRequest.getExchangeRate());
            receivePayment.setCustomerId(updateRequest.getReceivePaymentDetail().get(0).getCustomerId());
            receivePayment.setCustomerContactId(updateRequest.getReceivePaymentDetail().get(0).getCustomerContactId());
            receivePayment.setCreatedBy(userId);
            receivePayment.setIsActive(1);

            List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(receivePayment.getChartAccountId());
            receivePayment.setAccountCode(chartAccount.get(0).getCode());
            receivePayment.setAccountDescription(chartAccount.get(0).getName());

            //! Get the reference code
            {
                System.out.println("Testing 01");
                String receivePaymentCode = receivePaymentMapper.getReLastReceivePaymentCode();
                System.out.println("receivePaymentCode: " + receivePaymentCode);
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                receivePayment.setReference(incrementedString);
                System.out.println("incrementedString: " + incrementedString);
            }

            if(updateRequest.getReceivePaymentDetail().size() > 0){
                Double totalAmount = 0.0;
                for (int i = 0; i < updateRequest.getReceivePaymentDetail().size(); i++) {
                    totalAmount += updateRequest.getReceivePaymentDetail().get(i).getAmountPaid();
                }
                receivePayment.setTotalAmount(totalAmount);
            }

            Boolean insertAr = receivePaymentMapper.insertReceivedPayment(receivePayment);
            Boolean insertGl = false;

            System.out.println(receivePayment);

            if(insertAr == true) {
                if(updateRequest.getReceivePaymentDetail().size() > 0){
                    for(int i = 0; i < updateRequest.getReceivePaymentDetail().size(); i++){

                        //Update Sales Order Balance
                        receivePaymentMapper.updateSalesOrderBalance(updateRequest.getReceivePaymentDetail().get(i), userId);

                        ReceiveDetailPayment receiveDetailPayment = new ReceiveDetailPayment();

                        receiveDetailPayment.setSaleOrderId(updateRequest.getReceivePaymentDetail().get(i).getId());
                        receiveDetailPayment.setReceivePaymentId(receivePayment.getReceivePaymentId());
                        receiveDetailPayment.setCustomerId(updateRequest.getReceivePaymentDetail().get(i).getCustomerId());
                        receiveDetailPayment.setEmployeeId(updateRequest.getReceivePaymentDetail().get(i).getEmployeeId());
                        receiveDetailPayment.setAmountDue(updateRequest.getReceivePaymentDetail().get(i).getAmountDue());
                        receiveDetailPayment.setAmountPaid(updateRequest.getReceivePaymentDetail().get(i).getAmountPaid());
                        receiveDetailPayment.setBalance(updateRequest.getReceivePaymentDetail().get(i).getBalance());
                        receiveDetailPayment.setMemo(updateRequest.getReceivePaymentDetail().get(i).getMemo());

                        receivePaymentMapper.insertReceivedPaymentDetail(receiveDetailPayment);

                        // Insert SalesOrderReceipt
                        receivePayment.setDate(updateRequest.getDate());
                        receivePayment.setTotalAmount(updateRequest.getReceivePaymentDetail().get(i).getAmountDue());
                        receivePayment.setAmountPaid(updateRequest.getReceivePaymentDetail().get(i).getAmountPaid());
                        receivePayment.setBalance(updateRequest.getReceivePaymentDetail().get(i).getBalance());
                        receivePayment.setSaleOrderId(updateRequest.getReceivePaymentDetail().get(i).getId());

                        //! Get the reference code
                        {
                            String receivePaymentCode = receivePaymentMapper.getReLastSalesOrderReceiptCode();
                            String incrementedString;

                            if(receivePaymentCode != null){
                                incrementedString = incrementNumericPart(receivePaymentCode);
                            }else{
                                incrementedString = generateReference("INR");
                            }
                            receivePayment.setInvoicerReference(incrementedString);
                        }


                        receivePaymentMapper.insertSaleReceipt(receivePayment);

                        // Insert Gl
                        receivePayment.setReceivePaymentId(receivePayment.getReceivePaymentId());
                        insertGl = receivePaymentMapper.insertGeneralLedger(receivePayment);


                        for (int j = 0; j < 2; j++){
                            JournalEntryDetail generalLedgerDetail = new JournalEntryDetail();
                            generalLedgerDetail.setGeneralLedgerId(receivePayment.getGlId());
                            generalLedgerDetail.setCompanyId(receivePayment.getCompanyId());
                            generalLedgerDetail.setBranchId(receivePayment.getBranchId());
                            generalLedgerDetail.setCustomerId(receiveDetailPayment.getCustomerId());
                            generalLedgerDetail.setEmployeeId(receiveDetailPayment.getEmployeeId());
                            generalLedgerDetail.setMemo(receiveDetailPayment.getMemo());
                            generalLedgerDetail.setClassId(1L);
                            generalLedgerDetail.setType("Payment");

                            if(j == 0){
                                generalLedgerDetail.setChartAccountId(receivePayment.getChartAccountId());
                                generalLedgerDetail.setDebit(receiveDetailPayment.getAmountPaid());
                                generalLedgerDetail.setCredit(0D);
                            } else{
                                Long chartAccountReceived = receivePaymentMapper.getChartAccountReceived(7L);
                                System.out.println("chartAccountReceived: " + chartAccountReceived);
                                generalLedgerDetail.setChartAccountId(chartAccountReceived);
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(receiveDetailPayment.getAmountPaid());
                            }
                            receivePaymentMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }
                    }
                }
            }

            if (insertGl) {
                //! Insert Journal Entry File
                if(updateRequest.getFiles().size() > 0) {
                    for(int i = 0; i < updateRequest.getFiles().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(updateRequest.getFiles().get(i), receivePayment.getGlId());
                    }
                }
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/receive-payment/save", null, null, "Receive Payments", "Receive Payments", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, receivePayment.getReceivePaymentId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/save", 1033L, error.toString(), "Receive Payments", "Receive Payments", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            pagination.setTotal(receivePaymentMapper.countListPrint(filter));

            List<ReceivePaymentResponse> listDataResponse = receivePaymentMapper.getListPrint(filter);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){

                    listDataResponse.get(i).setFiles(receivePaymentMapper.getGlFile(listDataResponse.get(i).getGlId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",null,null,"Receive Payment Print","Receive Payment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listDataResponse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list-print",line, error.toString(),"Receive Payment Print","Receive Payment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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

            List<ReceivePaymentResponse> listDataResponse = receivePaymentMapper.find(id);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setDetailResponses(receivePaymentMapper.getReceivePaymentDetail(listDataResponse.get(i).getId()));

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


    public ResponseMessage<BaseResult> getCreditStatement(ReceivePaymentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
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
            filter.setUserId(userId);

            pagination.setTotal(receivePaymentMapper.countList(filter));

            List<ReceivePaymentCreditStatementResponse> responses = receivePaymentMapper.getCreditStatement(filter);

            List<ReceivePaymentResponse> listDataResponse = receivePaymentMapper.getList(filter);

            if(listDataResponse.size()>0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    if(listDataResponse.get(i).getPaymentTermId() != null && listDataResponse.get(i).getPaymentTermId() == 7){
                        if (listDataResponse.get(i).getDate() != null){
                            String expiredDate = listDataResponse.get(i).getDate();
                            listDataResponse.get(i).setNextExpired(expiredDate);
                        }
                    }
                }
            }

            responses.get(0).setReceivePaymentResponsesList(listDataResponse);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list",null,null,"Receive Payment","Receive Payment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/receive-payment/list",line, error.toString(),"Receive Payment","Receive Payment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
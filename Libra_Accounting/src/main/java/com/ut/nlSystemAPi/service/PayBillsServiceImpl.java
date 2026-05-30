package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.JournalEntryMapper;
import com.ut.nlSystemAPi.mapper.primary.PayBillsMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PayBillPrintFilter;
import com.ut.nlSystemAPi.model.filter.PayBillsFilter;
import com.ut.nlSystemAPi.model.request.Login.PayBills.PayBillRequest;
import com.ut.nlSystemAPi.model.response.DashboradAccounting.ProfileAndLostDashboardResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsDebitDataResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsPrintResponse;
import com.ut.nlSystemAPi.model.response.PayBills.PayBillsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PayBillsServiceImpl implements PayBillsService {

    @Autowired
    private PayBillsMapper payBillsMapper;

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

    public ResponseMessage<BaseResult> getList(PayBillsFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Pay Bills") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            //! Create table
            String tableName = "general_ledger_detail_ap" + userId;

            System.out.println("general_ledger_detail_ap: " + tableName);

//            payBillsMapper.dropTable(tableName);

            payBillsMapper.createTable(tableName);

            //Get Data
            List<PayBillsDebitDataResponse> debitData = payBillsMapper.getDebitData(filter);

            System.out.println("debitData: " + debitData);
            //Inert Debit Data
            if(debitData.size() > 0){
                for (PayBillsDebitDataResponse debitDataResponse : debitData) {
                    PayBillsDebitDataResponse payBillsDebitDataResponse = debitDataResponse;
                    Boolean isInsertData = payBillsMapper.insertDebitData(payBillsDebitDataResponse, tableName);
                }
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PayBillsResponse> responses = payBillsMapper.getList(filter, tableName);

            pagination.setTotal((long) responses.size());

            payBillsMapper.dropTable(tableName);

//            AND v.id IN (
//                    SELECT pr.vendor_id
//            FROM purchase_orders pr
//            INNER JOIN locations loc ON pr.location_id = loc.id
//            INNER JOIN vendors v ON pr.vendor_id = v.id
//            WHERE pr.status > 0
//            AND pr.balance > 0
//                    <if test="filter.companyId != null">
//                    AND pr.company_id = #{filter.companyId}
//                </if>
//                <if test="filter.vendorId != null">
//                    AND pr.vendor_id = #{filter.vendorId}
//                </if>
//                <if test="filter.locationId != null">
//                    AND pr.location_id = #{filter.locationId}
//                </if>
//        )

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pay-bill/list",null,null,"Pay Bills","Pay Bills (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pay-bill/list",line, error.toString(),"Pay Bills","Pay Bills (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> save(PayBillRequest updateRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

//            if (permissionMapper.checkPermission(userId, "Pay Bills") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            //! Set Data
            PayBills payBills = new PayBills();
            payBills.setCompanyId(updateRequest.getCompanyId());
            payBills.setLocationId(updateRequest.getLocationId());
            payBills.setVendorId(updateRequest.getVendorId());
            payBills.setBranchId(updateRequest.getBranchId());
            payBills.setDate(updateRequest.getDate());
            payBills.setDepositTo(updateRequest.getChartAccountId());
            payBills.setChartAccountId(updateRequest.getChartAccountId());
            payBills.setNote(updateRequest.getNote());
            payBills.setChequeNumber(updateRequest.getChequeNumber());
            payBills.setBankNo(updateRequest.getBankNo());
            payBills.setExchangeRate(updateRequest.getExchangeRate());
            payBills.setCreatedBy(userId);
            payBills.setIsActive(1);

            List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(payBills.getChartAccountId());

            payBills.setAccountCode(chartAccount.get(0).getCode());
            payBills.setAccountDescription(chartAccount.get(0).getName());

            //! Get the reference code
            {
                String receivePaymentCode = payBillsMapper.getReLastPayBillCode();
                String incrementedString;

                //If receive Payment not null we have to increase them else generate the new one
                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("PV");
                }
                payBills.setReference(incrementedString);
            }

            //! Insert Pay bill
            Boolean insertAr = payBillsMapper.insertPayBill(payBills);

            JournalEntry journalEntry = new JournalEntry();
            Boolean insertGl = true;
            if(insertAr){
                if(updateRequest.getPayBillDetailRequests().size() > 0){
                    for(int i = 0; i < updateRequest.getPayBillDetailRequests().size(); i++){
                        if(updateRequest.getPayBillDetailRequests().get(i).getType().equals("Bill")){
                            // Update PurchaseOrder
                            payBillsMapper.updatePurchaseOrder(updateRequest.getPayBillDetailRequests().get(i).getId(), updateRequest.getPayBillDetailRequests().get(i).getBalance(), userId);

                            // Update ER Deposit Amount
                            payBillsMapper.updateERDepositAmount(updateRequest.getPayBillDetailRequests().get(i).getId());

                            // Save to pay bill detail
                            PayBillsDetail payBillsDetail = new PayBillsDetail();
                            payBillsDetail.setPayBillId(payBills.getId());
                            payBillsDetail.setPurchaseOrderId(updateRequest.getPayBillDetailRequests().get(i).getId());
                            payBillsDetail.setAmountDue(updateRequest.getPayBillDetailRequests().get(i).getAmountDue());
                            payBillsDetail.setAmountPaid(updateRequest.getPayBillDetailRequests().get(i).getAmountPaid());
                            payBillsDetail.setBalance(updateRequest.getPayBillDetailRequests().get(i).getBalance());
                            payBillsDetail.setDueDate(updateRequest.getPayBillDetailRequests().get(i).getDate());
                            payBillsDetail.setMemo(updateRequest.getPayBillDetailRequests().get(i).getMemo());


                            Boolean result = payBillsMapper.insertPayBillDetail(payBillsDetail);

                            // Save to PV
                            payBillsDetail.setPvCode(payBills.getReference());
                            payBillsDetail.setPayDate(payBills.getDate());
                            payBillsDetail.setChartAccountId(payBills.getChartAccountId());
                            payBillsDetail.setCreatedBy(userId);

                            result = payBillsMapper.insertPv(payBillsDetail);

                            if(result){

                                //! Insert to general ledger
                                journalEntry.setPurchaseOrderId(payBillsDetail.getPurchaseOrderId());
                                journalEntry.setPvId(payBillsDetail.getPvId());
                                journalEntry.setPayBillId(payBills.getId());
                                journalEntry.setDate(payBills.getDate());
                                journalEntry.setReference(payBills.getReference());
                                journalEntry.setCreatedBy(userId);
                                journalEntry.setIsSys(1L);
                                journalEntry.setAdj(0L);
                                journalEntry.setIsActive(1);

                                Boolean isInsertGeneralLedger = payBillsMapper.insertGeneralLedger(journalEntry);

                                //! Insert General Ledger Detail
                                if(isInsertGeneralLedger){
                                    System.out.println("Testing SSDSD" + payBills.getCompanyId() + " - " + updateRequest.getPayBillDetailRequests().get(i).getLocationId());

                                    // get class id base on location group
                                    Long classId = payBillsMapper.getLocationGroupClassId(payBills.getCompanyId(), updateRequest.getPayBillDetailRequests().get(i).getLocationId());

                                    System.out.println("Testing 322:" + classId);
                                    JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                                    journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                                    journalEntryDetail.setBranchId(payBills.getBranchId());
                                    journalEntryDetail.setChartAccountId(payBills.getChartAccountId());
                                    journalEntryDetail.setCompanyId(payBills.getCompanyId());
                                    journalEntryDetail.setType("Pay Bill");
                                    journalEntryDetail.setDebit(0D);
                                    journalEntryDetail.setCredit(payBillsDetail.getAmountPaid());
                                    journalEntryDetail.setVendorId(payBillsDetail.getVendorId());
                                    journalEntryDetail.setMemo("ICS: Pay Bill for PO #");
                                    journalEntryDetail.setClassId(classId);

                                    payBillsMapper.insertGeneralLedgerDetail(journalEntryDetail);

                                    // Insert detailed 2
                                    Long chartAccountReceived = payBillsMapper.getChartAccountReceived(14L);
                                    journalEntryDetail.setChartAccountId(chartAccountReceived);
                                    journalEntryDetail.setDebit(payBillsDetail.getAmountPaid());
                                    journalEntryDetail.setCredit(0D);
                                    journalEntryDetail.setMemo("ICS: Pay Bill for PO #");

                                    payBillsMapper.insertGeneralLedgerDetail(journalEntryDetail);
                                }

                            }
                        } else {
                            System.out.println("Journal!");
                            PayBillsDetail payBillsDetail = new PayBillsDetail();
                            payBillsDetail.setPayBillId(payBills.getId());
                            payBillsDetail.setGlDetailId(updateRequest.getPayBillDetailRequests().get(i).getId());
                            payBillsDetail.setAmountDue(updateRequest.getPayBillDetailRequests().get(i).getAmountDue());
                            payBillsDetail.setAmountPaid(updateRequest.getPayBillDetailRequests().get(i).getAmountPaid());
                            payBillsDetail.setMemo(updateRequest.getPayBillDetailRequests().get(i).getMemo());
                            payBillsDetail.setBalance(updateRequest.getPayBillDetailRequests().get(i).getBalance());
                            payBillsDetail.setDueDate(updateRequest.getPayBillDetailRequests().get(i).getDate());
                            payBillsDetail.setVendorId(updateRequest.getPayBillDetailRequests().get(i).getVendorId());
                            payBillsDetail.setBranchId(payBills.getBranchId());
                            payBillsDetail.setUserId(userId);
                            payBillsDetail.setChartAccountId(payBills.getChartAccountId());
                            Boolean result = payBillsMapper.insertPayBillDetail(payBillsDetail);
                            
                            //! Insert Landed Cost and Landed Cost Detail
                            payBillsMapper.insertLandedCost(payBillsDetail);

                            payBillsMapper.insertLandedCostReceipt(payBillsDetail);

                            System.out.println("Testing 07");
                            System.out.println(result);

                            //! Insert to general ledger and general ledger detail
                            if(result){
                                journalEntry.setPayBillId(payBills.getId());
                                journalEntry.setApAgingId(payBills.getId());
                                journalEntry.setDate(payBills.getDate());
                                journalEntry.setReference(payBills.getReference());
                                journalEntry.setCreatedBy(userId);
                                journalEntry.setIsSys(0L);
                                journalEntry.setAdj(0L);
                                journalEntry.setIsActive(1);
                                journalEntry.setPurchaseOrderId(null);

                                Boolean isInsertGeneralLedger = payBillsMapper.insertGeneralLedger(journalEntry);

                                //! Insert General Ledger Detail
                                if(isInsertGeneralLedger){

                                    System.out.println("Testing");
                                    Long classId = payBillsMapper.getJournalClassId(updateRequest.getPayBillDetailRequests().get(i).getId());

                                    System.out.println("classId: " + classId);
                                    JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                                    journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                                    journalEntryDetail.setBranchId(payBills.getBranchId());
                                    journalEntryDetail.setChartAccountId(payBills.getChartAccountId());
                                    journalEntryDetail.setCompanyId(payBills.getCompanyId());
                                    journalEntryDetail.setType("Pay Bill");
                                    journalEntryDetail.setDebit(0D);
                                    journalEntryDetail.setCredit(payBillsDetail.getAmountPaid());
                                    journalEntryDetail.setVendorId(payBillsDetail.getVendorId());
                                    journalEntryDetail.setMemo(payBillsDetail.getMemo());
                                    journalEntryDetail.setClassId(classId);

                                    payBillsMapper.insertGeneralLedgerDetail(journalEntryDetail);

                                    //! Main GL
                                    Long mainGlId = payBillsMapper.getGlId(updateRequest.getPayBillDetailRequests().get(i).getId());

                                    if(mainGlId != null){
                                        payBillsMapper.updateMainGlByDedailtId(mainGlId, updateRequest.getPayBillDetailRequests().get(i).getId());
                                    } else {
                                        payBillsMapper.updateMainGl(updateRequest.getPayBillDetailRequests().get(i).getId());
                                    }

                                    // Insert detailed 2
                                    journalEntryDetail.setChartAccountId(updateRequest.getPayBillDetailRequests().get(i).getChartAccountId());
                                    journalEntryDetail.setMainGlId(updateRequest.getPayBillDetailRequests().get(i).getId());
                                    journalEntryDetail.setDebit(payBillsDetail.getAmountPaid());
                                    journalEntryDetail.setCredit(0D);

                                    payBillsMapper.insertGeneralLedgerDetail(journalEntryDetail);
                                }
                            }
                        }
                    }
                }
            }

            //! Insert Journal Entry File
            if(updateRequest.getPayBillFileRequests()!= null && !updateRequest.getPayBillFileRequests().isEmpty()) {
                for(int i = 0; i < updateRequest.getPayBillFileRequests().size(); i++){
                    journalEntryMapper.insertJournalEntryFile(updateRequest.getPayBillFileRequests().get(i), journalEntry.getId());
                }
            }
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pay-bill/save", null, null, "Pay Bills", "Pay Bills (save)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, payBills.getId()));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pay-bill/save", 1033L, error.toString(), "Pay Bills", "Pay Bills (save)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            pagination.setTotal(payBillsMapper.countListPrint(filter));

            List<PayBillsPrintResponse> listDataResponse = payBillsMapper.getListPrint(filter);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    listDataResponse.get(i).setFiles(payBillsMapper.getGlFile(listDataResponse.get(i).getId()));
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

            List<PayBillsPrintResponse> listDataResponse = payBillsMapper.find(id);

            if(listDataResponse.size() > 0){
                for(int i = 0; i < listDataResponse.size(); i++){
                    if(listDataResponse.get(i).getType() == 1) {
                        //! check
                        listDataResponse.get(i).setDetailResponses(payBillsMapper.getPayBillCheckDetail(listDataResponse.get(i).getGlId()));
                    } else {
                        System.out.println("Normal");
                        //! Get Normal
                        listDataResponse.get(i).setDetailResponses(payBillsMapper.gePayBillNormalDetail(listDataResponse.get(i).getId()));
                    }
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
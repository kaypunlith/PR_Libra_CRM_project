package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.JournalEntrySupervisorMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.JournalEntry;
import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryStatusRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.JournalEntryDetailResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.JournalEntryResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.ReceivePaymentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JournalEntrySupervisorServiceImpl implements JournalEntrySupervisorService {

    @Autowired
    private JournalEntrySupervisorMapper journalEntrySupervisorMapper;

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

    public ResponseMessage<BaseResult> getList(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (supervisor level) (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(journalEntrySupervisorMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<JournalEntryResponse> journalEntryResponse = journalEntrySupervisorMapper.getList(filter);

            List<JournalEntryDetailResponse> responses = new ArrayList<>();
            if(journalEntryResponse.size() > 0){
                for(int i = 0; i < journalEntryResponse.size(); i++){

                    Long journalEntryId = journalEntryResponse.get(i).getId();

                    //! Find OR or PV Voucher
                    if(journalEntryResponse.get(i).getReference() != null){
                        String extracted = journalEntryResponse.get(i).getReference().substring(2, 4);
                        if(extracted.equals("OR")){
                            journalEntryResponse.get(i).setIsOrVoucher(1L);
                        } else if (extracted.equals("PV")) {
                            journalEntryResponse.get(i).setIsPvVoucher(1L);
                        }
                    } else {
                        journalEntryResponse.get(i).setIsOrVoucher(0L);
                        journalEntryResponse.get(i).setIsPvVoucher(0L);
                    }

                    List<JournalEntryDetailResponse> journalEntryDetail = journalEntrySupervisorMapper.getJournalEntryDetail(journalEntryId);

                    journalEntryDetail.get(0).setNo((long) (i + 1));
                    journalEntryDetail.get(0).setDate(journalEntryResponse.get(i).getDate());
                    journalEntryDetail.get(0).setReference(journalEntryResponse.get(i).getReference());
                    journalEntryDetail.get(0).setAdj(journalEntryResponse.get(i).getAdj());
                    journalEntryDetail.get(0).setCreatedBy(journalEntryResponse.get(i).getCreatedBy());
                    journalEntryDetail.get(0).setStatus(journalEntryResponse.get(i).getStatus());
                    journalEntryDetail.get(0).setIsRecurrence(journalEntryResponse.get(i).getIsRecurrence());
                    journalEntryDetail.get(0).setNote(journalEntryResponse.get(i).getNote());
                    journalEntryDetail.get(0).setBranchName(journalEntryResponse.get(i).getBranchName());
                    journalEntryDetail.get(0).setBranchId(journalEntryResponse.get(i).getBranchId());
                    journalEntryDetail.get(0).setIsOrVoucher(journalEntryResponse.get(i).getIsOrVoucher());
                    journalEntryDetail.get(0).setIsPvVoucher(journalEntryResponse.get(i).getIsPvVoucher());
                    journalEntryDetail.get(0).setFile(journalEntrySupervisorMapper.getJournalEntryFile(journalEntryId));

                    // Add to list
                    responses.addAll(journalEntryDetail);

                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/list",null,null,"Journal Entry Supervisor","Journal Entry Supervisor (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/list",line, error.toString(),"Journal Entry Supervisor","Journal Entry Supervisor (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (supervisor level) (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<JournalEntryResponse> responses = journalEntrySupervisorMapper.getOne(id);

            //! Find OR or PV Voucher
            if(responses.size() > 0){
                String extracted = responses.get(0).getReference().substring(2, 4);
                if(extracted.equals("OR")){
                    List<ReceivePaymentsResponse> receivePaymentResponses = journalEntrySupervisorMapper.getReLastReceivePaymentByJournalId(id);
                    if(receivePaymentResponses.size() > 0){
                        responses.get(0).setExchangeRate(receivePaymentResponses.get(0).getExchangeRate());
                        responses.get(0).setChequeNo(receivePaymentResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsOrVoucher(1L);
                } else if (extracted.equals("PV")) {
                    List<ReceivePaymentsResponse> payBillResponses = journalEntrySupervisorMapper.getReLastPayBillByJournalId(id);
                    if(payBillResponses.size() > 0){
                        responses.get(0).setExchangeRate(payBillResponses.get(0).getExchangeRate());
                        responses.get(0).setChequeNo(payBillResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsPvVoucher(1L);
                }
            }

            if(responses.size() > 0){
                Long journalEntryId = responses.get(0).getId();
                List<JournalEntryDetailResponse> journalEntryDetail = journalEntrySupervisorMapper.getJournalEntryDetail(journalEntryId);

                responses.get(0).setJournalEntryDetail(journalEntryDetail);
                responses.get(0).setCompanyId(journalEntryDetail.get(0).getCompanyId());
                responses.get(0).setCompanyName(journalEntryDetail.get(0).getCompanyName());
                responses.get(0).setFile(journalEntrySupervisorMapper.getJournalEntryFile(journalEntryId));

                //! Find Paid To Name
                if(journalEntryDetail.size() > 0){
                    String paidToName = "";

                    for (int i = 0; i < journalEntryDetail.size(); i++) {
                        if(journalEntryDetail.get(i).getCredit() > 0){
                            if(journalEntryDetail.get(i).getCustomerName() != null){
                                paidToName = paidToName + journalEntryDetail.get(i).getCustomerName() + ", ";
                            } else if(journalEntryDetail.get(i).getEmployeeName() != null){
                                paidToName = paidToName + journalEntryDetail.get(i).getEmployeeName() + ", ";
                            } else if (journalEntryDetail.get(i).getVendorName() != null){
                                paidToName = paidToName + journalEntryDetail.get(i).getVendorName() + ", ";
                            }
                        }
                    }
                    responses.get(0).setPaidToName(paidToName);
                }
            }


            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/find/{id}",null,null,"Journal Entry Supervisor","Journal Entry Supervisor (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/find/{id}",line, error.toString(),"Journal Entry Supervisor","Journal Entry Supervisor (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(JournalEntryRequest journalEntryRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (supervisor level) (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            System.out.println("test 0");
            //! Check if credit and debit is equal
            if (journalEntryRequest.getJournalEntryDetailRequests().size() > 0){
                Double totalDebit = 0D;
                Double totalCredit = 0D;
                for(int i = 0; i < journalEntryRequest.getJournalEntryDetailRequests().size(); i++){
                    // Sum total Debit
                    totalDebit += journalEntryRequest.getJournalEntryDetailRequests().get(i).getDebit();
                    // Sum total Credit
                    totalCredit += journalEntryRequest.getJournalEntryDetailRequests().get(i).getCredit();
                }
                if(!totalDebit.equals(totalCredit)){
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Total amount of debit must be equal to total amount of credit.", false));
                }
            }
            System.out.println("test 1");

            // Check Data
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setDate(journalEntryRequest.getDate());
            journalEntry.setReference(journalEntryRequest.getReference());
            journalEntry.setIsRecurrence(journalEntryRequest.getIsRecurrence());
            journalEntry.setCompanyId(journalEntryRequest.getCompanyId());
            journalEntry.setBranchId(journalEntryRequest.getBranchId());
            journalEntry.setIsOrVoucher(journalEntryRequest.getIsOrVoucher());
            journalEntry.setIsPvVoucher(journalEntryRequest.getIsPvVoucher());
            journalEntry.setIsApprove(1L);
            journalEntry.setAdj(journalEntryRequest.getAdj());
            journalEntry.setExchangeRate(journalEntryRequest.getExchangeRate());
            journalEntry.setChequeNo(journalEntryRequest.getChequeNo());
            journalEntry.setNote(journalEntryRequest.getNote());

            journalEntry.setCreatedBy(userId);
            journalEntry.setIsActive(1);
            System.out.println("test 2 ");
            System.out.println(journalEntry);
            //! Generate Reference
            if(journalEntry.getIsOrVoucher() == 1) {
                String receivePaymentCode = journalEntrySupervisorMapper.getReLastReceivePaymentCode();
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                journalEntry.setReference(incrementedString);

            } else if (journalEntry.getIsPvVoucher() == 1){
                String payBillCode = journalEntrySupervisorMapper.getReLastPayBillCode();
                String incrementedString;

                if(payBillCode != null){
                    incrementedString = incrementNumericPart(payBillCode);
                }else{
                    incrementedString = generateReference("PV");
                }
                journalEntry.setReference(incrementedString);

            } else {
                journalEntry.setReference(journalEntryRequest.getReference());
            }

            System.out.println("test 3");

            // !Insert General Ledger
            Boolean insertGeneralLedgers = journalEntrySupervisorMapper.insert(journalEntry);
            System.out.println("inser general ledger: " + insertGeneralLedgers);
            //! Insert General Ledger Detail
            Boolean insertGeneralLedgerDetail = false;
            if(insertGeneralLedgers == true){
                if (journalEntryRequest.getJournalEntryDetailRequests().size() > 0){
                    List<JournalEntryDetail> journalEntryDetails = new ArrayList<>();
                    Double totalDebit = 0D;
                    for(int i = 0; i < journalEntryRequest.getJournalEntryDetailRequests().size(); i++){
                        // Set Data of General Ledger Detail
                        JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                        journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                        journalEntryDetail.setChartAccountId(journalEntryRequest.getJournalEntryDetailRequests().get(i).getChartAccountId());
                        journalEntryDetail.setCompanyId(journalEntry.getCompanyId());
                        journalEntryDetail.setCheque(journalEntry.getChequeNo());
                        journalEntryDetail.setExchangeRate(journalEntry.getExchangeRate());
                        journalEntryDetail.setDebit(journalEntryRequest.getJournalEntryDetailRequests().get(i).getDebit());
                        journalEntryDetail.setCredit(journalEntryRequest.getJournalEntryDetailRequests().get(i).getCredit());
                        journalEntryDetail.setType("General Journal");
                        journalEntryDetail.setMemo(journalEntryRequest.getJournalEntryDetailRequests().get(i).getMemo());
                        journalEntryDetail.setCustomerId(journalEntryRequest.getJournalEntryDetailRequests().get(i).getCustomerId());
                        journalEntryDetail.setVendorId(journalEntryRequest.getJournalEntryDetailRequests().get(i).getVendorId());
                        journalEntryDetail.setEmployeeId(journalEntryRequest.getJournalEntryDetailRequests().get(i).getEmployeeId());
                        journalEntryDetail.setClassId(journalEntryRequest.getJournalEntryDetailRequests().get(i).getClassId());
                        journalEntryDetail.setBranchId(journalEntryRequest.getBranchId());
                        journalEntryDetail.setCreatedBy(userId);


                        insertGeneralLedgerDetail = journalEntrySupervisorMapper.insertGeneralLedgerDetail(journalEntryDetail);
                        System.out.println("insert success");
                        // Set Data back to main model
                        journalEntryDetails.add(journalEntryDetail);

                        // Sum total Debit
                        totalDebit += journalEntryDetail.getDebit();

                    }
                    journalEntryDetails.get(0).setTotalDebit(totalDebit);
                    journalEntry.setJournalEntryDetails(journalEntryDetails);
                }
            }

            Boolean result = false;
            if(insertGeneralLedgerDetail){

                List<ChartAccountDropdownResponse> chartAccount = journalEntrySupervisorMapper.getAccountChartNameById(journalEntry.getJournalEntryDetails().get(0).getChartAccountId());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountName(chartAccount.get(0).getName());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountCode(chartAccount.get(0).getCode());
                journalEntry.getJournalEntryDetails().get(0).setDate(journalEntry.getDate());
                journalEntry.getJournalEntryDetails().get(0).setReference(journalEntry.getReference());

                if(journalEntry.getIsOrVoucher() == 1) {
                    result = journalEntrySupervisorMapper.insertReceivePayment(journalEntry.getJournalEntryDetails().get(0));
                } else if (journalEntry.getIsPvVoucher() == 1){
                    //! Insert Pay Bill
                    result = journalEntrySupervisorMapper.insertPayBill(journalEntry.getJournalEntryDetails().get(0));
                } else {
                    result = true;
                }
            }


            if (result) {
                /*System Activity*/
                //! Insert Jounal Entry File
                if(journalEntryRequest.getFile().size() > 0) {
                    for(int i = 0; i < journalEntryRequest.getFile().size(); i++){
                        journalEntrySupervisorMapper.insertJournalEntryFile(journalEntryRequest.getFile().get(i), journalEntry.getId());
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry-supervisor/add",null,null,"Journal Entry Supervisor","Journal Entry Supervisor (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/add",line, error.toString(),"Journal Entry Supervisor","Journal Entry Supervisor (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(JournalEntryUpdateRequest journalEntryUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (supervisor level) (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Check if credit and debit is equal
            if (journalEntryUpdateRequest.getJournalEntryDetailRequests().size() > 0){
                Double totalDebit = 0D;
                Double totalCredit = 0D;
                for(int i = 0; i < journalEntryUpdateRequest.getJournalEntryDetailRequests().size(); i++){
                    // Sum total Debit
                    totalDebit += journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getDebit();
                    // Sum total Credit
                    totalCredit += journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getCredit();
                }
                if(!totalDebit.equals(totalCredit)){
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Total amount of debit must be equal to total amount of credit.", false));
                }
            }

            // Check Data
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setId(journalEntryUpdateRequest.getId());
            journalEntry.setDate(journalEntryUpdateRequest.getDate());
            journalEntry.setCompanyId(journalEntryUpdateRequest.getCompanyId());
            journalEntry.setBranchId(journalEntryUpdateRequest.getBranchId());
            journalEntry.setIsOrVoucher(journalEntryUpdateRequest.getIsOrVoucher());
            journalEntry.setIsPvVoucher(journalEntryUpdateRequest.getIsPvVoucher());
            journalEntry.setReference(journalEntryUpdateRequest.getReference());
            journalEntry.setAdj(journalEntryUpdateRequest.getAdj());
            journalEntry.setExchangeRate(journalEntryUpdateRequest.getExchangeRate());
            journalEntry.setChequeNo(journalEntryUpdateRequest.getChequeNo());
            journalEntry.setNote(journalEntryUpdateRequest.getNote());
            //! Generate Reference
            if(journalEntry.getIsOrVoucher() == 1) {
                Long receivePaymentId = journalEntrySupervisorMapper.getReceivePaymentId(journalEntry.getId());
                if(receivePaymentId == null){
                    String receivePaymentCode = journalEntrySupervisorMapper.getReLastReceivePaymentCode();
                    String incrementedString;

                    if(receivePaymentCode != null){
                        incrementedString = incrementNumericPart(receivePaymentCode);
                    }else{
                        incrementedString = generateReference("OR");
                    }
                    journalEntry.setReference(incrementedString);
                } else {
                    journalEntry.setIsRecurrence(journalEntryUpdateRequest.getIsRecurrence());
                }
            } else if (journalEntry.getIsPvVoucher() == 1){

                Long payBillId = journalEntrySupervisorMapper.getPayBillId(journalEntry.getId());

                if(payBillId == null){
                    String payBillCode = journalEntrySupervisorMapper.getReLastPayBillCode();
                    String incrementedString;

                    if(payBillCode != null){
                        incrementedString = incrementNumericPart(payBillCode);
                    }else{
                        incrementedString = generateReference("PV");
                    }
                    journalEntry.setReference(incrementedString);
                }else {
                    journalEntry.setIsRecurrence(journalEntryUpdateRequest.getIsRecurrence());
                }
            } else {
                journalEntry.setReference(journalEntryUpdateRequest.getReference());
            }

            // !Insert General Ledger
            Boolean updateGeneralLedgers = journalEntrySupervisorMapper.update(journalEntry);
            Boolean insertGeneralLedgerDetail = false;
            if(updateGeneralLedgers == true) {
                //! Delete General Ledger Detail
                journalEntrySupervisorMapper.deleteGeneralLedgerDetail(journalEntry.getId());


                //! Insert General Ledger Detail
                if (journalEntryUpdateRequest.getJournalEntryDetailRequests().size() > 0){
                    List<JournalEntryDetail> journalEntryDetails = new ArrayList<>();
                    Double totalDebit = 0D;
                    for(int i = 0; i < journalEntryUpdateRequest.getJournalEntryDetailRequests().size(); i++){
                        // Set Data of General Ledger Detail
                        JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                        journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                        journalEntryDetail.setChartAccountId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getChartAccountId());
                        journalEntryDetail.setCompanyId(journalEntry.getCompanyId());
                        journalEntryDetail.setCheque(journalEntry.getChequeNo());
                        journalEntryDetail.setExchangeRate(journalEntry.getExchangeRate());
                        journalEntryDetail.setDebit(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getDebit());
                        journalEntryDetail.setCredit(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getCredit());
                        journalEntryDetail.setType("General Journal");
                        journalEntryDetail.setMemo(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getMemo());
                        journalEntryDetail.setCustomerId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getCustomerId());
                        journalEntryDetail.setVendorId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getVendorId());
                        journalEntryDetail.setEmployeeId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getEmployeeId());
                        journalEntryDetail.setClassId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getClassId());
                        journalEntryDetail.setBranchId(journalEntryUpdateRequest.getBranchId());
                        journalEntryDetail.setCreatedBy(userId);

                        insertGeneralLedgerDetail = journalEntrySupervisorMapper.insertGeneralLedgerDetail(journalEntryDetail);

                        // Set Data back to main model
                        journalEntryDetails.add(journalEntryDetail);

                        // Sum total Debit
                        totalDebit += journalEntryDetail.getDebit();

                    }
                    journalEntryDetails.get(0).setTotalDebit(totalDebit);
                    journalEntry.setJournalEntryDetails(journalEntryDetails);
                }
            }

            Boolean result = true;
            if(insertGeneralLedgerDetail){

                List<ChartAccountDropdownResponse> chartAccount = journalEntrySupervisorMapper.getAccountChartNameById(journalEntry.getJournalEntryDetails().get(0).getChartAccountId());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountName(chartAccount.get(0).getName());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountCode(chartAccount.get(0).getCode());
                journalEntry.getJournalEntryDetails().get(0).setDate(journalEntry.getDate());
                journalEntry.getJournalEntryDetails().get(0).setReference(journalEntry.getReference());

                if(journalEntry.getIsOrVoucher() == 1) {
                    Long receivePaymentId = journalEntrySupervisorMapper.getReceivePaymentId(journalEntry.getId());
                    //! Insert Receive Payment
                    if(receivePaymentId == null || receivePaymentId == 0){
                        result = journalEntrySupervisorMapper.insertReceivePayment(journalEntry.getJournalEntryDetails().get(0));
                    }
                } else if (journalEntry.getIsPvVoucher() == 1){
                    Long payBillId = journalEntrySupervisorMapper.getPayBillId(journalEntry.getId());
                    //! Insert Pay Bill
                    if(payBillId == null || payBillId == 0){
                        result = journalEntrySupervisorMapper.insertPayBill(journalEntry.getJournalEntryDetails().get(0));
                    }
                }
            }

            if (result) {
                //! Delete all files
                journalEntrySupervisorMapper.deleteJournalEntryFile(journalEntry.getId(), userId);

                //! Insert New files
                if(journalEntryUpdateRequest.getFile().size() > 0) {
                    for(int i = 0; i < journalEntryUpdateRequest.getFile().size(); i++){
                        journalEntrySupervisorMapper.insertJournalEntryFile(journalEntryUpdateRequest.getFile().get(i), journalEntry.getId());
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry-supervisor/update",null,null,"Journal Entry Supervisor","Journal Entry Supervisor (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/update",line, error.toString(),"Journal Entry Supervisor","Journal Entry Supervisor (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Journal Entry (supervisor level) (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = journalEntrySupervisorMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry-supervisor/delete/{id}", null, null, "Journal Entry Supervisor", "Journal Entry Supervisor (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/delete/{id}", line, error.toString(), "Journal Entry Supervisor", "Journal Entry Supervisor (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateStatus(JournalEntryStatusRequest journalEntryStatusRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Journal Entry (supervisor level) (change status)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            System.out.println(journalEntryStatusRequest);

            Boolean result = journalEntrySupervisorMapper.updateStatus(journalEntryStatusRequest, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry-supervisor/change status/{id}", null, null, "Journal Entry Supervisor", "Journal Entry Supervisor (change status)", "change status", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry-supervisor/change status/{id}", line, error.toString(), "Journal Entry Supervisor", "Journal Entry Supervisor (change status)", "change status", 2, "Error", startDuration, endDuration, httpServletRequest);
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
        String result = yearLastTwoDigits + input + "000001";
        return result;
    }

}
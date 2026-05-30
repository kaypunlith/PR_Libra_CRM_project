package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.JournalEntryMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.JournalEntry;
import com.ut.nlSystemAPi.model.JournalEntryDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.JournalEntryFilter;
import com.ut.nlSystemAPi.model.filter.MakeDepositFilter;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.*;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountDropdownResponse;
import com.ut.nlSystemAPi.model.response.JournalEntry.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

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

    public ResponseMessage<BaseResult> getList(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Restrict to current user's entries
            filter.setCreateBy(userId);
            filter.setUserId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(journalEntryMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            Long chartAccountGroupTypeId = null;
            if (filter.getChartAccountGroupId() != null) {
                filter.setOrderBy("general_ledgers.date ASC, general_ledgers.id ASC");
                chartAccountGroupTypeId = journalEntryMapper.getChartAccountGroupType(filter.getChartAccountGroupId());
            }

            List<JournalEntryResponse> journalEntryResponse = journalEntryMapper.getList(filter);

            List<JournalEntryDetailResponse> responses = new ArrayList<>();

            if (!journalEntryResponse.isEmpty()) {
                List<Long> journalEntryIds = journalEntryResponse.stream()
                        .map(JournalEntryResponse::getId)
                        .collect(Collectors.toList());

                Map<Long, List<JournalEntryDetailResponse>> journalEntryDetailMap = journalEntryIds.isEmpty()
                        ? Collections.emptyMap()
                        : journalEntryMapper.getJournalEntryDetailByJournalIds(journalEntryIds, null, null)
                        .stream()
                        .collect(Collectors.groupingBy(JournalEntryDetailResponse::getJournalEntryId));

                Map<Long, List<JournalEntryFileResponse>> journalEntryFileMap = journalEntryIds.isEmpty()
                        ? Collections.emptyMap()
                        : journalEntryMapper.getJournalEntryFilesByJournalIds(journalEntryIds)
                        .stream()
                        .collect(Collectors.groupingBy(JournalEntryFileResponse::getJournalEntryId));

                for (int i = 0; i < journalEntryResponse.size(); i++) {
                    JournalEntryResponse journalEntry = journalEntryResponse.get(i);
                    Long journalEntryId = journalEntry.getId();

                    long isOrVoucher = 0L;
                    long isPvVoucher = 0L;

                    String reference = journalEntry.getReference();
                    if (reference != null && reference.length() >= 4) {
                        String extracted = reference.substring(2, 4);
                        if ("OR".equals(extracted)) {
                            isOrVoucher = 1L;
                        } else if ("PV".equals(extracted)) {
                            isPvVoucher = 1L;
                        }
                    }

                    journalEntry.setIsOrVoucher(isOrVoucher);
                    journalEntry.setIsPvVoucher(isPvVoucher);

                    List<JournalEntryDetailResponse> journalEntryDetail = journalEntryDetailMap.getOrDefault(journalEntryId, Collections.emptyList());

                    if (!journalEntryDetail.isEmpty()) {
                        JournalEntryDetailResponse header = journalEntryDetail.get(0);
                        header.setNo((long) (i + 1));
                        header.setDate(journalEntry.getDate());
                        header.setReference(journalEntry.getReference());
                        header.setAdj(journalEntry.getAdj());
                        header.setCreatedBy(journalEntry.getCreatedBy());
                        header.setStatus(journalEntry.getStatus());
                        header.setIsRecurrence(journalEntry.getIsRecurrence());
                        header.setNote(journalEntry.getNote());
                        header.setBranchName(journalEntry.getBranchName());
                        header.setBranchId(journalEntry.getBranchId());
                        header.setIsOrVoucher(journalEntry.getIsOrVoucher());
                        header.setIsPvVoucher(journalEntry.getIsPvVoucher());
                        header.setFile(journalEntryFileMap.getOrDefault(journalEntryId, Collections.emptyList()));

                        // Add to list
                        responses.addAll(journalEntryDetail);
                    }
                }
            }

            if (filter.getChartAccountGroupId() != null && !responses.isEmpty()) {
                List<Long> debitTypeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 12L, 13L, 15L);
                boolean isDebitTypeGroup = chartAccountGroupTypeId != null && debitTypeIds.contains(chartAccountGroupTypeId);
                responses.sort(Comparator
                        .comparing(JournalEntryDetailResponse::getDate, Comparator.nullsLast(String::compareTo))
                        .thenComparing(JournalEntryDetailResponse::getJournalEntryId, Comparator.nullsLast(Long::compareTo))
                        .thenComparing(JournalEntryDetailResponse::getId, Comparator.nullsLast(Long::compareTo)));

                double runningBalance = 0D;
                for (JournalEntryDetailResponse detail : responses) {
                    Double debit = detail.getDebit() != null ? detail.getDebit() : 0D;
                    Double credit = detail.getCredit() != null ? detail.getCredit() : 0D;
                    double amount = isDebitTypeGroup ? (debit - credit) : (credit - debit);
                    runningBalance += amount;
                    detail.setAmount(amount * -1);
                    detail.setCalculatedBalance(runningBalance);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/list",null,null,"Journal Entry","Journal Entry (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/list",line, error.toString(),"Journal Entry","Journal Entry (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListReport(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // For report view, enforce approved entries as per PHP report
            filter.setStatus("1");
            filter.setOrderBy("general_ledgers.date ASC, general_ledgers.id ASC");

            // Restrict to companies the current user can access when no company filter supplied
            Long userId = userService.getUserAuth().getId();
            filter.setUserId(userId);
            Long chartAccountGroupTypeId = null;
            if (filter.getChartAccountGroupId() != null) {
                chartAccountGroupTypeId = journalEntryMapper.getChartAccountGroupType(filter.getChartAccountGroupId());
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(journalEntryMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            System.out.println(filter);

            List<JournalEntryResponse> journalEntryResponse = journalEntryMapper.getList(filter);
            System.out.println(journalEntryResponse);
            List<JournalEntryDetailResponse> responses = new ArrayList<>();

            if (!journalEntryResponse.isEmpty()) {
                List<Long> journalEntryIds = journalEntryResponse.stream()
                        .map(JournalEntryResponse::getId)
                        .collect(Collectors.toList());
                System.out.println(journalEntryIds);
                Map<Long, List<JournalEntryDetailResponse>> journalEntryDetailMap = journalEntryIds.isEmpty()
                        ? Collections.emptyMap()
                        : journalEntryMapper.getJournalEntryDetailByJournalIds(journalEntryIds, filter.getChartAccountGroupId(), filter.getChartAccountId())
                        .stream()
                        .collect(Collectors.groupingBy(JournalEntryDetailResponse::getJournalEntryId));
                System.out.println(journalEntryDetailMap);
                Map<Long, List<JournalEntryFileResponse>> journalEntryFileMap = journalEntryIds.isEmpty()
                        ? Collections.emptyMap()
                        : journalEntryMapper.getJournalEntryFilesByJournalIds(journalEntryIds)
                        .stream()
                        .collect(Collectors.groupingBy(JournalEntryFileResponse::getJournalEntryId));

                for (int i = 0; i < journalEntryResponse.size(); i++) {
                    JournalEntryResponse journalEntry = journalEntryResponse.get(i);
                    Long journalEntryId = journalEntry.getId();

                    long isOrVoucher = 0L;
                    long isPvVoucher = 0L;

                    String reference = journalEntry.getReference();
                    if (reference != null && reference.length() >= 4) {
                        String extracted = reference.substring(2, 4);
                        if ("OR".equals(extracted)) {
                            isOrVoucher = 1L;
                        } else if ("PV".equals(extracted)) {
                            isPvVoucher = 1L;
                        }
                    }

                    journalEntry.setIsOrVoucher(isOrVoucher);
                    journalEntry.setIsPvVoucher(isPvVoucher);

                    List<JournalEntryDetailResponse> journalEntryDetail = journalEntryDetailMap.getOrDefault(journalEntryId, Collections.emptyList());

                    if (!journalEntryDetail.isEmpty()) {
                        JournalEntryDetailResponse header = journalEntryDetail.get(0);
                        header.setNo((long) (i + 1));
                        header.setDate(journalEntry.getDate());
                        header.setReference(journalEntry.getReference());
                        header.setAdj(journalEntry.getAdj());
                        header.setCreatedBy(journalEntry.getCreatedBy());
                        header.setStatus(journalEntry.getStatus());
                        header.setIsRecurrence(journalEntry.getIsRecurrence());
                        header.setNote(journalEntry.getNote());
                        header.setBranchName(journalEntry.getBranchName());
                        header.setBranchId(journalEntry.getBranchId());
                        header.setIsOrVoucher(journalEntry.getIsOrVoucher());
                        header.setIsPvVoucher(journalEntry.getIsPvVoucher());
                        header.setFile(journalEntryFileMap.getOrDefault(journalEntryId, Collections.emptyList()));

                        // Add to list
                        responses.addAll(journalEntryDetail);
                    }
                }
            }

            if (filter.getChartAccountGroupId() != null && !responses.isEmpty()) {
                List<Long> debitTypeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 12L, 13L, 15L);
                boolean isDebitTypeGroup = chartAccountGroupTypeId != null && debitTypeIds.contains(chartAccountGroupTypeId);
                responses.sort(Comparator
                        .comparing(JournalEntryDetailResponse::getDate, Comparator.nullsLast(String::compareTo))
                        .thenComparing(JournalEntryDetailResponse::getJournalEntryId, Comparator.nullsLast(Long::compareTo))
                        .thenComparing(JournalEntryDetailResponse::getId, Comparator.nullsLast(Long::compareTo)));

                double runningBalance = 0D;
                for (JournalEntryDetailResponse detail : responses) {
                    Double debit = detail.getDebit() != null ? detail.getDebit() : 0D;
                    Double credit = detail.getCredit() != null ? detail.getCredit() : 0D;
                    double amount = isDebitTypeGroup ? (debit - credit) : (credit - debit);
                    runningBalance += amount;
                    detail.setAmount(amount);
                    detail.setBalance(runningBalance);
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("",null,null,"Journal Entry","Journal Entry (View)", "View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("",line, error.toString(),"Journal Entry","Journal Entry (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListReportByGroup(JournalEntryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            System.out.println(filter);
            // Align with PHP ajax_by_group: apply date as of (<=) by setting an earliest from date when only dateTo/asOf is provided
            if (filter.getDateTo() != null && (filter.getDateFrom() == null || filter.getDateFrom().isEmpty())) {
                filter.setDateFrom("1900-01-01");
            }

            // Default status to approved entries to match legacy report
            if (filter.getStatus() == null || filter.getStatus().isEmpty()) {
                filter.setStatus("1");
            }
            filter.setOrderBy("gl.date ASC, gl.id ASC, gld.id ASC");

            Long userId = userService.getUserAuth().getId();
            filter.setUserId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(journalEntryMapper.countListReportByGroup(filter));
            if (filter.getPage() != null && filter.getRowsPerPage() != null) {
                filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            }

            List<JournalEntryDetailResponse> responses = journalEntryMapper.getListReportByGroup(filter);
            System.out.println(responses);
            if (filter.getChartAccountGroupId() != null && !responses.isEmpty()) {
                List<Long> debitTypeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 12L, 13L, 15L);
                System.out.println(debitTypeIds);
                Long chartAccountGroupTypeId = journalEntryMapper.getChartAccountGroupType(filter.getChartAccountGroupId());
                boolean isDebitTypeGroup = chartAccountGroupTypeId != null && debitTypeIds.contains(chartAccountGroupTypeId);
                responses.sort(Comparator
                        .comparing(JournalEntryDetailResponse::getDate, Comparator.nullsLast(String::compareTo))
                        .thenComparing(JournalEntryDetailResponse::getJournalEntryId, Comparator.nullsLast(Long::compareTo))
                        .thenComparing(JournalEntryDetailResponse::getId, Comparator.nullsLast(Long::compareTo)));

                long counter = 1L;
                double runningBalance = 0D;
                for (JournalEntryDetailResponse detail : responses) {
                    Double signedAmount = detail.getSignedAmount() != null ? detail.getSignedAmount() : 0D;
                    double amount = isDebitTypeGroup ? signedAmount : signedAmount * -1;
                    runningBalance += amount;
                    detail.setNo(counter++);
                    detail.setAmount(amount);
                    detail.setBalance(runningBalance);
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/list-report-by-group",null,null,"Journal Entry","Journal Entry (View)", "View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/list-report-by-group",line, error.toString(),"Journal Entry","Journal Entry (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Journal Entry (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<JournalEntryResponse> responses = journalEntryMapper.getOne(id);

            //! Find OR or PV Voucher
            if(responses.size() > 0){
                String extracted = responses.get(0).getReference().substring(2, 4);
                if(extracted.equals("OR")){
                    List<ReceivePaymentsResponse> receivePaymentResponses = journalEntryMapper.getReLastReceivePaymentByJournalId(id);
                    if(receivePaymentResponses.size() > 0){
                        responses.get(0).setExchangeRate(receivePaymentResponses.get(0).getExchangeRate());
                        responses.get(0).setChequeNo(receivePaymentResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsOrVoucher(1L);
                } else if (extracted.equals("PV")) {
                    List<ReceivePaymentsResponse> payBillResponses = journalEntryMapper.getReLastPayBillByJournalId(id);
                    if(payBillResponses.size() > 0){
                        responses.get(0).setExchangeRate(payBillResponses.get(0).getExchangeRate());
                        responses.get(0).setChequeNo(payBillResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsPvVoucher(1L);
                }
            }

            if(responses.size() > 0){
                Long journalEntryId = responses.get(0).getId();
                List<JournalEntryDetailResponse> journalEntryDetail = journalEntryMapper.getJournalEntryDetail(journalEntryId);

                responses.get(0).setJournalEntryDetail(journalEntryDetail);
                responses.get(0).setCompanyId(journalEntryDetail.get(0).getCompanyId());
                responses.get(0).setCompanyName(journalEntryDetail.get(0).getCompanyName());
                responses.get(0).setFile(journalEntryMapper.getJournalEntryFile(journalEntryId));

                //! Find Paid To Name
                if(journalEntryDetail.size() > 0){
                    String paidToName = "";

                    for (int i = 0; i < journalEntryDetail.size(); i++) {
                        if(journalEntryDetail.get(i).getCredit() > 0){
                            if(i == 0){
                                System.out.println("Testing02");
                                if(journalEntryDetail.get(i).getCustomerName() != null){
                                    paidToName = paidToName + journalEntryDetail.get(i).getCustomerName();
                                } else if(journalEntryDetail.get(i).getEmployeeName() != null){
                                    paidToName = paidToName + journalEntryDetail.get(i).getEmployeeName();
                                } else if (journalEntryDetail.get(i).getVendorName() != null){
                                    paidToName = paidToName + journalEntryDetail.get(i).getVendorName();
                                }
                            } else {
                                if(paidToName.equals("")){
                                    if(journalEntryDetail.get(i).getCustomerName() != null){
                                        paidToName = journalEntryDetail.get(i).getCustomerName();
                                    } else if(journalEntryDetail.get(i).getEmployeeName() != null){
                                        paidToName = journalEntryDetail.get(i).getEmployeeName();
                                    } else if (journalEntryDetail.get(i).getVendorName() != null){
                                        paidToName = journalEntryDetail.get(i).getVendorName();
                                    }
                                } else {
                                    if(journalEntryDetail.get(i).getCustomerName() != null){
                                        paidToName = paidToName + ", " + journalEntryDetail.get(i).getCustomerName();
                                    } else if(journalEntryDetail.get(i).getEmployeeName() != null){
                                        paidToName = paidToName + ", " + journalEntryDetail.get(i).getEmployeeName();
                                    } else if (journalEntryDetail.get(i).getVendorName() != null){
                                        paidToName = paidToName + ", " + journalEntryDetail.get(i).getVendorName();
                                    }
                                }
                            }

                        }
                    }
                    responses.get(0).setPaidToName(paidToName);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/find/{id}",null,null,"Journal Entry","Journal Entry (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/find/{id}",line, error.toString(),"Journal Entry","Journal Entry (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Journal Entry (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Check if credit and debit is equal
            System.out.println(journalEntryRequest.getJournalEntryDetailRequests());
            if (!journalEntryRequest.getJournalEntryDetailRequests().isEmpty()){
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

            // Check Data
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setDate(journalEntryRequest.getDate());
            journalEntry.setReference(journalEntryRequest.getReference());
            journalEntry.setIsRecurrence(journalEntryRequest.getIsRecurrence());
            journalEntry.setCompanyId(journalEntryRequest.getCompanyId());
            journalEntry.setBranchId(journalEntryRequest.getBranchId());
            journalEntry.setIsOrVoucher(journalEntryRequest.getIsOrVoucher());
            journalEntry.setIsPvVoucher(0L);
            journalEntry.setIsPvVoucher(journalEntryRequest.getIsPvVoucher());
            journalEntry.setIsApprove(0L);
            journalEntry.setAdj(journalEntryRequest.getAdj());
            journalEntry.setExchangeRate(journalEntryRequest.getExchangeRate());
            journalEntry.setChequeNo(journalEntryRequest.getChequeNo());
            journalEntry.setNote(journalEntryRequest.getNote());
            journalEntry.setDepositType(0L);
            journalEntry.setCreatedBy(userId);
            journalEntry.setIsActive(1);

            //! Generate Reference
            if(journalEntry.getIsOrVoucher() == 1) {
                String receivePaymentCode = journalEntryMapper.getReLastReceivePaymentCode();
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                journalEntry.setReference(incrementedString);

            } else if (journalEntry.getIsPvVoucher() == 1){
                String payBillCode = journalEntryMapper.getReLastPayBillCode();
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

            // !Insert General Ledger
            Boolean insertGeneralLedgers = journalEntryMapper.insert(journalEntry);

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

                        insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetail);

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
                List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(journalEntry.getJournalEntryDetails().get(0).getChartAccountId());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountName(chartAccount.get(0).getName());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountCode(chartAccount.get(0).getCode());
                journalEntry.getJournalEntryDetails().get(0).setDate(journalEntry.getDate());
                journalEntry.getJournalEntryDetails().get(0).setReference(journalEntry.getReference());
                if(journalEntry.getIsOrVoucher() == 1) {
                    //! Insert Receive Payment
                    result = journalEntryMapper.insertReceivePayment(journalEntry.getJournalEntryDetails().get(0));
                } else if (journalEntry.getIsPvVoucher() == 1){
                    //! Insert Pay Bill
                    System.out.println("PV Voucher");
                    System.out.println(journalEntry.getJournalEntryDetails().get(0));
                    result = journalEntryMapper.insertPayBill(journalEntry.getJournalEntryDetails().get(0));
                    System.out.println("PV Voucher1");
                }
            }

            if (result) {
                //! Insert Journal Entry File
                if(journalEntryRequest.getFile() != null && journalEntryRequest.getFile().size() > 0) {
                    for(int i = 0; i < journalEntryRequest.getFile().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(journalEntryRequest.getFile().get(i), journalEntry.getId());
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/add",null,null,"Journal Entry","Journal Entry (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, journalEntry.getId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/add",line, error.toString(),"Journal Entry","Journal Entry (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> writeChecks(WriteChecksRequest writeChecksRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (write checks)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Check if credit and debit is equal
            if (writeChecksRequest.getWriteChecksRequestDetailRequests().size() > 0){
                Double totalDebit = 0D;
                Double totalCredit = writeChecksRequest.getAmount();

                for(int i = 0; i < writeChecksRequest.getWriteChecksRequestDetailRequests().size(); i++){
                    // Sum total Debit
                    totalDebit += writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getAmount();
                }
                if(!totalDebit.equals(totalCredit)){
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Total amount of debit must be equal to total amount of credit.", false));
                }
            }

            // Check Data
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setDate(writeChecksRequest.getDate());
            journalEntry.setReference(writeChecksRequest.getReference());
            journalEntry.setIsRecurrence(writeChecksRequest.getIsRecurrence());
            journalEntry.setCompanyId(writeChecksRequest.getCompanyId());
            journalEntry.setBranchId(writeChecksRequest.getBranchId());
            journalEntry.setExchangeRate(writeChecksRequest.getExchangeRate());
            journalEntry.setAdj(0L);
            journalEntry.setIsOrVoucher(writeChecksRequest.getIsOrVoucher());
            journalEntry.setIsPvVoucher(writeChecksRequest.getIsPvVoucher());
            journalEntry.setIsApprove(0L);
            journalEntry.setChequeNo(writeChecksRequest.getChequeNo());
            journalEntry.setNote(writeChecksRequest.getNote());
            journalEntry.setDepositType(0L);

            journalEntry.setCreatedBy(userId);
            journalEntry.setIsActive(1);

            //! Generate Reference
            if(journalEntry.getIsOrVoucher() == 1) {
                String receivePaymentCode = journalEntryMapper.getReLastReceivePaymentCode();
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                journalEntry.setReference(incrementedString);

            } else if (journalEntry.getIsPvVoucher() == 1){
                String payBillCode = journalEntryMapper.getReLastPayBillCode();
                String incrementedString;

                if(payBillCode != null){
                    incrementedString = incrementNumericPart(payBillCode);
                }else{
                    incrementedString = generateReference("PV");
                }
                journalEntry.setReference(incrementedString);

            } else {
                journalEntry.setReference(writeChecksRequest.getReference());
            }

            // !Insert General Ledger
            Boolean insertGeneralLedgers = journalEntryMapper.insert(journalEntry);

            //! Insert General Ledger Detail
            Boolean insertGeneralLedgerDetail = false;

            JournalEntryDetail journalEntryDetailMain = new JournalEntryDetail();

            if(insertGeneralLedgers == true){

                //! Set data for general ledger 1
                journalEntryDetailMain.setGeneralLedgerId(journalEntry.getId());
                journalEntryDetailMain.setChartAccountId(writeChecksRequest.getChartAccountId());
                journalEntryDetailMain.setCompanyId(writeChecksRequest.getCompanyId());
                journalEntryDetailMain.setBranchId(writeChecksRequest.getBranchId());
                journalEntryDetailMain.setCheque(writeChecksRequest.getChequeNo());
                journalEntryDetailMain.setType("Check");
                journalEntryDetailMain.setDebit(0D);
                journalEntryDetailMain.setCredit(writeChecksRequest.getAmount());
                journalEntryDetailMain.setMemo(writeChecksRequest.getNote());
                journalEntryDetailMain.setCustomerId(writeChecksRequest.getCustomerId());
                journalEntryDetailMain.setVendorId(writeChecksRequest.getVendorId());
                journalEntryDetailMain.setEmployeeId(writeChecksRequest.getEmployeeId());
                journalEntryDetailMain.setClassId(writeChecksRequest.getClassId());
                journalEntryDetailMain.setExchangeRate(writeChecksRequest.getExchangeRate());
                journalEntryDetailMain.setCreatedBy(userId);

                // !Insert General Ledger Detail Main
                insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetailMain);

                if (writeChecksRequest.getWriteChecksRequestDetailRequests().size() > 0){
                    List<JournalEntryDetail> journalEntryDetails = new ArrayList<>();
                    Double totalDebit = 0D;
                    for(int i = 0; i < writeChecksRequest.getWriteChecksRequestDetailRequests().size(); i++){
                        // Set Data of General Ledger Detail
                        System.out.println("Add write checks request detail successfully");
                        JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                        journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                        journalEntryDetail.setChartAccountId(writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getChartAccountId());
                        journalEntryDetail.setCompanyId(journalEntry.getCompanyId());
                        journalEntryDetail.setCheque(journalEntry.getChequeNo());
                        journalEntryDetail.setExchangeRate(journalEntry.getExchangeRate());
                        journalEntryDetail.setDebit(writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getAmount());
                        journalEntryDetail.setCredit(0D);
                        journalEntryDetail.setType("Check");
                        journalEntryDetail.setMemo(writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getMemo());
                        journalEntryDetail.setCustomerId(writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getCustomerId());
                        journalEntryDetail.setVendorId(writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getVendorId());
                        journalEntryDetail.setEmployeeId(writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getEmployeeId());
                        journalEntryDetail.setClassId(writeChecksRequest.getWriteChecksRequestDetailRequests().get(i).getClassId());
                        journalEntryDetail.setBranchId(writeChecksRequest.getBranchId());
                        journalEntryDetail.setCreatedBy(userId);

                        insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetail);

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

                List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(journalEntryDetailMain.getChartAccountId());
                journalEntryDetailMain.setChartAccountName(chartAccount.get(0).getName());
                journalEntryDetailMain.setChartAccountCode(chartAccount.get(0).getCode());
                journalEntryDetailMain.setDate(journalEntry.getDate());
                journalEntryDetailMain.setReference(journalEntry.getReference());
                journalEntryDetailMain.setBranchId(writeChecksRequest.getBranchId());

                if(journalEntry.getIsOrVoucher() == 1) {

                    //! Insert Receive Payment
                    result = journalEntryMapper.insertReceivePayment(journalEntryDetailMain);
                } else if (journalEntry.getIsPvVoucher() == 1){
                    //! Insert Pay Bill
                    result = journalEntryMapper.insertPayBill(journalEntryDetailMain);
                }
            }

            if (result) {
                //! Insert Journal Entry File
                if(writeChecksRequest.getFile().size() > 0) {
                    for(int i = 0; i < writeChecksRequest.getFile().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(writeChecksRequest.getFile().get(i), journalEntry.getId());
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/write-checks",null,null,"Journal Entry","Journal Entry (write checks)","(write checks)",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, journalEntry.getId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/write-checks",line, error.toString(),"Journal Entry","Journal Entry (write checks)","(write checks)",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> updateWriteChecks(WriteChecksRequestUpdate requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (write checks)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Check if credit and debit is equal
            if (requestUpdate.getWriteChecksRequestDetailRequests().size() > 0){
                Double totalDebit = 0D;
                Double totalCredit = requestUpdate.getAmount();

                for(int i = 0; i < requestUpdate.getWriteChecksRequestDetailRequests().size(); i++){
                    // Sum total Debit
                    totalDebit += requestUpdate.getWriteChecksRequestDetailRequests().get(i).getDebit();
                }
                if(!totalDebit.equals(totalCredit)){
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Total amount of debit must be equal to total amount of credit.", false));
                }
            }

            // Check Data
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setId(requestUpdate.getId());
            journalEntry.setDate(requestUpdate.getDate());
            journalEntry.setReference(requestUpdate.getReference());
            journalEntry.setIsRecurrence(requestUpdate.getIsRecurrence());
            journalEntry.setCompanyId(requestUpdate.getCompanyId());
            journalEntry.setBranchId(requestUpdate.getBranchId());
            journalEntry.setExchangeRate(requestUpdate.getExchangeRate());
            journalEntry.setAdj(0L);
            journalEntry.setIsOrVoucher(requestUpdate.getIsOrVoucher());
            journalEntry.setIsPvVoucher(requestUpdate.getIsPvVoucher());
            journalEntry.setIsApprove(0L);
            journalEntry.setChequeNo(requestUpdate.getChequeNo());
            journalEntry.setNote(requestUpdate.getNote());
            journalEntry.setDepositType(0L);

            journalEntry.setCreatedBy(userId);
            journalEntry.setIsActive(1);

            //! Generate Reference
            if(journalEntry.getIsOrVoucher() == 1) {
                String receivePaymentCode = journalEntryMapper.getReLastReceivePaymentCode();
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                journalEntry.setReference(incrementedString);

            } else if (journalEntry.getIsPvVoucher() == 1){
                String payBillCode = journalEntryMapper.getReLastPayBillCode();
                String incrementedString;

                if(payBillCode != null){
                    incrementedString = incrementNumericPart(payBillCode);
                }else{
                    incrementedString = generateReference("PV");
                }
                journalEntry.setReference(incrementedString);

            } else {
                journalEntry.setReference(requestUpdate.getReference());
            }

            // !update General Ledger
            Boolean updateGeneralLedgers = journalEntryMapper.update(journalEntry);

            //! Insert General Ledger Detail
            Boolean insertGeneralLedgerDetail = false;

            JournalEntryDetail journalEntryDetailMain = new JournalEntryDetail();

            if(updateGeneralLedgers == true){

                //! Delete General Ledger Detail
                journalEntryMapper.deleteGeneralLedgerDetail(journalEntry.getId());

                //! Set data for general ledger 1
                journalEntryDetailMain.setGeneralLedgerId(journalEntry.getId());
                journalEntryDetailMain.setChartAccountId(requestUpdate.getChartAccountId());
                journalEntryDetailMain.setCompanyId(requestUpdate.getCompanyId());
                journalEntryDetailMain.setCheque(requestUpdate.getChequeNo());
                journalEntryDetailMain.setType("Check");
                journalEntryDetailMain.setDebit(0D);
                journalEntryDetailMain.setCredit(requestUpdate.getAmount());
                journalEntryDetailMain.setMemo(requestUpdate.getNote());
                journalEntryDetailMain.setCustomerId(requestUpdate.getCustomerId());
                journalEntryDetailMain.setVendorId(requestUpdate.getVendorId());
                journalEntryDetailMain.setEmployeeId(requestUpdate.getEmployeeId());
                journalEntryDetailMain.setClassId(requestUpdate.getClassId());
                journalEntryDetailMain.setExchangeRate(requestUpdate.getExchangeRate());
                journalEntryDetailMain.setCreatedBy(userId);

                // !Insert General Ledger Detail Main
                insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetailMain);

                if (requestUpdate.getWriteChecksRequestDetailRequests().size() > 0){
                    List<JournalEntryDetail> journalEntryDetails = new ArrayList<>();
                    Double totalDebit = 0D;
                    for(int i = 0; i < requestUpdate.getWriteChecksRequestDetailRequests().size(); i++){
                        // Set Data of General Ledger Detail
                        JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                        journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                        journalEntryDetail.setChartAccountId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getChartAccountId());
                        journalEntryDetail.setCompanyId(journalEntry.getCompanyId());
                        journalEntryDetail.setCheque(journalEntry.getChequeNo());
                        journalEntryDetail.setExchangeRate(journalEntry.getExchangeRate());
                        journalEntryDetail.setDebit(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getDebit());
                        journalEntryDetail.setCredit(0D);
                        journalEntryDetail.setType("Check");
                        journalEntryDetail.setMemo(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getMemo());
                        journalEntryDetail.setCustomerId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getCustomerId());
                        journalEntryDetail.setVendorId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getVendorId());
                        journalEntryDetail.setEmployeeId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getEmployeeId());
                        journalEntryDetail.setClassId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getClassId());
                        journalEntryDetail.setBranchId(requestUpdate.getBranchId());
                        journalEntryDetail.setCreatedBy(userId);

                        insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetail);

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

                List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(journalEntryDetailMain.getChartAccountId());
                journalEntryDetailMain.setChartAccountName(chartAccount.get(0).getName());
                journalEntryDetailMain.setChartAccountCode(chartAccount.get(0).getCode());
                journalEntryDetailMain.setDate(journalEntry.getDate());
                journalEntryDetailMain.setReference(journalEntry.getReference());

                if(journalEntry.getIsOrVoucher() == 1) {
                    //! Delete Receive Payment
                    journalEntryMapper.deleteReceivePaymentByGLId(journalEntry.getId(), userId);
                    //! Insert Receive Payment
                    result = journalEntryMapper.insertReceivePayment(journalEntryDetailMain);
                } else if (journalEntry.getIsPvVoucher() == 1){
                    //! Delete Pay Bill
                    journalEntryMapper.deletePayBillByGLId(journalEntry.getId(), userId);
                    //! Insert Pay Bill
                    result = journalEntryMapper.insertPayBill(journalEntryDetailMain);
                }
            }

            if (result) {
                //! Insert Journal Entry File
                if(requestUpdate.getFile().size() > 0) {
                    journalEntryMapper.deleteJournalEntryFile(journalEntry.getId(), userId);
                    for(int i = 0; i < requestUpdate.getFile().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(requestUpdate.getFile().get(i), journalEntry.getId());
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/write-checks",null,null,"Journal Entry","Journal Entry (write checks)","(write checks)",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/write-checks",line, error.toString(),"Journal Entry","Journal Entry (write checks)","(write checks)",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> findCheckById(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Journal Entry (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<JournalEntryResponse> responses = journalEntryMapper.getOne(id);

            //! Find OR or PV Voucher
            if(responses.size() > 0){
                String extracted = responses.get(0).getReference().substring(2, 4);
                if(extracted.equals("OR")){
                    List<ReceivePaymentsResponse> receivePaymentResponses = journalEntryMapper.getReLastReceivePaymentByJournalId(id);
                    if(receivePaymentResponses.size() > 0){
                        responses.get(0).setExchangeRate(4000D);
                        responses.get(0).setChequeNo(receivePaymentResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsOrVoucher(1L);
                } else if (extracted.equals("PV")) {
                    List<ReceivePaymentsResponse> payBillResponses = journalEntryMapper.getReLastPayBillByJournalId(id);
                    if(payBillResponses.size() > 0){
                        responses.get(0).setExchangeRate(4000D);
                        responses.get(0).setChequeNo(payBillResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsPvVoucher(1L);
                }
            }

            if(responses.size() > 0){
                Long journalEntryId = responses.get(0).getId();
                List<JournalEntryDetailResponse> journalEntryDetail = journalEntryMapper.getJournalEntryDetail(journalEntryId);

                responses.get(0).setJournalEntryDetail(journalEntryDetail);
                responses.get(0).setCompanyId(journalEntryDetail.get(0).getCompanyId());
                responses.get(0).setCompanyName(journalEntryDetail.get(0).getCompanyName());
                responses.get(0).setFile(journalEntryMapper.getJournalEntryFile(journalEntryId));
            }

            responses.get(0).setCOA(journalEntryMapper.getCOA(responses.get(0).getId()));
            String paidTo = journalEntryMapper.getPaidTo(responses.get(0).getId());
            if(paidTo == null) {
                responses.get(0).setPaidTo("");
            }else{
                responses.get(0).setPaidTo(paidTo);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/find-check/find/{id}",null,null,"Journal Entry","Journal Entry (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/find-check/{id}",line, error.toString(),"Journal Entry","Journal Entry (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> makeDeposits(MakeDepositsRequest makeDepositsRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (make deposits)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            //! Check if credit and debit is equal
            if (makeDepositsRequest.getWriteChecksRequestDetailRequests().size() > 0){
                Double totalDebit = 0D;
                Double totalCredit = makeDepositsRequest.getAmount();

                for(int i = 0; i < makeDepositsRequest.getWriteChecksRequestDetailRequests().size(); i++){
                    // Sum total Debit
                    totalDebit += makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getAmount();
                }
                if(!totalDebit.equals(totalCredit)){
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Total amount of debit must be equal to total amount of credit.", false));
                }
            }
            // Check Data
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setDate(makeDepositsRequest.getDate());
            journalEntry.setReference(makeDepositsRequest.getReference());
            journalEntry.setIsRecurrence(makeDepositsRequest.getIsRecurrence());
            journalEntry.setCompanyId(makeDepositsRequest.getCompanyId());
            journalEntry.setBranchId(makeDepositsRequest.getBranchId());
            journalEntry.setExchangeRate(makeDepositsRequest.getExchangeRate());
            journalEntry.setAdj(0L);
            journalEntry.setTotalDeposit(makeDepositsRequest.getAmount());
            journalEntry.setIsOrVoucher(makeDepositsRequest.getIsOrVoucher());
            journalEntry.setIsPvVoucher(makeDepositsRequest.getIsPvVoucher());
            journalEntry.setIsApprove(0L);
            journalEntry.setChequeNo(makeDepositsRequest.getChequeNo());
            journalEntry.setNote(makeDepositsRequest.getNote());
            journalEntry.setApplyDepositToId(makeDepositsRequest.getApplyDepositToId());
            journalEntry.setApplyReferenceCode(makeDepositsRequest.getApplyReferenceCode());
            journalEntry.setApplyReferenceName(makeDepositsRequest.getApplyReferenceName());
            journalEntry.setVendorId(makeDepositsRequest.getVendorId());
            journalEntry.setCreatedBy(userId);
            journalEntry.setIsActive(1);

            //! Generate Reference
            if(journalEntry.getIsOrVoucher() == 1) {
                String receivePaymentCode = journalEntryMapper.getReLastReceivePaymentCode();
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                journalEntry.setReference(incrementedString);

            } else if (journalEntry.getIsPvVoucher() == 1){
                String payBillCode = journalEntryMapper.getReLastPayBillCode();
                String incrementedString;

                if(payBillCode != null){
                    incrementedString = incrementNumericPart(payBillCode);
                }else{
                    incrementedString = generateReference("PV");
                }
                journalEntry.setReference(incrementedString);

            } else {
                journalEntry.setReference(makeDepositsRequest.getReference());
            }

            //! Update deposit
            if(makeDepositsRequest.getDepositType() == 1){
                // Purchase Request
                journalEntry.setDepositType(2L);

                Double depositAmount = journalEntryMapper.getPrDepositAmountById(journalEntry.getApplyDepositToId());
                Double totalDeposit = 0D;
                if(depositAmount != null){
                    totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                } else {
                    totalDeposit = journalEntry.getTotalDeposit();
                }

                journalEntryMapper.updatePrDepositAmount(totalDeposit, journalEntry.getApplyDepositToId());

            } else if (makeDepositsRequest.getDepositType() == 2) {
                // Purchase Order
                journalEntry.setDepositType(3L);

                Double depositAmount = journalEntryMapper.getPoDepositAmountById(journalEntry.getApplyDepositToId());

                Double totalDeposit = 0D;
                if(depositAmount != null){
                    totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                } else {
                    totalDeposit = journalEntry.getTotalDeposit();
                }

                journalEntryMapper.updatePoDepositAmount(totalDeposit, journalEntry.getApplyDepositToId());

            } else if (makeDepositsRequest.getDepositType() == 3) {
                // Quotations
                journalEntry.setDepositType(4L);

                Double depositAmount = journalEntryMapper.getQTDepositAmountById(journalEntry.getApplyDepositToId());

                Double totalDeposit = 0D;
                if(depositAmount != null){
                    totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                } else {
                    totalDeposit = journalEntry.getTotalDeposit();
                }

                journalEntryMapper.updateQTDepositAmount(totalDeposit, journalEntry.getApplyDepositToId());

            } else if (makeDepositsRequest.getDepositType() == 4) {
                // Sale order
                journalEntry.setDepositType(5L);

                Double depositAmount = journalEntryMapper.getSODepositAmountById(journalEntry.getApplyDepositToId());

                Double totalDeposit = 0D;
                if(depositAmount != null){
                    totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                } else {
                    totalDeposit = journalEntry.getTotalDeposit();
                }

                journalEntryMapper.updateSODepositAmount(totalDeposit, journalEntry.getApplyDepositToId());
            }

            // !Insert General Ledger
            Boolean insertGeneralLedgers = journalEntryMapper.insert(journalEntry);

            //! Insert General Ledger Detail
            Boolean insertGeneralLedgerDetail = false;

            JournalEntryDetail journalEntryDetailMain = new JournalEntryDetail();

            if(insertGeneralLedgers == true){

                //! Set data for general ledger 1
                journalEntryDetailMain.setGeneralLedgerId(journalEntry.getId());
                journalEntryDetailMain.setChartAccountId(makeDepositsRequest.getChartAccountId());
                journalEntryDetailMain.setCompanyId(makeDepositsRequest.getCompanyId());
                journalEntryDetailMain.setCheque(makeDepositsRequest.getChequeNo());
                journalEntryDetailMain.setType("Deposit");
                journalEntryDetailMain.setDebit(makeDepositsRequest.getAmount());
                journalEntryDetailMain.setCredit(0D);
                journalEntryDetailMain.setTotalDebit(makeDepositsRequest.getAmount());
                journalEntryDetailMain.setMemo(makeDepositsRequest.getNote());
                journalEntryDetailMain.setCustomerId(makeDepositsRequest.getCustomerId());
                journalEntryDetailMain.setVendorId(makeDepositsRequest.getVendorId());
                journalEntryDetailMain.setEmployeeId(makeDepositsRequest.getEmployeeId());
                journalEntryDetailMain.setClassId(makeDepositsRequest.getClassId());
                journalEntryDetailMain.setExchangeRate(makeDepositsRequest.getExchangeRate());
                journalEntryDetailMain.setBranchId(makeDepositsRequest.getBranchId());
                System.out.println(journalEntryDetailMain.getBranchId());
                journalEntryDetailMain.setCreatedBy(userId);

                // !Insert General Ledger Detail Main
                insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetailMain);

                if (makeDepositsRequest.getWriteChecksRequestDetailRequests().size() > 0){
                    List<JournalEntryDetail> journalEntryDetails = new ArrayList<>();
                    Double totalDebit = 0D;
                    for(int i = 0; i < makeDepositsRequest.getWriteChecksRequestDetailRequests().size(); i++){
                        // Set Data of General Ledger Detail
                        JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                        journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                        journalEntryDetail.setChartAccountId(makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getChartAccountId());
                        journalEntryDetail.setCompanyId(journalEntry.getCompanyId());
                        journalEntryDetail.setCheque(journalEntry.getChequeNo());
                        journalEntryDetail.setExchangeRate(journalEntry.getExchangeRate());
                        journalEntryDetail.setDebit(0D);
                        journalEntryDetail.setCredit(makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getAmount());
                        journalEntryDetail.setType("Deposit");
                        journalEntryDetail.setMemo(makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getMemo());
                        journalEntryDetail.setCustomerId(makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getCustomerId());
                        journalEntryDetail.setVendorId(makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getVendorId());
                        journalEntryDetail.setEmployeeId(makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getEmployeeId());
                        journalEntryDetail.setClassId(makeDepositsRequest.getWriteChecksRequestDetailRequests().get(i).getClassId());
                        journalEntryDetail.setBranchId(makeDepositsRequest.getBranchId());
                        journalEntryDetail.setCreatedBy(userId);

                        insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetail);

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

                List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(journalEntryDetailMain.getChartAccountId());
                journalEntryDetailMain.setChartAccountName(chartAccount.get(0).getName());
                journalEntryDetailMain.setChartAccountCode(chartAccount.get(0).getCode());
                journalEntryDetailMain.setDate(journalEntry.getDate());
                journalEntryDetailMain.setReference(journalEntry.getReference());

                if(journalEntry.getIsOrVoucher() == 1) {
                    //! Insert Receive Payment
                    result = journalEntryMapper.insertReceivePayment(journalEntryDetailMain);
                } else if (journalEntry.getIsPvVoucher() == 1){
                    //! Insert Pay Bill
                    result = journalEntryMapper.insertPayBill(journalEntryDetailMain);
                }
            }

            if (result) {
                //! Insert Journal Entry File
                if(makeDepositsRequest.getFile().size() > 0) {
                    for(int i = 0; i < makeDepositsRequest.getFile().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(makeDepositsRequest.getFile().get(i), journalEntry.getId());
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/make-deposits",null,null,"Journal Entry","Journal Entry (make deposits)","(make deposits)",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, journalEntry.getId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/make-deposits",line, error.toString(),"Journal Entry","Journal Entry (make deposits)","(make deposits)",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> updateMakeDeposits(MakeDepositsRequestUpdate requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (make deposits)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Check if credit and debit is equal
            if (requestUpdate.getWriteChecksRequestDetailRequests().size() > 0){
                Double totalDebit = 0D;
                Double totalCredit = requestUpdate.getAmount();

                for(int i = 0; i < requestUpdate.getWriteChecksRequestDetailRequests().size(); i++){
                    // Sum total Debit
                    totalDebit += requestUpdate.getWriteChecksRequestDetailRequests().get(i).getAmount();
                }
                if(!totalDebit.equals(totalCredit)){
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Total amount of debit must be equal to total amount of credit.", false));
                }
            }

            // Check Data
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setId(requestUpdate.getId());
            journalEntry.setDate(requestUpdate.getDate());
            journalEntry.setReference(requestUpdate.getReference());
            journalEntry.setIsRecurrence(requestUpdate.getIsRecurrence());
            journalEntry.setCompanyId(requestUpdate.getCompanyId());
            journalEntry.setBranchId(requestUpdate.getBranchId());
            journalEntry.setExchangeRate(requestUpdate.getExchangeRate());
            journalEntry.setAdj(0L);
            journalEntry.setTotalDeposit(requestUpdate.getAmount());
            journalEntry.setIsOrVoucher(requestUpdate.getIsOrVoucher());
            journalEntry.setIsPvVoucher(requestUpdate.getIsPvVoucher());
            journalEntry.setIsApprove(0L);
            journalEntry.setChequeNo(requestUpdate.getChequeNo());
            journalEntry.setNote(requestUpdate.getNote());

            journalEntry.setApplyDepositToId(requestUpdate.getApplyDepositToId());
            journalEntry.setApplyReferenceCode(requestUpdate.getApplyReferenceCode());
            journalEntry.setApplyReferenceName(requestUpdate.getApplyReferenceName());
            journalEntry.setVendorId(requestUpdate.getVendorId());

            journalEntry.setCreatedBy(userId);
            journalEntry.setIsActive(1);

            //! Generate Reference
            if(journalEntry.getIsOrVoucher() == 1) {
                String receivePaymentCode = journalEntryMapper.getReLastReceivePaymentCode();
                String incrementedString;

                if(receivePaymentCode != null){
                    incrementedString = incrementNumericPart(receivePaymentCode);
                }else{
                    incrementedString = generateReference("OR");
                }
                journalEntry.setReference(incrementedString);

            } else if (journalEntry.getIsPvVoucher() == 1){
                String payBillCode = journalEntryMapper.getReLastPayBillCode();
                String incrementedString;

                if(payBillCode != null){
                    incrementedString = incrementNumericPart(payBillCode);
                }else{
                    incrementedString = generateReference("PV");
                }
                journalEntry.setReference(incrementedString);

            } else {
                journalEntry.setReference(requestUpdate.getReference());
            }

            //! Update deposit
            if(requestUpdate.getDepositType() == 1){
                // Purchase Request
                journalEntry.setDepositType(2L);

                Double depositAmount = journalEntryMapper.getPrDepositAmountById(journalEntry.getApplyDepositToId());
                Double totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                journalEntryMapper.updatePrDepositAmount(totalDeposit, journalEntry.getApplyDepositToId());

            } else if (requestUpdate.getDepositType() == 2) {
                // Purchase Order
                journalEntry.setDepositType(3L);

                Double depositAmount = journalEntryMapper.getPoDepositAmountById(journalEntry.getApplyDepositToId());
                Double totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                journalEntryMapper.updatePoDepositAmount(totalDeposit, journalEntry.getApplyDepositToId());

            } else if (requestUpdate.getDepositType() == 3) {
                // Quotations
                journalEntry.setDepositType(4L);

                Double depositAmount = journalEntryMapper.getQTDepositAmountById(journalEntry.getApplyDepositToId());
                Double totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                journalEntryMapper.updateQTDepositAmount(totalDeposit, journalEntry.getApplyDepositToId());

            } else if (requestUpdate.getDepositType() == 4) {
                // Sale order
                journalEntry.setDepositType(5L);

                Double depositAmount = journalEntryMapper.getSODepositAmountById(journalEntry.getApplyDepositToId());
                Double totalDeposit = depositAmount + journalEntry.getTotalDeposit();
                journalEntryMapper.updateSODepositAmount(totalDeposit, journalEntry.getApplyDepositToId());
            }

            // !Update General Ledger
            Boolean updateGeneralLedger = journalEntryMapper.update(journalEntry);

            //! Insert General Ledger Detail
            Boolean insertGeneralLedgerDetail = false;

            JournalEntryDetail journalEntryDetailMain = new JournalEntryDetail();

            if(updateGeneralLedger == true){

                //! Delete General Ledger Detail
                journalEntryMapper.deleteGeneralLedgerDetail(journalEntry.getId());

                //! Set data for general ledger 1
                journalEntryDetailMain.setGeneralLedgerId(journalEntry.getId());
                journalEntryDetailMain.setChartAccountId(requestUpdate.getChartAccountId());
                journalEntryDetailMain.setCompanyId(requestUpdate.getCompanyId());
                journalEntryDetailMain.setCheque(requestUpdate.getChequeNo());
                journalEntryDetailMain.setType("Deposit");
                journalEntryDetailMain.setDebit(requestUpdate.getAmount());
                journalEntryDetailMain.setCredit(0D);
                journalEntryDetailMain.setTotalDebit(requestUpdate.getAmount());
                journalEntryDetailMain.setMemo(requestUpdate.getNote());
                journalEntryDetailMain.setCustomerId(requestUpdate.getCustomerId());
                journalEntryDetailMain.setVendorId(requestUpdate.getVendorId());
                journalEntryDetailMain.setEmployeeId(requestUpdate.getEmployeeId());
                journalEntryDetailMain.setClassId(requestUpdate.getClassId());
                journalEntryDetailMain.setExchangeRate(requestUpdate.getExchangeRate());
                journalEntryDetailMain.setBranchId(requestUpdate.getBranchId());
                journalEntryDetailMain.setCreatedBy(userId);

                // !Insert General Ledger Detail Main
                insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetailMain);

                if (requestUpdate.getWriteChecksRequestDetailRequests().size() > 0){
                    List<JournalEntryDetail> journalEntryDetails = new ArrayList<>();
                    Double totalDebit = 0D;
                    for(int i = 0; i < requestUpdate.getWriteChecksRequestDetailRequests().size(); i++){
                        // Set Data of General Ledger Detail
                        JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
                        journalEntryDetail.setGeneralLedgerId(journalEntry.getId());
                        journalEntryDetail.setChartAccountId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getChartAccountId());
                        journalEntryDetail.setCompanyId(journalEntry.getCompanyId());
                        journalEntryDetail.setCheque(journalEntry.getChequeNo());
                        journalEntryDetail.setExchangeRate(journalEntry.getExchangeRate());
                        journalEntryDetail.setDebit(0D);
                        journalEntryDetail.setCredit(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getAmount());
                        journalEntryDetail.setType("Deposit");
                        journalEntryDetail.setMemo(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getMemo());
                        journalEntryDetail.setCustomerId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getCustomerId());
                        journalEntryDetail.setVendorId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getVendorId());
                        journalEntryDetail.setEmployeeId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getEmployeeId());
                        journalEntryDetail.setClassId(requestUpdate.getWriteChecksRequestDetailRequests().get(i).getClassId());
                        journalEntryDetail.setBranchId(requestUpdate.getBranchId());
                        journalEntryDetail.setCreatedBy(userId);

                        insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetail);

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

                List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(journalEntryDetailMain.getChartAccountId());
                journalEntryDetailMain.setChartAccountName(chartAccount.get(0).getName());
                journalEntryDetailMain.setChartAccountCode(chartAccount.get(0).getCode());
                journalEntryDetailMain.setDate(journalEntry.getDate());
                journalEntryDetailMain.setReference(journalEntry.getReference());

                if(journalEntry.getIsOrVoucher() == 1) {
                    //! Delete Receive Payment
                    journalEntryMapper.deleteReceivePaymentByGLId(journalEntry.getId(), userId);
                    //! Insert Receive Payment
                    result = journalEntryMapper.insertReceivePayment(journalEntryDetailMain);
                } else if (journalEntry.getIsPvVoucher() == 1){
                    //! Delete Pay Bill
                    journalEntryMapper.deletePayBillByGLId(journalEntry.getId(), userId);
                    //! Insert Receive Payment
                    result = journalEntryMapper.insertPayBill(journalEntryDetailMain);
                }
            }

            if (result) {
                //! Insert Journal Entry File
                if(requestUpdate.getFile().size() > 0) {
                    journalEntryMapper.deleteJournalEntryFile(journalEntry.getId(), userId);
                    for(int i = 0; i < requestUpdate.getFile().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(requestUpdate.getFile().get(i), journalEntry.getId());
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/make-deposits",null,null,"Journal Entry","Journal Entry (make deposits)","(make deposits)",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, journalEntry.getId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/make-deposits",line, error.toString(),"Journal Entry","Journal Entry (make deposits)","(make deposits)",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> findDepositsById(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Journal Entry (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<JournalEntryResponse> responses = journalEntryMapper.getOne(id);

            //! Find OR or PV Voucher
            if(responses.size() > 0){
                String extracted = responses.get(0).getReference().substring(2, 4);
                if(extracted.equals("OR")){
                    List<ReceivePaymentsResponse> receivePaymentResponses = journalEntryMapper.getReLastReceivePaymentByJournalId(id);
                    if(receivePaymentResponses.size() > 0){
                        responses.get(0).setExchangeRate(receivePaymentResponses.get(0).getExchangeRate());
                        responses.get(0).setChequeNo(receivePaymentResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsOrVoucher(1L);
                } else if (extracted.equals("PV")) {
                    List<ReceivePaymentsResponse> payBillResponses = journalEntryMapper.getReLastPayBillByJournalId(id);
                    if(payBillResponses.size() > 0){
                        responses.get(0).setExchangeRate(payBillResponses.get(0).getExchangeRate());
                        responses.get(0).setChequeNo(payBillResponses.get(0).getChequeNumber());
                    }
                    responses.get(0).setIsPvVoucher(1L);
                }
            }

            // Get receive deposit form generate ledger by id
            {
                List<MakeDepositApplyToResponse> makeDepositApplyToResponses = new ArrayList<>();

                //! get deposit info
                if (responses.get(0).getDepositType() == 2) {
                    // Purchase Request
                    makeDepositApplyToResponses = journalEntryMapper.getOnePrDepositById(responses.get(0).getApplyToId());

                } else if (responses.get(0).getDepositType() == 3) {
                    // Purchase Order
                    makeDepositApplyToResponses = journalEntryMapper.getOnePoDepositById(responses.get(0).getApplyToId());

                } else if (responses.get(0).getDepositType() == 4) {
                    // Quotations
                    makeDepositApplyToResponses = journalEntryMapper.getOneQTDepositById(responses.get(0).getApplyToId());

                } else if (responses.get(0).getDepositType() == 5) {
                    // Sale order
                    makeDepositApplyToResponses = journalEntryMapper.getOneSODepositById(responses.get(0).getApplyToId());
                }

                responses.get(0).setMakeDepositApplyToResponses(makeDepositApplyToResponses);
            }

            List<MakeDepositApplyToResponse> depositFrom = journalEntryMapper.getDepositFromByGlId(id);

            if(responses.size() > 0){

                Long journalEntryId = responses.get(0).getId();
                List<JournalEntryDetailResponse> journalEntryDetail = journalEntryMapper.getJournalEntryDetail(journalEntryId);

                responses.get(0).setChartAccountId(journalEntryDetail.get(0).getChartAccountId());
                responses.get(0).setChartAccountName(journalEntryDetail.get(0).getChartAccountName());
                responses.get(0).setClassId(journalEntryDetail.get(0).getClassId());
                responses.get(0).setClassName(journalEntryDetail.get(0).getClassName());
                responses.get(0).setCompanyId(journalEntryDetail.get(0).getCompanyId());
                responses.get(0).setCompanyName(journalEntryDetail.get(0).getCompanyName());
                responses.get(0).setAmount(journalEntryDetail.get(0).getDebit());
                responses.get(0).setClassName(journalEntryDetail.get(0).getClassName());
                responses.get(0).setFile(journalEntryMapper.getJournalEntryFile(journalEntryId));

                // Set Receive deposit from
                journalEntryDetail.get(1).setReceiveDepositFromId(depositFrom.get(0).getId());
                journalEntryDetail.get(1).setReceiveDepositFromName(depositFrom.get(0).getName());

                // Remove the element at index 0
                journalEntryDetail.remove(0);

                // Set data to the response
                responses.get(0).setJournalEntryDetail(journalEntryDetail);

            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/find-deposits/find/{id}",null,null,"Journal Entry","Journal Entry (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/find-deposits/{id}",line, error.toString(),"Journal Entry","Journal Entry (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Journal Entry (edit)") == 0) {
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
            journalEntry.setBranchId(journalEntryUpdateRequest.getBranchId());
            journalEntry.setIsOrVoucher(journalEntryUpdateRequest.getIsOrVoucher());
            journalEntry.setIsPvVoucher(journalEntryUpdateRequest.getIsPvVoucher());
            journalEntry.setReference(journalEntryUpdateRequest.getReference());
            journalEntry.setAdj(journalEntryUpdateRequest.getAdj());
            journalEntry.setExchangeRate(journalEntryUpdateRequest.getExchangeRate());
            journalEntry.setChequeNo(journalEntryUpdateRequest.getChequeNo());
            journalEntry.setNote(journalEntryUpdateRequest.getNote());

            //! Generate Reference
            if(journalEntry.getReference().equals("")){
                if(journalEntry.getIsOrVoucher() == 1) {
                    Long receivePaymentId = journalEntryMapper.getReceivePaymentId(journalEntry.getId());
                    if(receivePaymentId == 0 || journalEntry.getReference().equals("")){
                        String receivePaymentCode = journalEntryMapper.getReLastReceivePaymentCode();
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
                    if(journalEntry.getReference().equals("")){
                        String payBillOldCode = journalEntryMapper.getPayBillId(journalEntry.getId());

                        if(payBillOldCode.equals("") || payBillOldCode == null){
                            System.out.println("Hello");
                            String payBillCode = journalEntryMapper.getReLastPayBillCode();
                            String incrementedString;

                            if(payBillCode != null){
                                incrementedString = incrementNumericPart(payBillCode);
                            }else{
                                incrementedString = generateReference("PV");
                            }
                            journalEntry.setReference(incrementedString);
                        }else {
                            journalEntry.setReference(payBillOldCode);
                        }
                    }
                } else {
                    journalEntry.setReference(journalEntryUpdateRequest.getReference());
                }
            }

            // !Insert General Ledger
            Boolean updateGeneralLedgers = journalEntryMapper.update(journalEntry);
            Boolean insertGeneralLedgerDetail = false;
            if(updateGeneralLedgers == true) {
                String journalEntryType = journalEntryMapper.getJournalEntryType(journalEntry.getId());
                //! Delete General Ledger Detail
                journalEntryMapper.deleteGeneralLedgerDetail(journalEntry.getId());
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
                        journalEntryDetail.setType(journalEntryType);
                        journalEntryDetail.setMemo(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getMemo());
                        journalEntryDetail.setCustomerId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getCustomerId());
                        journalEntryDetail.setVendorId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getVendorId());
                        journalEntryDetail.setEmployeeId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getEmployeeId());
                        journalEntryDetail.setClassId(journalEntryUpdateRequest.getJournalEntryDetailRequests().get(i).getClassId());
                        journalEntryDetail.setBranchId(journalEntryUpdateRequest.getBranchId());
                        journalEntryDetail.setCreatedBy(userId);

                        insertGeneralLedgerDetail = journalEntryMapper.insertGeneralLedgerDetail(journalEntryDetail);

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

                List<ChartAccountDropdownResponse> chartAccount = journalEntryMapper.getAccountChartNameById(journalEntry.getJournalEntryDetails().get(0).getChartAccountId());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountName(chartAccount.get(0).getName());
                journalEntry.getJournalEntryDetails().get(0).setChartAccountCode(chartAccount.get(0).getCode());
                journalEntry.getJournalEntryDetails().get(0).setDate(journalEntry.getDate());
                journalEntry.getJournalEntryDetails().get(0).setReference(journalEntry.getReference());
                if(journalEntry.getIsOrVoucher() != null && journalEntry.getIsOrVoucher() == 1) {
                    System.out.println("Update Recive payment");
//                    Long receivePaymentId = journalEntryMapper.getReceivePaymentId(journalEntry.getId());
                    Boolean isDelete = journalEntryMapper.deleteReceivePaymentByGLId(journalEntry.getId(), userId);
                    //! Insert Receive Payment
                    if(isDelete) {
                        result = journalEntryMapper.insertReceivePayment(journalEntry.getJournalEntryDetails().get(0));
                    }
                }else if (journalEntry.getIsPvVoucher() != null && journalEntry.getIsPvVoucher() == 1){
//                    Long payBillId = journalEntryMapper.getPayBillId(journalEntry.getId());
                    Boolean isDelete = journalEntryMapper.deletePayBillByGLId(journalEntry.getId(), userId);
                    //! Insert Pay Bill
                    if(isDelete){
                        result = journalEntryMapper.insertPayBill(journalEntry.getJournalEntryDetails().get(0));
                    }
                }
            }

            if (result) {
                //! Delete all files
                journalEntryMapper.deleteJournalEntryFile(journalEntry.getId(), userId);

                //! Insert New files
                if(journalEntryUpdateRequest.getFile().size() > 0) {
                    for(int i = 0; i < journalEntryUpdateRequest.getFile().size(); i++){
                        journalEntryMapper.insertJournalEntryFile(journalEntryUpdateRequest.getFile().get(i), journalEntry.getId());
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/update",null,null,"Journal Entry","Journal Entry (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, journalEntry.getId()));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/update",line, error.toString(),"Journal Entry","Journal Entry (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
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

            if (permissionMapper.checkPermission(userId, "Journal Entry (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = journalEntryMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/delete/{id}", null, null, "Journal Entry", "Journal Entry (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/delete/{id}", line, error.toString(), "Journal Entry", "Journal Entry (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> closeRecurrence(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Journal Entry (Close Recurrence)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = journalEntryMapper.closeRecurrence(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/closeRecurrence/{id}", null, null, "Journal Entry", "Journal Entry (Close Recurrence)", "Close Recurrence", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/closeRecurrence/{id}", line, error.toString(), "Journal Entry", "Journal Entry (Close Recurrence)", "Close Recurrence", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> addRecurrence(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Journal Entry (Add Recurrence)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = journalEntryMapper.addRecurrence(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/addRecurrence/{id}", null, null, "Journal Entry", "Journal Entry (Add Recurrence)", "Add Recurrence", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/addRecurrence/{id}", line, error.toString(), "Journal Entry", "Journal Entry (Add Recurrence)", "Add Recurrence", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateStatus(JournalEntryStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Journal Entry (Add Recurrence)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            System.out.println(request);

            Boolean result = journalEntryMapper.updateStatus(request, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/update-status/{id}", null, null, "Journal Entry", "Journal Entry (Update Status)", "Update Status", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/update-status/{id}", line, error.toString(), "Journal Entry", "Journal Entry (Update Status)", "Update Status", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> checkReference(String reference, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            Long result = journalEntryMapper.checkReference(reference);

            if(result > 0){
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Duplicate reference!", false));
            }

            if (true) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/check-reference/{reference}", null, null, "Journal Entry", "Journal Entry (check-reference)", "Update Status", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/check-reference/{reference}", line, error.toString(), "Journal Entry", "Journal Entry (check-reference)", "Update Status", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> getMakeDepositApplyToList(MakeDepositFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Journal Entry (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<MakeDepositApplyToResponse> responses = new ArrayList<>();

            if(filter.getApplyToId() == 1){
                //! Purchase Request
                pagination.setTotal(journalEntryMapper.countPurchaseRequestList(filter));
                responses = journalEntryMapper.getPurchaseRequestList(filter);
            } else if (filter.getApplyToId() == 2) {
                //! Purchase Order
                pagination.setTotal(journalEntryMapper.countPurchaseOrderList(filter));
                responses = journalEntryMapper.getPurchaseOrderList(filter);
            } else if (filter.getApplyToId() == 3) {
                //! Quotations
                pagination.setTotal(journalEntryMapper.countQuotationsList(filter));
                responses = journalEntryMapper.getQuotationsList(filter);
            } else if (filter.getApplyToId() == 4) {
                //! Sale order
                pagination.setTotal(journalEntryMapper.countSaleOrderList(filter));
                responses = journalEntryMapper.getSaleOrderList(filter);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/make-deposit-apply-to-list",null,null,"Journal Entry","Journal Entry (make-deposit-apply-to-list)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/make-deposit-apply-to-list",line, error.toString(),"Journal Entry","Journal Entry (make-deposit-apply-to-list)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> note(JournalEntryNoteRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            Boolean result = journalEntryMapper.note(request);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/journal-entry/note", null, null, "Journal Entry", "Journal Entry (note)", "Note", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/note", line, error.toString(), "Journal Entry", "Journal Entry (note)", "Note", 2, "Error", startDuration, endDuration, httpServletRequest);
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

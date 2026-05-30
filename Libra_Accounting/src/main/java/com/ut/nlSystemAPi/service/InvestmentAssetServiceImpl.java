package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.InvestmentAssetMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.InvestmentAsset;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.PostToJournal;
import com.ut.nlSystemAPi.model.PostToJournalDetails;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InvestmentAssetFilter;

import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetFileRequest;

import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetRequest;
import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.PostToJournal.PostToJournalRequest;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponseDetails;
import com.ut.nlSystemAPi.model.response.InvestmentAsset.InvestmentAssetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvestmentAssetServiceImpl implements InvestmentAssetService {

    @Autowired
    private InvestmentAssetMapper investmentAssetMapper;

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

    public ResponseMessage<BaseResult> getListInvestmentAsset(InvestmentAssetFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
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
            pagination.setTotal(investmentAssetMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            // Get Data
            List<InvestmentAssetResponse> investmentAssetResponses = investmentAssetMapper.getList(filter);

            System.out.println(investmentAssetResponses);

            //! Get Fixed asset file
            if(investmentAssetResponses.size() > 0){
                for(int i = 0; i < investmentAssetResponses.size(); i++){

                    if (investmentAssetResponses.get(i).getIsDepre()){
                        // Find Depreciation amount
                        Double cost = investmentAssetResponses.get(i).getCost();
                        Double businessUsePercentage = investmentAssetResponses.get(i).getBusinessUsePercentage();
                        Double salvageValue = investmentAssetResponses.get(i).getSalvageValue();
                        Long assetLife = investmentAssetResponses.get(i).getAssetLife();
                        LocalDate date1 = LocalDate.parse(investmentAssetResponses.get(i).getDate()); // Assume getDate() returns "YYYY-MM-DD"

                        // Calculate date2 (end date)
                        LocalDate date2 = date1.plusMonths(assetLife);

                        // Calculate total depreciation days
                        long totalDeprDay = ChronoUnit.DAYS.between(date1, date2);

                        Double totalCost = cost * (businessUsePercentage / 100) - salvageValue;
                        Double totalCostPerDay = totalCost / totalDeprDay;

                        if(investmentAssetResponses.get(i).getDeprMethod().equals("SLM")){
                            System.out.println("totalCostPerDay: " + totalCostPerDay);
                            System.out.println("totalCost: " + totalCost);
                            Double depre = totalCostPerDay * investmentAssetResponses.get(i).getDepreciationDayAmount();
                            investmentAssetResponses.get(i).setDepreciation(depre);
                        } else if(investmentAssetResponses.get(i).getDeprMethod().equals("DBM")){
                            Double bookValue = cost;
                            Double accumDepr = 0.0;
                            Double deprRate = (businessUsePercentage / 100) / totalDeprDay;
                            int days = investmentAssetResponses.get(i).getDepreciationDayAmount().intValue();
                            for (int day = 0; day < days; day++) {
                                Double costPerDay = bookValue * deprRate;
                                accumDepr += costPerDay;
                                bookValue -= costPerDay;
                                if (accumDepr > totalCost) {
                                    accumDepr = totalCost;
                                    break;
                                }
                            }
                            investmentAssetResponses.get(i).setDepreciation(accumDepr);
                        } else if (investmentAssetResponses.get(i).getDeprMethod().equals("DDBM")){
                            Double bookValue = cost;
                            Double accumDepr = 0.0;
                            Double deprRate = ((businessUsePercentage / 100) / totalDeprDay) * 2;
                            int days = investmentAssetResponses.get(i).getDepreciationDayAmount().intValue();
                            for (int day = 0; day < days; day++) {
                                Double costPerDay = bookValue * deprRate;
                                accumDepr += costPerDay;
                                bookValue -= costPerDay;
                                if (accumDepr > totalCost) {
                                    accumDepr = totalCost;
                                    break;
                                }
                            }
                            investmentAssetResponses.get(i).setDepreciation(accumDepr);
                        }
                    }
                    investmentAssetResponses.get(i).setFile(investmentAssetMapper.getInvestmentAssetFile(investmentAssetResponses.get(i).getId()));
                }
            }
            
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/investment-asset/list",null,null,"Investment Asset","Investment Asset (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", investmentAssetResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/investment-asset/list",line, error.toString(),"Investment Asset","Investment Asset (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Set Slide
            List<InvestmentAssetResponse> investmentAssetResponses = investmentAssetMapper.getOne(id);
            //! Get Fixed asset file
            if(investmentAssetResponses.size() > 0){
                for(int i = 0; i < investmentAssetResponses.size(); i++){
                    investmentAssetResponses.get(0).setFile(investmentAssetMapper.getInvestmentAssetFile(investmentAssetResponses.get(0).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/investment-asset/find/{id}",null,null,"Investment Asset","Investment Asset (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", investmentAssetResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/investment-asset/find/{id}",line, error.toString(),"Investment Asset","Investment Asset (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(InvestmentAssetRequest investmentAssetRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (investmentAssetMapper.checkDuplicate(investmentAssetRequest.getFixedAssetCode(), investmentAssetRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate fixed asset code", false));
            }

            // Check Data
            InvestmentAsset investmentAsset = new InvestmentAsset();
            investmentAsset.setCompanyId(investmentAssetRequest.getCompanyId());
            investmentAsset.setBranchId(investmentAssetRequest.getBranchId());
            investmentAsset.setLocationId(investmentAssetRequest.getLocationId());
            investmentAsset.setVendorId(investmentAssetRequest.getVendorId());
            investmentAsset.setAssetAccount(investmentAssetRequest.getAssetAccount());
            investmentAsset.setAccumulatedDepartment(investmentAssetRequest.getAccumulatedDepartment());
            investmentAsset.setDeprExpense(investmentAssetRequest.getDeprExpense());
            investmentAsset.setFixedAssetCode(investmentAssetRequest.getFixedAssetCode());
            investmentAsset.setPurchaseOrderNumber(investmentAssetRequest.getPurchaseOrderNumber());
            investmentAsset.setSerialNumber(investmentAssetRequest.getSerialNumber());
            investmentAsset.setDate(investmentAssetRequest.getDate());
            investmentAsset.setDeprMethod(investmentAssetRequest.getDeprMethod());
            investmentAsset.setWarrantyExpires(investmentAssetRequest.getWarrantyExpires());
            investmentAsset.setCost(investmentAssetRequest.getCost());
            investmentAsset.setPhotoName(investmentAssetRequest.getPhotoName());
            investmentAsset.setPhoto(investmentAssetRequest.getPhoto());
            investmentAsset.setName(investmentAssetRequest.getName());
            investmentAsset.setIsDepre(investmentAssetRequest.getIsDepre());
            investmentAsset.setAssetLife(investmentAssetRequest.getAssetLife());
            investmentAsset.setSalvageValue(investmentAssetRequest.getSalvageValue());
            investmentAsset.setBusinessUsePercentage(investmentAssetRequest.getBusinessUsePercentage());
            investmentAsset.setDescription(investmentAssetRequest.getDescription());
            investmentAsset.setCostRemain(investmentAssetRequest.getCostRemain());
            investmentAsset.setIsInUsed(0L);
            investmentAsset.setType(3);
            investmentAsset.setCreatedBy(userId);

            // Insert Chart Account
            Boolean result = investmentAssetMapper.insert(investmentAsset);

            if (result) {

                //! Insert New files
                List<InvestmentAssetFileRequest> files = investmentAssetRequest.getFile();
                if (files != null && !files.isEmpty()) {
                    files.forEach(fileRequest -> {
                        fileRequest.setCreatedBy(userId);
                        fileRequest.setModifiedBy(userId);
                        investmentAssetMapper.insertInvestmentAssetFile(fileRequest, investmentAsset.getId());
                    });
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/investment-asset/add",null,null,"Investment Asset","Investment Asset (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/investment-asset/add",line, error.toString(),"Investment Asset","Investment Asset (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(InvestmentAssetUpdateRequest investmentAssetUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
//            if(investmentAssetMapper.checkDuplicate(investmentAssetUpdateRequest.getAssetCode(), null) > 0){
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate asset code", false));
//            }

            // Check Data
            InvestmentAsset investmentAsset = new InvestmentAsset();
            investmentAsset.setId(investmentAssetUpdateRequest.getId());
            investmentAsset.setCompanyId(investmentAssetUpdateRequest.getCompanyId());
            investmentAsset.setBranchId(investmentAssetUpdateRequest.getBranchId());
            investmentAsset.setLocationId(investmentAssetUpdateRequest.getLocationId());
            investmentAsset.setVendorId(investmentAssetUpdateRequest.getVendorId());
            investmentAsset.setAssetAccount(investmentAssetUpdateRequest.getAssetAccount());
            investmentAsset.setAccumulatedDepartment(investmentAssetUpdateRequest.getAccumulatedDepartment());
            investmentAsset.setDeprExpense(investmentAssetUpdateRequest.getDeprExpense());
            investmentAsset.setDeprMethod(investmentAssetUpdateRequest.getDeprMethod());
            investmentAsset.setFixedAssetCode(investmentAssetUpdateRequest.getFixedAssetCode());
            investmentAsset.setPurchaseOrderNumber(investmentAssetUpdateRequest.getPurchaseOrderNumber());
            investmentAsset.setSerialNumber(investmentAssetUpdateRequest.getSerialNumber());
            investmentAsset.setDate(investmentAssetUpdateRequest.getDate());
            investmentAsset.setWarrantyExpires(investmentAssetUpdateRequest.getWarrantyExpires());
            investmentAsset.setCost(investmentAssetUpdateRequest.getCost());
            investmentAsset.setPhoto(investmentAssetUpdateRequest.getPhoto());
            investmentAsset.setIsDepre(investmentAssetUpdateRequest.getIsDepre());
            investmentAsset.setAssetLife(investmentAssetUpdateRequest.getAssetLife());
            investmentAsset.setSalvageValue(investmentAssetUpdateRequest.getSalvageValue());
            investmentAsset.setBusinessUsePercentage(investmentAssetUpdateRequest.getBusinessUsePercentage());
            investmentAsset.setDescription(investmentAssetUpdateRequest.getDescription());
            investmentAsset.setName(investmentAssetUpdateRequest.getName());
            investmentAsset.setPhotoName(investmentAssetUpdateRequest.getPhotoName());
            investmentAsset.setCostRemain(investmentAssetUpdateRequest.getCostRemain());
            investmentAsset.setIsInUsed(0L);
            investmentAsset.setModifiedBy(userId);

            Boolean result = investmentAssetMapper.update(investmentAsset);

            if (result) {
                //! Remove old files
                investmentAssetMapper.deleteInvestmentAssetFile(investmentAsset.getId(), userId);

                //! Insert New files
                List<InvestmentAssetFileRequest> files = investmentAssetUpdateRequest.getFile();
                if (files != null && !files.isEmpty()) {
                    files.forEach(fileRequest -> {
                        fileRequest.setModifiedBy(userId);
                        investmentAssetMapper.insertInvestmentAssetFile(fileRequest, investmentAsset.getId());
                    });
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/investment-asset/add",null,null,"Investment Asset","Investment Asset (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/investment-asset/add",line, error.toString(),"Investment Asset","Investment Asset (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
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

            if (permissionMapper.checkPermission(userId, "Chart of Account (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = investmentAssetMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/investment-asset/delete/{id}", null, null, "Investment Asset", "Investment Asset (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/investment-asset/delete/{id}", line, error.toString(), "Investment Asset", "Investment Asset (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListPostToJournal(PostToJournalFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
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
//            pagination.setTotal(fixedAssetMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            // Get Data
            List<PostToJournalResponse> postToJournalResponses1 = investmentAssetMapper.getListPostToJournal(filter);
            List<PostToJournalResponse> postToJournalResponses2 = investmentAssetMapper.addListPostToJournal(filter);

            // Combine Lists
            List<PostToJournalResponse> combinedResponses = new ArrayList<>();
            combinedResponses.addAll(postToJournalResponses1);
            combinedResponses.addAll(postToJournalResponses2);

            if (combinedResponses.size() > 0) {
                for (int j = 0; j < combinedResponses.size(); j++) {
                    Double accumDeprAmount = 0D;
                    PostToJournalResponse response = combinedResponses.get(j);
                    response.setLastPostedAmount(investmentAssetMapper.getLastPostedAmount(filter, response.getAccountId()));

                    //! Find the debit and credit
                    if(response.getIsDepreAcc() == 1){
                        response.setDebit(investmentAssetMapper.getCreditAmount(filter, response.getAccountId()));
                    } else {
                        response.setCredit(investmentAssetMapper.getDebitAmount(filter, response.getAccountId()));
                    }

//                    List<PostToJournalResponseDetails> details = fixedAssetMapper.getPostToJournalDetails(filter, response.getAccountId());

                    List<FixedAssetResponse> fixedAssetResponses = investmentAssetMapper.getPostToJournalDetails(filter, response.getAccountId(), response.getIsDepreAcc());

                    System.out.println("fixedAssetResponsdfsdses: " + fixedAssetResponses.size());
                    if(fixedAssetResponses.size() > 0) {
                        for (int i = 0; i < fixedAssetResponses.size(); i++) {
                            if (fixedAssetResponses.get(i).getIsDepre()) {
                                // Find Depreciation amount
                                Double cost = fixedAssetResponses.get(i).getCost();
                                Double businessUsePercentage = fixedAssetResponses.get(i).getBusinessUsePercentage();
                                Double salvageValue = fixedAssetResponses.get(i).getSalvageValue();
                                Long assetLife = fixedAssetResponses.get(i).getAssetLife();
                                LocalDate date1 = LocalDate.parse(fixedAssetResponses.get(i).getDate()); // Assume getDate() returns "YYYY-MM-DD"

                                // Calculate date2 (end date)
                                LocalDate date2 = date1.plusMonths(assetLife);

                                // Calculate total depreciation days
                                long totalDeprDay = ChronoUnit.DAYS.between(date1, date2);

                                // Get accumulated days (requested days)
                                Double accumDay = fixedAssetResponses.get(i).getDepreciationDayAmount();

                                // Make sure accumDay does not exceed totalDeprDay
                                if (accumDay > totalDeprDay) {
                                    accumDay = (double) totalDeprDay;
                                }

                                Double totalCost = cost * (businessUsePercentage / 100) - salvageValue;
                                Double totalCostPerDay = totalCost / totalDeprDay;

                                System.out.println("totalCostPerDay: " + fixedAssetResponses.get(i).getDeprMethod());

                                if(fixedAssetResponses.get(i).getDeprMethod().equals("SLM")){
                                    Double depre = totalCostPerDay * accumDay;
                                    if (response.getIsDepreAcc() == 0) {
                                        depre = depre * -1;
                                    }
                                    if (depre > totalCost) {
                                        depre = totalCost;
                                    }

                                    System.out.println("depre Final: " + depre);
                                    fixedAssetResponses.get(i).setAccumDeprAmount(depre);
                                } else if(fixedAssetResponses.get(i).getDeprMethod().equals("DBM")){
                                    Double bookValue = cost;
                                    Double accumDepr = 0.0;
                                    Double deprRate = (businessUsePercentage / 100) / totalDeprDay;
                                    int days = fixedAssetResponses.get(i).getDepreciationDayAmount().intValue();
                                    for (int day = 0; day < days; day++) {
                                        Double costPerDay = bookValue * deprRate;
                                        accumDepr += costPerDay;
                                        bookValue -= costPerDay;
                                        if (accumDepr > totalCost) {
                                            accumDepr = totalCost;
                                            break;
                                        }
                                    }
                                    if (response.getIsDepreAcc() == 0){
                                        accumDepr = accumDepr * -1;
                                    }
                                    fixedAssetResponses.get(i).setAccumDeprAmount(accumDepr);
                                } else if (fixedAssetResponses.get(i).getDeprMethod().equals("DDBM")){
                                    Double bookValue = cost;
                                    Double accumDepr = 0.0;
                                    Double deprRate = ((businessUsePercentage / 100) / totalDeprDay) * 2;
                                    int days = fixedAssetResponses.get(i).getDepreciationDayAmount().intValue();
                                    for (int day = 0; day < days; day++) {
                                        Double costPerDay = bookValue * deprRate;
                                        accumDepr += costPerDay;
                                        bookValue -= costPerDay;
                                        if (accumDepr > totalCost) {
                                            accumDepr = totalCost;
                                            break;
                                        }
                                    }
                                    if (response.getIsDepreAcc() == 0){
                                        accumDepr = accumDepr * -1;
                                    }
                                    fixedAssetResponses.get(i).setAccumDeprAmount(accumDepr);
                                }
                            }
                            accumDeprAmount += fixedAssetResponses.get(i).getAccumDeprAmount();
                        }
                    }
                    response.setDetails(fixedAssetResponses);
                    combinedResponses.get(j).setAccumDeprAmount(accumDeprAmount);
                }
            }

            //*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset/list",null,null,"Fixed Asset","Fixed Asset (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", combinedResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset/list",line, error.toString(),"Fixed Asset","Fixed Asset (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> savePostToJournal(PostToJournalRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now(); // Initialize start time for activity logging

        try {

            Long userId = userService.getUserAuth().getId();

            // Check Permission
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
//            if (fixedAssetMapper.checkDuplicate(fixedAssetRequest.getFixedAssetCode(), fixedAssetRequest.getId()) > 0) {
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate fixed asset code", false));
//            }

            PostToJournal postToJournal = new PostToJournal();
            postToJournal.setDate(request.getDate());
            postToJournal.setCompanyId(request.getCompanyId());
            postToJournal.setNote(request.getNote());
            postToJournal.setReference(request.getReference());
            postToJournal.setIsApproved(1);
            postToJournal.setIsDepreciated(1);
            postToJournal.setIsAppreciaction(0);
            postToJournal.setIsInvestment(1);
            postToJournal.setIsActive(1);
            postToJournal.setCreatedBy(userId);

            Boolean result = investmentAssetMapper.savePostToJournal(postToJournal);
            Boolean resultDetail = false;
            if (result) {
                for (int i = 0; i< request.getDetails().size(); i++) {
                    PostToJournalDetails postToJournalDetails = new PostToJournalDetails();
                    postToJournalDetails.setGeneralLedgerId(postToJournal.getId());
                    postToJournalDetails.setChartAccountId(request.getDetails().get(i).getChartAccountId());
                    postToJournalDetails.setCompanyId(request.getCompanyId());
                    postToJournalDetails.setBranchId(request.getBranchId());
                    postToJournalDetails.setMemo(request.getDetails().get(i).getMemo());
                    postToJournalDetails.setDebit(request.getDetails().get(i).getDebit());
                    postToJournalDetails.setCredit(request.getDetails().get(i).getCredit());
                    postToJournalDetails.setType("Investment");
                    resultDetail = investmentAssetMapper.saveDetailPostToJournal(postToJournalDetails);
                }
                if (resultDetail) {
                    investmentAssetMapper.updateAsset();
                }

                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/fixed-asset/add", null, null, "Fixed Asset", "Fixed Asset (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now(); // Initialize end time for activity logging
            /* System Activity */
            activityLogService.insert("/fixed-asset/add", null, error.toString(), "Fixed Asset", "Fixed Asset (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
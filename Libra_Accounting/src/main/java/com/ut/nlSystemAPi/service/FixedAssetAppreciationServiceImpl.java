package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.FixedAssetAppreciationMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.FixedAssetAppreciation;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.PostToJournal;
import com.ut.nlSystemAPi.model.PostToJournalDetails;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.FixedAssetAppreciationFilter;
import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetAppreciationRequest.FixedAssetAppreciationFileRequest;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetAppreciationRequest.FixedAssetAppreciationRequest;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetAppreciationRequest.FixedAssetAppreciationUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.PostToJournal.PostToJournalRequest;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponseDetails;
import com.ut.nlSystemAPi.model.response.FixedAssetAppreciation.FixedAssetAppreciationResponse;
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
public class FixedAssetAppreciationServiceImpl implements FixedAssetAppreciationService {

    @Autowired
    private FixedAssetAppreciationMapper fixedAssetAppreciationMapper;

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

    public ResponseMessage<BaseResult> getList(FixedAssetAppreciationFilter fixedAssetAppreciationFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
//            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(fixedAssetAppreciationFilter.getPage());
            pagination.setRowsPerPage(fixedAssetAppreciationFilter.getRowsPerPage());
            pagination.setTotal(fixedAssetAppreciationMapper.countList(fixedAssetAppreciationFilter));
            fixedAssetAppreciationFilter.setPage((fixedAssetAppreciationFilter.getPage() - 1) * fixedAssetAppreciationFilter.getRowsPerPage());

            // Get Data
            List<FixedAssetAppreciationResponse> fixedAssetResponses = fixedAssetAppreciationMapper.getList(fixedAssetAppreciationFilter);

            //! Get Fixed asset file
            if(fixedAssetResponses.size() > 0){
                for(int i = 0; i < fixedAssetResponses.size(); i++){
                    if (fixedAssetResponses.get(i).getIsDepre()){
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

                        Double totalCost = cost * (businessUsePercentage / 100) - salvageValue;
                        Double totalCostPerDay = totalCost / totalDeprDay;

                        if(fixedAssetResponses.get(i).getDeprMethod().equals("SLM")){
                            Double depre = totalCostPerDay * fixedAssetResponses.get(i).getDepreciationDayAmount();
                            fixedAssetResponses.get(i).setDepreciation(depre);
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
                            fixedAssetResponses.get(i).setDepreciation(accumDepr);
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
                            fixedAssetResponses.get(i).setDepreciation(accumDepr);
                        }
                    }
                    fixedAssetResponses.get(i).setFile(fixedAssetAppreciationMapper.getFixedAssetAppreciationFile(fixedAssetResponses.get(i).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset-appreciation/list",null,null,"Fixed Asset Appreciation","Fixed Asset Appreciation (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", fixedAssetResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset-appreciation/list",line, error.toString(),"Fixed Asset Appreciation","Fixed Asset Appreciation (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            List<FixedAssetAppreciationResponse> fixedAssetAppreciationResponse= fixedAssetAppreciationMapper.getOne(id);

            //! Get Fixed asset file
            if(fixedAssetAppreciationResponse.size() > 0){
                for(int i = 0; i < fixedAssetAppreciationResponse.size(); i++){
                    fixedAssetAppreciationResponse.get(0).setFile(fixedAssetAppreciationMapper.getFixedAssetAppreciationFile(fixedAssetAppreciationResponse.get(0).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset-appreciation/find/{id}",null,null,"Fixed Asset Appreciation","Fixed Asset Appreciation (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", fixedAssetAppreciationResponse, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset-appreciation/find/{id}",line, error.toString(),"Fixed Asset Appreciation","Fixed Asset Appreciation (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(FixedAssetAppreciationRequest fixedAssetAppreciationRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now(); // Initialize start time for activity logging

        try {

            Long userId = userService.getUserAuth().getId();

            // Check Permission
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (fixedAssetAppreciationMapper.checkDuplicate(fixedAssetAppreciationRequest.getFixedAssetCode(), fixedAssetAppreciationRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate fixed asset code", false));
            }

            FixedAssetAppreciation fixedAssetAppreciation = new FixedAssetAppreciation();
            fixedAssetAppreciation.setCompanyId(fixedAssetAppreciationRequest.getCompanyId());
            fixedAssetAppreciation.setBranchId(fixedAssetAppreciationRequest.getBranchId());
            fixedAssetAppreciation.setLocationId(fixedAssetAppreciationRequest.getLocationId());
            fixedAssetAppreciation.setVendorId(fixedAssetAppreciationRequest.getVendorId());
            fixedAssetAppreciation.setAssetAccount(fixedAssetAppreciationRequest.getAssetAccount());
            fixedAssetAppreciation.setAccumulatedDepartment(fixedAssetAppreciationRequest.getAccumulatedDepartment());
            fixedAssetAppreciation.setDeprExpense(fixedAssetAppreciationRequest.getDeprExpense());
            fixedAssetAppreciation.setFixedAssetCode(fixedAssetAppreciationRequest.getFixedAssetCode());
            fixedAssetAppreciation.setPurchaseOrderNumber(fixedAssetAppreciationRequest.getPurchaseOrderNumber());
            fixedAssetAppreciation.setSerialNumber(fixedAssetAppreciationRequest.getSerialNumber());
            fixedAssetAppreciation.setDate(fixedAssetAppreciationRequest.getDate());
            fixedAssetAppreciation.setDeprMethod(fixedAssetAppreciationRequest.getDeprMethod());
            fixedAssetAppreciation.setWarrantyExpires(fixedAssetAppreciationRequest.getWarrantyExpires());
            fixedAssetAppreciation.setCost(fixedAssetAppreciationRequest.getCost());
            fixedAssetAppreciation.setPhotoName(fixedAssetAppreciationRequest.getPhotoName());
            fixedAssetAppreciation.setPhoto(fixedAssetAppreciationRequest.getPhoto());
            fixedAssetAppreciation.setName(fixedAssetAppreciationRequest.getName());
            fixedAssetAppreciation.setIsDepre(fixedAssetAppreciationRequest.getIsDepre());
            fixedAssetAppreciation.setAssetLife(fixedAssetAppreciationRequest.getAssetLife());
            fixedAssetAppreciation.setSalvageValue(fixedAssetAppreciationRequest.getSalvageValue());
            fixedAssetAppreciation.setBusinessUsePercentage(fixedAssetAppreciationRequest.getBusinessUsePercentage());
            fixedAssetAppreciation.setDescription(fixedAssetAppreciationRequest.getDescription());
            fixedAssetAppreciation.setCostRemain(fixedAssetAppreciationRequest.getCostRemain());
            fixedAssetAppreciation.setIsInUsed(0L);
            fixedAssetAppreciation.setType(2);
            fixedAssetAppreciation.setCreatedBy(userId);

            Boolean result = fixedAssetAppreciationMapper.insert(fixedAssetAppreciation);

            LocalTime endDuration = LocalTime.now();

            if (result) {
                //! Insert New files
                List<FixedAssetAppreciationFileRequest> files = fixedAssetAppreciationRequest.getFile();
                if (files != null && !files.isEmpty()) {
                    files.forEach(fileRequest -> {
                        fileRequest.setCreatedBy(userId);
                        fileRequest.setModifiedBy(userId);
                        fixedAssetAppreciationMapper.insertFixedAssetAppreciationFile(fileRequest, fixedAssetAppreciation.getId());
                    });
                }
                /* System Activity */
                activityLogService.insert("/fixed-asset-appreciation/add", null, null, "Fixed Asset Appreciation", "Fixed Asset Appreciation (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now(); // Initialize end time for activity logging
            /* System Activity */
            activityLogService.insert("/fixed-asset-appreciation/add", null, error.toString(), "Fixed Asset Appreciation", "Fixed Asset Appreciation (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(FixedAssetAppreciationUpdateRequest fixedAssetAppreciationUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now(); // Initialize start time for activity logging

        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
//            if (fixedAssetMapper.checkDuplicate(fixedAssetUpdateRequest.getFixedAssetCode(), fixedAssetUpdateRequest.getId()) > 0) {
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate fixed asset code", false));
//            }

            FixedAssetAppreciation fixedAssetAppreciation = new FixedAssetAppreciation();
            fixedAssetAppreciation.setId(fixedAssetAppreciationUpdateRequest.getId());
            fixedAssetAppreciation.setCompanyId(fixedAssetAppreciationUpdateRequest.getCompanyId());
            fixedAssetAppreciation.setBranchId(fixedAssetAppreciationUpdateRequest.getBranchId());
            fixedAssetAppreciation.setLocationId(fixedAssetAppreciationUpdateRequest.getLocationId());
            fixedAssetAppreciation.setVendorId(fixedAssetAppreciationUpdateRequest.getVendorId());
            fixedAssetAppreciation.setAssetAccount(fixedAssetAppreciationUpdateRequest.getAssetAccount());
            fixedAssetAppreciation.setAccumulatedDepartment(fixedAssetAppreciationUpdateRequest.getAccumulatedDepartment());
            fixedAssetAppreciation.setDeprExpense(fixedAssetAppreciationUpdateRequest.getDeprExpense());
            fixedAssetAppreciation.setDeprMethod(fixedAssetAppreciationUpdateRequest.getDeprMethod());
            fixedAssetAppreciation.setFixedAssetCode(fixedAssetAppreciationUpdateRequest.getFixedAssetCode());
            fixedAssetAppreciation.setPurchaseOrderNumber(fixedAssetAppreciationUpdateRequest.getPurchaseOrderNumber());
            fixedAssetAppreciation.setSerialNumber(fixedAssetAppreciationUpdateRequest.getSerialNumber());
            fixedAssetAppreciation.setDate(fixedAssetAppreciationUpdateRequest.getDate());
            fixedAssetAppreciation.setWarrantyExpires(fixedAssetAppreciationUpdateRequest.getWarrantyExpires());
            fixedAssetAppreciation.setCost(fixedAssetAppreciationUpdateRequest.getCost());
            fixedAssetAppreciation.setPhoto(fixedAssetAppreciationUpdateRequest.getPhoto());
            fixedAssetAppreciation.setIsDepre(fixedAssetAppreciationUpdateRequest.getIsDepre());
            fixedAssetAppreciation.setAssetLife(fixedAssetAppreciationUpdateRequest.getAssetLife());
            fixedAssetAppreciation.setSalvageValue(fixedAssetAppreciationUpdateRequest.getSalvageValue());
            fixedAssetAppreciation.setBusinessUsePercentage(fixedAssetAppreciationUpdateRequest.getBusinessUsePercentage());
            fixedAssetAppreciation.setDescription(fixedAssetAppreciationUpdateRequest.getDescription());
            fixedAssetAppreciation.setName(fixedAssetAppreciationUpdateRequest.getName());
            fixedAssetAppreciation.setPhotoName(fixedAssetAppreciationUpdateRequest.getPhotoName());
            fixedAssetAppreciation.setCostRemain(fixedAssetAppreciationUpdateRequest.getCostRemain());
            fixedAssetAppreciation.setIsInUsed(0L);
            fixedAssetAppreciation.setModifiedBy(userId);
            // Update Fixed Asset
            Boolean result = fixedAssetAppreciationMapper.update(fixedAssetAppreciation);

            LocalTime endDuration = LocalTime.now(); // Initialize end time for activity logging

            if (result) {
                //! Remove old files
                fixedAssetAppreciationMapper.deleteFixedAssetAppreciationFile(fixedAssetAppreciation.getId(), userId);

                //! Insert New files
                List<FixedAssetAppreciationFileRequest> files = fixedAssetAppreciationUpdateRequest.getFile();
                if (files != null && !files.isEmpty()) {
                    files.forEach(fileRequest -> {
                        fileRequest.setModifiedBy(userId);
                        fixedAssetAppreciationMapper.insertFixedAssetAppreciationFile(fileRequest, fixedAssetAppreciation.getId());
                    });
                }
                /* System Activity */
                activityLogService.insert("/fixed-asset-appreciation/update", null, null, "Fixed Asset Appreciation", "Fixed Asset Appreciation (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now(); // Initialize end time for activity logging
            /* System Activity */
            activityLogService.insert("/fixed-asset-appreciation/update", null, error.toString(), "Fixed Asset Appreciation", "Fixed Asset Appreciation (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            Boolean result = fixedAssetAppreciationMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/fixed-asset-appreciation/delete/{id}", null, null, "Fixed Asset Appreciation", "Fixed Asset Appreciation (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset-appreciation/delete/{id}", line, error.toString(), "Fixed Asset Appreciation", "Fixed Asset Appreciation (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            List<PostToJournalResponse> postToJournalResponses1 = fixedAssetAppreciationMapper.getListPostToJournal(filter);
            List<PostToJournalResponse> postToJournalResponses2 = fixedAssetAppreciationMapper.addListPostToJournal(filter);

            System.out.println("Testing01");
            // Combine Lists
            List<PostToJournalResponse> combinedResponses = new ArrayList<>();
            combinedResponses.addAll(postToJournalResponses1);
            combinedResponses.addAll(postToJournalResponses2);

            if (combinedResponses.size() > 0) {
                for (int j = 0; j < combinedResponses.size(); j++) {
                    Double accumDeprAmount = 0D;
                    PostToJournalResponse response = combinedResponses.get(j);
                    response.setLastPostedAmount(fixedAssetAppreciationMapper.getLastPostedAmount(filter, response.getAccountId()));

                    //! Find the debit and credit
                    if(response.getIsDepreAcc() == 1){
                        response.setDebit(fixedAssetAppreciationMapper.getCreditAmount(filter, response.getAccountId()));
                    } else {
                        response.setCredit(fixedAssetAppreciationMapper.getDebitAmount(filter, response.getAccountId()));
                    }

//                    List<PostToJournalResponseDetails> details = fixedAssetMapper.getPostToJournalDetails(filter, response.getAccountId());

                    List<FixedAssetResponse> fixedAssetResponses = fixedAssetAppreciationMapper.getPostToJournalDetails(filter, response.getAccountId(), response.getIsDepreAcc());

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
            postToJournal.setIsAppreciaction(1);
            postToJournal.setIsActive(1);
            postToJournal.setCreatedBy(userId);

            Boolean result = fixedAssetAppreciationMapper.savePostToJournal(postToJournal);
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
                        postToJournalDetails.setType("Appreciation");
                    resultDetail = fixedAssetAppreciationMapper.saveDetailPostToJournal(postToJournalDetails);
                }
                if (resultDetail) {
                    fixedAssetAppreciationMapper.updateAsset();
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
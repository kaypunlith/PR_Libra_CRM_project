package com.ut.nlSystemAPi.service;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.FixedAssetMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.FixedAssetFilter;
import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetRequest.FixedAssetFileRequest;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetRequest.FixedAssetRequest;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetRequest.FixedAssetUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.PostToJournal.PostToJournalRequest;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class FixedAssetServiceImpl implements FixedAssetService {

    @Autowired
    private FixedAssetMapper fixedAssetMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(FixedAssetFilter fixedAssetFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(fixedAssetFilter.getPage());
            pagination.setRowsPerPage(fixedAssetFilter.getRowsPerPage());
            pagination.setTotal(fixedAssetMapper.countList(fixedAssetFilter));
            fixedAssetFilter.setPage((fixedAssetFilter.getPage() - 1) * fixedAssetFilter.getRowsPerPage());

            // Get Data
            List<FixedAssetResponse> fixedAssetResponses = fixedAssetMapper.getList(fixedAssetFilter);

            System.out.println(fixedAssetResponses);


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
                    fixedAssetResponses.get(i).setFile(fixedAssetMapper.getFixedAssetFile(fixedAssetResponses.get(i).getId()));
                }
            }

            //*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset/list",null,null,"Fixed Asset","Fixed Asset (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", fixedAssetResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset/list",line, error.toString(),"Fixed Asset","Fixed Asset (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            List<FixedAssetResponse> fixedAssetResponses= fixedAssetMapper.getOne(id);

            //! Get Fixed asset file
            if(fixedAssetResponses.size() > 0){
                for(int i = 0; i < fixedAssetResponses.size(); i++){
                    fixedAssetResponses.get(0).setFile(fixedAssetMapper.getFixedAssetFile(fixedAssetResponses.get(0).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset/find/{id}",null,null,"Fixed Asset","Fixed Asset (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", fixedAssetResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset/find/{id}",line, error.toString(),"Fixed Asset","Fixed Asset (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(FixedAssetRequest fixedAssetRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now(); // Initialize start time for activity logging

        try {

            Long userId = userService.getUserAuth().getId();

            // Check Permission
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (fixedAssetMapper.checkDuplicate(fixedAssetRequest.getFixedAssetCode(), fixedAssetRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate fixed asset code", false));
            }

            FixedAsset fixedAsset = new FixedAsset();
            fixedAsset.setCompanyId(fixedAssetRequest.getCompanyId());
            fixedAsset.setBranchId(fixedAssetRequest.getBranchId());
            fixedAsset.setLocationId(fixedAssetRequest.getLocationId());
            fixedAsset.setVendorId(fixedAssetRequest.getVendorId());
            fixedAsset.setAssetAccount(fixedAssetRequest.getAssetAccount());
            fixedAsset.setAccumulatedDepartment(fixedAssetRequest.getAccumulatedDepartment());
            fixedAsset.setDeprExpense(fixedAssetRequest.getDeprExpense());
            fixedAsset.setFixedAssetCode(fixedAssetRequest.getFixedAssetCode());
            fixedAsset.setPurchaseOrderNumber(fixedAssetRequest.getPurchaseOrderNumber());
            fixedAsset.setSerialNumber(fixedAssetRequest.getSerialNumber());
            fixedAsset.setDate(fixedAssetRequest.getDate());
            fixedAsset.setDeprMethod(fixedAssetRequest.getDeprMethod());
            fixedAsset.setWarrantyExpires(fixedAssetRequest.getWarrantyExpires());
            fixedAsset.setCost(fixedAssetRequest.getCost());
            fixedAsset.setPhotoName(fixedAssetRequest.getPhotoName());
            fixedAsset.setPhoto(fixedAssetRequest.getPhoto());
            fixedAsset.setName(fixedAssetRequest.getName());
            fixedAsset.setIsDepre(fixedAssetRequest.getIsDepre());
            fixedAsset.setAssetLife(fixedAssetRequest.getAssetLife());
            fixedAsset.setSalvageValue(fixedAssetRequest.getSalvageValue());
            fixedAsset.setBusinessUsePercentage(fixedAssetRequest.getBusinessUsePercentage());
            fixedAsset.setDescription(fixedAssetRequest.getDescription());
            fixedAsset.setCostRemain(fixedAssetRequest.getCostRemain());
            fixedAsset.setIsInUsed(0L);
            fixedAsset.setCreatedBy(userId);

            Boolean result = fixedAssetMapper.insert(fixedAsset);

            LocalTime endDuration = LocalTime.now();

            if (result) {
                //! Insert New files
                List<FixedAssetFileRequest> files = fixedAssetRequest.getFile();
                if (files != null && !files.isEmpty()) {
                    files.forEach(fileRequest -> {
                        fileRequest.setCreatedBy(userId);
                        fileRequest.setModifiedBy(userId);
                        fixedAssetMapper.insertFixedAssetFile(fileRequest, fixedAsset.getId());
                    });
                }
                /* System Activity */
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

    @Override
    public ResponseMessage<BaseResult> update(FixedAssetUpdateRequest fixedAssetUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
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

            FixedAsset fixedAsset = new FixedAsset();
            fixedAsset.setId(fixedAssetUpdateRequest.getId());
            fixedAsset.setCompanyId(fixedAssetUpdateRequest.getCompanyId());
            fixedAsset.setBranchId(fixedAssetUpdateRequest.getBranchId());
            fixedAsset.setLocationId(fixedAssetUpdateRequest.getLocationId());
            fixedAsset.setVendorId(fixedAssetUpdateRequest.getVendorId());
            fixedAsset.setAssetAccount(fixedAssetUpdateRequest.getAssetAccount());
            fixedAsset.setAccumulatedDepartment(fixedAssetUpdateRequest.getAccumulatedDepartment());
            fixedAsset.setDeprExpense(fixedAssetUpdateRequest.getDeprExpense());
            fixedAsset.setDeprMethod(fixedAssetUpdateRequest.getDeprMethod());
            fixedAsset.setFixedAssetCode(fixedAssetUpdateRequest.getFixedAssetCode());
            fixedAsset.setPurchaseOrderNumber(fixedAssetUpdateRequest.getPurchaseOrderNumber());
            fixedAsset.setSerialNumber(fixedAssetUpdateRequest.getSerialNumber());
            fixedAsset.setDate(fixedAssetUpdateRequest.getDate());
            fixedAsset.setWarrantyExpires(fixedAssetUpdateRequest.getWarrantyExpires());
            fixedAsset.setCost(fixedAssetUpdateRequest.getCost());
            fixedAsset.setPhoto(fixedAssetUpdateRequest.getPhoto());
            fixedAsset.setIsDepre(fixedAssetUpdateRequest.getIsDepre());
            fixedAsset.setAssetLife(fixedAssetUpdateRequest.getAssetLife());
            fixedAsset.setSalvageValue(fixedAssetUpdateRequest.getSalvageValue());
            fixedAsset.setBusinessUsePercentage(fixedAssetUpdateRequest.getBusinessUsePercentage());
            fixedAsset.setDescription(fixedAssetUpdateRequest.getDescription());
            fixedAsset.setName(fixedAssetUpdateRequest.getName());
            fixedAsset.setPhotoName(fixedAssetUpdateRequest.getPhotoName());
            fixedAsset.setCostRemain(fixedAssetUpdateRequest.getCostRemain());
            fixedAsset.setIsInUsed(0L);
            fixedAsset.setModifiedBy(userId);
            // Update Fixed Asset
            Boolean result = fixedAssetMapper.update(fixedAsset);

            if (result) {
                //! Remove old files
                fixedAssetMapper.deleteFixedAssetFile(fixedAsset.getId(), userId);
                //! Insert New files
                List<FixedAssetFileRequest> files = fixedAssetUpdateRequest.getFile();
                if (files != null && !files.isEmpty()) {
                    files.forEach(fileRequest -> {
                        fileRequest.setModifiedBy(userId);
                        fixedAssetMapper.insertFixedAssetFile(fileRequest, fixedAsset.getId());
                    });
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/fixed-asset/update", null, null, "Fixed Asset", "Fixed Asset (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now(); // Initialize end time for activity logging
            /* System Activity */
            activityLogService.insert("/fixed-asset/update", null, error.toString(), "Fixed Asset", "Fixed Asset (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            Boolean result = fixedAssetMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/fixed-asset/delete/{id}", null, null, "Fixed Asset", "Fixed Asset (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/fixed-asset/delete/{id}", line, error.toString(), "Fixed Asset", "Fixed Asset (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            System.out.println("filter: " + filter);

            // Get Data
            List<PostToJournalResponse> postToJournalResponses1 = fixedAssetMapper.getListPostToJournal(filter);
            List<PostToJournalResponse> postToJournalResponses2 = fixedAssetMapper.addListPostToJournal(filter);

            // Combine Lists
            List<PostToJournalResponse> combinedResponses = new ArrayList<>();
            combinedResponses.addAll(postToJournalResponses1);
            combinedResponses.addAll(postToJournalResponses2);


            if (combinedResponses.size() > 0) {
                for (int j = 0; j < combinedResponses.size(); j++) {
                    Double accumDeprAmount = 0D;
                    PostToJournalResponse response = combinedResponses.get(j);
                    response.setLastPostedAmount(fixedAssetMapper.getLastPostedAmount(filter, response.getAccountId()));

                    //! Find the debit and credit
                    if(response.getIsDepreAcc() == 1){
                        response.setDebit(fixedAssetMapper.getCreditAmount(filter, response.getAccountId()));
                    } else {
                        response.setCredit(fixedAssetMapper.getDebitAmount(filter, response.getAccountId()));
                    }

//                    List<PostToJournalResponseDetails> details = fixedAssetMapper.getPostToJournalDetails(filter, response.getAccountId());

                    List<FixedAssetResponse> fixedAssetResponses = fixedAssetMapper.getPostToJournalDetails(filter, response.getAccountId(), response.getIsDepreAcc());

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
            postToJournal.setIsActive(1);
            postToJournal.setCreatedBy(userId);

            Boolean result = fixedAssetMapper.savePostToJournal(postToJournal);
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
                    postToJournalDetails.setType("Depreciation");
                    resultDetail = fixedAssetMapper.saveDetailPostToJournal(postToJournalDetails);
                }
                if (resultDetail) {
                    fixedAssetMapper.updateAsset();
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
package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ChartOfAccountMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.ChartOfAccount;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountUpdateRequest;
import com.ut.nlSystemAPi.model.response.ChartOfAccount.ChartOfAccountResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ChartOfAccountServiceImpl implements ChartOfAccountService {

    @Autowired
    private ChartOfAccountMapper chartOfAccountMapper;

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

    public ResponseMessage<BaseResult> getListChartOfAccount(ChartAccountFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
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
            pagination.setTotal(chartOfAccountMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            // Get Data
            List<ChartOfAccountResponse> chartOfAccountResponses = chartOfAccountMapper.getList(filter);

//            if(chartOfAccountResponses.size() > 0){
//                for (int i = 0; i <chartOfAccountResponses.size(); i++){
//                    filter.setParentId(chartOfAccountResponses.get(i).getId());
//                    List<ChartOfAccountResponse> sub1 = chartOfAccountMapper.getList(filter);
//                    //! Sub 2
//                    if(sub1.size() > 0){
//                        for ( int j = 0; j < sub1.size(); j++ ){
//                            filter.setParentId(sub1.get(j).getId());
//                            List<ChartOfAccountResponse> sub2 = chartOfAccountMapper.getList(filter);
//                            //! Sub 3
//                            if(sub2.size() > 0){
//                                for (int k = 0; k < sub2.size(); k++){
//                                    filter.setParentId(sub2.get(k).getId());
//                                    List<ChartOfAccountResponse> sub3 = chartOfAccountMapper.getList(filter);
//                                    sub2.get(k).setSubAccount(sub3);
//                                }
//                            }
//                            sub1.get(j).setSubAccount(sub2);
//                        }
//                    }
//                    chartOfAccountResponses.get(i).setSubAccount(sub1);
//                }
//            }

            if (chartOfAccountResponses != null && chartOfAccountResponses.size() > 0) {
//                for (int i = 0; i < chartOfAccountResponses.size(); i++){
//                    // Get Companies
//                    chartOfAccountResponses.get(i).setBranchResponses(chartOfAccountMapper.getBranchByAccountChartId(chartOfAccountResponses.get(i).getId()));;
//                }

                for (int i = 0; i < chartOfAccountResponses.size(); i++){
                    // Get Companies
                    chartOfAccountResponses.get(i).setCompanyResponses(chartOfAccountMapper.getCompanyByAccountChartId(chartOfAccountResponses.get(i).getId()));
                    //Get balance
                    Double chartAccountBalance = chartOfAccountMapper.getChartAccountBalance(chartOfAccountResponses.get(i).getId());
                    chartOfAccountResponses.get(i).setBalance(chartAccountBalance);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/list",null,null,"Chart Of Account","Chart Of Account (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartOfAccountResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/list",line, error.toString(),"Chart Of Account","Chart Of Account (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> listBySelect(ChartAccountFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
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
            pagination.setTotal(chartOfAccountMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            //! Get Data
            List<ChartOfAccountResponse> chartOfAccountResponses = chartOfAccountMapper.getListData(filter, null);

            //! Sub 1
            if(chartOfAccountResponses.size() > 0){
                for (int i = 0; i <chartOfAccountResponses.size(); i++){
                    List<ChartOfAccountResponse> sub1 = chartOfAccountMapper.getListData(filter, chartOfAccountResponses.get(i).getId());
                    //! Sub 2
                    if(sub1.size() > 0){
                        for ( int j = 0; j < sub1.size(); j++ ){
                            List<ChartOfAccountResponse> sub2 = chartOfAccountMapper.getListData(filter, sub1.get(j).getId());
                            //! Sub 3
                            if(sub2.size() > 0){
                                for (int k = 0; k < sub2.size(); k++){
                                    List<ChartOfAccountResponse> sub3 = chartOfAccountMapper.getListData(filter, sub2.get(k).getId());
                                    sub2.get(k).setSubAccount(sub3);
                                }
                            }
                            sub1.get(j).setSubAccount(sub2);
                        }
                    }
                    chartOfAccountResponses.get(i).setSubAccount(sub1);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/list",null,null,"Chart Of Account","Chart Of Account (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartOfAccountResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/list",line, error.toString(),"Chart Of Account","Chart Of Account (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            List<ChartOfAccountResponse> chartOfAccountResponses= chartOfAccountMapper.getOne(id);


            if (chartOfAccountResponses != null && chartOfAccountResponses.size() > 0) {
//                for (int i = 0; i < chartOfAccountResponses.size(); i++){
//                    // Get branch
//                    chartOfAccountResponses.get(i).setBranchResponses(chartOfAccountMapper.getBranchByAccountChartId(chartOfAccountResponses.get(i).getId()));;
//                }
                //Get companies

                for (int i = 0; i < chartOfAccountResponses.size(); i++){
                    List<CompanyChartAccountResponse> companyChartAccountResponses= chartOfAccountMapper.getCompanyByAccountChartId(chartOfAccountResponses.get(i).getId());
                    chartOfAccountResponses.get(i).setCompanyResponses(companyChartAccountResponses);
                }

            }



            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/find/{id}",null,null,"Chart Of Account","Chart Of Account (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartOfAccountResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/find/{id}",line, error.toString(),"Chart Of Account","Chart Of Account (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ChartOfAccountRequest chartOfAccountRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            System.out.println(chartOfAccountRequest);
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(chartOfAccountMapper.checkDuplicate(chartOfAccountRequest.getAccountCodes(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account code", false));
            }
            System.out.println("Test");

            // Check Data
            ChartOfAccount chartOfAccount = new ChartOfAccount();
            chartOfAccount.setParentId(chartOfAccountRequest.getParentId());
            chartOfAccount.setChartAccountTypeId(chartOfAccountRequest.getChartAccountTypeId());
            chartOfAccount.setChartAccountGroupId(chartOfAccountRequest.getChartAccountGroupId());
            chartOfAccount.setApplyBranchType(1L);
            chartOfAccount.setAccountCodes(chartOfAccountRequest.getAccountCodes());
            chartOfAccount.setAccountDescription(chartOfAccountRequest.getAccountDescription());
            chartOfAccount.setManual(chartOfAccountRequest.getManual());
            chartOfAccount.setCreatedBy(userId);
            chartOfAccount.setIsActive(1);
            // Insert Chart Account

            System.out.println(chartOfAccount);
            Boolean result = chartOfAccountMapper.insert(chartOfAccount);

            if (result) {

                    chartOfAccountMapper.insertBranchId(chartOfAccount.getId(), chartOfAccountRequest.getBranchId());

                // Insert Chart Account Company
                for (int i = 0; i < chartOfAccountRequest.getCompanyId().size(); i++){
                    chartOfAccountMapper.insertCompanyId(chartOfAccount.getId(), chartOfAccountRequest.getCompanyId().get(i));
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-of-account/add",null,null,"Chart Of Account","Chart Of Account (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/add",line, error.toString(),"Chart Of Account","Chart Of Account (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(ChartOfAccountUpdateRequest chartOfAccountUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(chartOfAccountMapper.checkDuplicate(chartOfAccountUpdateRequest.getAccountCodes(), chartOfAccountUpdateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account code", false));
            }

            // Check parent id
            if(Objects.equals(chartOfAccountUpdateRequest.getId(), chartOfAccountUpdateRequest.getParentId())){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Can't use self chart of account as parent.", false));
            }

            // Check Data
            ChartOfAccount chartOfAccount = new ChartOfAccount();
            chartOfAccount.setId(chartOfAccountUpdateRequest.getId());
            chartOfAccount.setParentId(chartOfAccountUpdateRequest.getParentId());
            chartOfAccount.setChartAccountTypeId(chartOfAccountUpdateRequest.getChartAccountTypeId());
            chartOfAccount.setChartAccountGroupId(chartOfAccountUpdateRequest.getChartAccountGroupId());
            chartOfAccount.setAccountCodes(chartOfAccountUpdateRequest.getAccountCodes());
            chartOfAccount.setAccountDescription(chartOfAccountUpdateRequest.getAccountDescription());
            chartOfAccount.setManual(chartOfAccountUpdateRequest.getManual());

            chartOfAccount.setModifiedBy(userId);
            chartOfAccount.setIsActive(1);

            // Insert Chart Account
            Boolean result = chartOfAccountMapper.update(chartOfAccount);

            if (result) {
                // Delete Chart Account branch
                chartOfAccountMapper.deleteChartAccountBranch(chartOfAccount.getId());

                // Insert Chart Account branch
                    chartOfAccountMapper.insertBranchId(chartOfAccount.getId(), chartOfAccountUpdateRequest.getBranchId());

                // Delete Chart Account Company
                chartOfAccountMapper.deleteChartAccountCompany(chartOfAccount.getId());

                // Insert Chart Account Company
                for (int i = 0; i < chartOfAccountUpdateRequest.getCompanyId().size(); i++){
                    chartOfAccountMapper.insertCompanyId(chartOfAccount.getId(), chartOfAccountUpdateRequest.getCompanyId().get(i));
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-of-account/update",null,null,"Chart Of Account","Chart Of Account (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/update",line, error.toString(),"Chart Of Account","Chart Of Account (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
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

            Boolean result = chartOfAccountMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-of-account/delete/{id}", null, null, "Chart Of Account", "Chart Of Account (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/delete/{id}", line, error.toString(), "Chart Of Account", "Chart Of Account (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> updateStatus(Long id, Long statusId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Chart of Account (change status)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = chartOfAccountMapper.updateStatus(id, statusId, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-of-account/update-status/{id}", null, null, "Chart Of Account", "Chart Of Account (change status)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-of-account/update-status/{id}", line, error.toString(), "Chart Of Account", "Chart Of Account (change status)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
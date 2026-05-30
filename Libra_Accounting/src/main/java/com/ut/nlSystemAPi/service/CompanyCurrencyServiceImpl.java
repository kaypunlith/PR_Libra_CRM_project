package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.CompanyCurrencyMapper;
import com.ut.nlSystemAPi.mapper.primary.CurrencyMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.CompanyCurrency;
import com.ut.nlSystemAPi.model.Currency;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.CompanyCurrencyFilter;
import com.ut.nlSystemAPi.model.request.Login.CompnayCurrency.CompanyCurrencyRequest;
import com.ut.nlSystemAPi.model.request.Login.CompnayCurrency.CompanyCurrencyUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.Currency.CurrencyRequest;
import com.ut.nlSystemAPi.model.request.Login.Currency.CurrencyUpdateRequest;
import com.ut.nlSystemAPi.model.response.CompanyCurrency.CompanyCurrencyResponse;
import com.ut.nlSystemAPi.model.response.Currency.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class CompanyCurrencyServiceImpl implements CompanyCurrencyService {

    @Autowired
    private CompanyCurrencyMapper companyCurrencyMapper;

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

    public ResponseMessage<BaseResult> getList(CompanyCurrencyFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Company Currency (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(companyCurrencyMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CompanyCurrencyResponse> companyCurrencyResponses = companyCurrencyMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/list",null,null,"Company Currency","Company Currency (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", companyCurrencyResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/list",line, error.toString(),"Company Currency","Company Currency (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
//            if (permissionMapper.checkPermission(userId, "Company Currency (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<CompanyCurrencyResponse> companyCurrencyResponses= companyCurrencyMapper.getOne(id);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/find/{id}",null,null,"Company Currency","Company Currency (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", companyCurrencyResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/find/{id}",line, error.toString(),"Company Currency","Company Currency (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(CompanyCurrencyRequest companyCurrencyRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Company Currency (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            // Check Duplicate
            if(companyCurrencyMapper.checkDuplicate(companyCurrencyRequest.getCompanyId(), companyCurrencyRequest.getCurrencyId(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate currency.", false));
            }

            // Check Data
            CompanyCurrency companyCurrency = new CompanyCurrency();

            companyCurrency.setCompanyId(companyCurrencyRequest.getCompanyId());
            companyCurrency.setCurrencyFromId(companyCurrencyRequest.getCurrencyId());
            companyCurrency.setBranchId(companyCurrencyRequest.getBranchId());
            companyCurrency.setCreatedBy(userId);
            companyCurrency.setIsActive(1);

            // Insert
            Boolean result = companyCurrencyMapper.insert(companyCurrency);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/company-currency/add",null,null,"Company Currency","Company Currency (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/add",line, error.toString(),"Company Currency","Company Currency (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(CompanyCurrencyUpdateRequest companyCurrencyUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Company Currency (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            // Check Duplicate
            if(companyCurrencyMapper.checkDuplicate(companyCurrencyUpdateRequest.getCompanyId(), companyCurrencyUpdateRequest.getCurrencyId(), companyCurrencyUpdateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate currency.", false));
            }

            // Check Data
            CompanyCurrency companyCurrency = new CompanyCurrency();

            companyCurrency.setId(companyCurrencyUpdateRequest.getId());
            companyCurrency.setCompanyId(companyCurrencyUpdateRequest.getCompanyId());
            companyCurrency.setCurrencyFromId(companyCurrencyUpdateRequest.getCurrencyId());
            companyCurrency.setBranchId(companyCurrencyUpdateRequest.getBranchId());

            companyCurrency.setModifiedBy(userId);

            // Insert Chart Account
            Boolean result = companyCurrencyMapper.update(companyCurrency);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/company-currency/update",null,null,"Company Currency","Company Currency (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/update",line, error.toString(),"Company Currency","Company Currency (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
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

            if (permissionMapper.checkPermission(userId, "Company Currency (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = companyCurrencyMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/company-currency/delete/{id}", null, null, "Company Currency", "Company Currency (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/delete/{id}", line, error.toString(), "Company Currency", "Company Currency (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> applyPos(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Company Currency (Apply To POS)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // reset apply pos
            Boolean applyPos = companyCurrencyMapper.resetPos();

            Boolean result = false;
            if(applyPos){
                result = companyCurrencyMapper.applyPos(id, userId);
            }

            System.out.println(result);

            if (result) {

                Long companyId = companyCurrencyMapper.getCompanyIdByCompanyCurrencyId(id);

                // Update company pos
                companyCurrencyMapper.updateCompanyPos(id, companyId, userId);

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/company-currency/apply-pos/{id}", null, null, "Company Currency", "Company Currency (Apply To POS)", "Apply To POS", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company-currency/apply-pos/{id}", line, error.toString(), "Company Currency", "Company Currency (Apply To POS)", "Apply To POS", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
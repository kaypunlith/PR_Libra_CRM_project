package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.CompanyMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.Company;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.request.Login.Company.CompanyRequest;
import com.ut.nlSystemAPi.model.request.Login.Company.CompanyUpdateRequest;
import com.ut.nlSystemAPi.model.response.Company.CompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(companyMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CompanyResponse> companyResponses = companyMapper.getList(filter);
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/list", null, null, "company", " company (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", companyResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/list", 1033L, error.toString(), "company", "company (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<CompanyResponse> companyResponses = companyMapper.getOne(id);

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/find/" + id, null, null, "company", "company (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", companyResponses, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/find/" + id, 1033L, error.toString(), "company", "company (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(CompanyRequest companyRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            // Check for duplicate name
            if (companyMapper.checkDuplicate(companyRequest.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate company  Name", false));
            }

            Company company = new Company();
            company.setName(companyRequest.getName());
            company.setPhoto(companyRequest.getPhoto());
            company.setVatNo(companyRequest.getVatNo());
            company.setFaxNumber(companyRequest.getFax());
            company.setBaseCurrencyId(companyRequest.getBaseCurrencyId());
            company.setOfficeTelephone(companyRequest.getOfficeTelephone());
            company.setOtherTelephone(companyRequest.getOtherTelephone());
            company.setEmail(companyRequest.getEmail());
            company.setAddress(companyRequest.getAddress());
            company.setAddressInKhmer(companyRequest.getAddressInKhmer());
            company.setIsActive(1);
            company.setCreatedBy(userId);

            boolean result = companyMapper.insert(company);
            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/company/add", null, null, "company", "company (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/add", 1033L, error.toString(), "VAT Setting", "VAT Setting (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(CompanyUpdateRequest companyUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
//            // Check Duplicate name
            if (companyMapper.checkDuplicate(companyUpdateRequest.getName(), companyUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name of  company", false));
            }
            Company company = new Company();
            company.setId(companyUpdateRequest.getId());
            company.setPhoto(companyUpdateRequest.getPhoto());
            company.setVatNo(companyUpdateRequest.getVatNo());
            company.setName(companyUpdateRequest.getName());
            company.setFaxNumber(companyUpdateRequest.getFax());
            company.setBaseCurrencyId(companyUpdateRequest.getBaseCurrencyId());
            company.setOfficeTelephone(companyUpdateRequest.getOfficeTelephone());
            company.setOtherTelephone(companyUpdateRequest.getOtherTelephone());
            company.setEmail(companyUpdateRequest.getEmail());
            company.setAddress(companyUpdateRequest.getAddress());
            company.setAddressInKhmer(companyUpdateRequest.getAddressInKhmer());
            System.out.println(company);
            company.setIsActive(1);
            boolean result = companyMapper.update(company);
            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                 activityLogService.insert("/company/update", null, null, "company", "company (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
            } else {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/company/update", 1033L, "Failed to update company", "company", "company (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail to update company", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/update", 1033L, error.toString(), "company", "company (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error occurred during update", false));
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = companyMapper.delete(id, userId);
            LocalTime endDuration = LocalTime.now();

            // System Activity
            activityLogService.insert("/company/delete/{id}", null, null, "company", "company (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);

            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (RuntimeException error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/delete/{id}", 1033L, error.toString(), "company", "company (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}

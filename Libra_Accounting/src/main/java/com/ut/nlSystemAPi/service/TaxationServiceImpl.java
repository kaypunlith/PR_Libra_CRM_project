package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.TaxationMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Taxation;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TaxationFilter;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationExchangeRateRequest;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationRequest;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.ModuleTypeResponse;
import com.ut.nlSystemAPi.model.response.Taxation.TaxationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class TaxationServiceImpl implements TaxationService {

    @Autowired
    private TaxationMapper taxationMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(TaxationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(taxationMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<TaxationResponse> taxationResponses = taxationMapper.getList(filter);

            if (taxationResponses != null && !taxationResponses.isEmpty()) {
                for (TaxationResponse taxationResponse : taxationResponses) {
                    // Retrieve and set module types
                    List<ModuleTypeResponse> moduleTypes = taxationMapper.getModuleTypeByModuleId(taxationResponse.getId());
                    taxationResponse.setModuleTypes(moduleTypes);
                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/list", null, null, "VAT Setting", "VAT Setting (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", taxationResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/list", 1033L, error.toString(), "VAT Setting", "VAT Setting (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<TaxationResponse> taxationResponses = taxationMapper.getOne(id);

            if (taxationResponses != null && !taxationResponses.isEmpty()) {
                for (TaxationResponse taxationResponse : taxationResponses) {
                    List<ModuleTypeResponse> moduleTypes = taxationMapper.getModuleTypeByModuleId(taxationResponse.getId());
                    taxationResponse.setModuleTypes(moduleTypes);
                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/find/" + id, null, null, "VAT Setting", "VAT Setting (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", taxationResponses, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/find/" + id, 1033L, error.toString(), "VAT Setting", "VAT Setting (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(TaxationRequest taxationRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "VAT Setting (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check for duplicate name
            if (taxationMapper.checkDuplicate(taxationRequest.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Taxation Name", false));
            }

            // Create a new Taxation object and set properties
            Taxation taxation = new Taxation();
            taxation.setCompanyId(taxationRequest.getCompanyId());
            taxation.setTypeId(taxationRequest.getTypeId());
            taxation.setBranchId(taxationRequest.getBranchId());
            taxation.setName(taxationRequest.getName());
            taxation.setPercentage(taxationRequest.getPercentage());
            taxation.setChartOfAccountId(taxationRequest.getChartOfAccountId());
            taxation.setRateForTax(taxationRequest.getRateForTax());
            taxation.setCreatedBy(userId);
            taxation.setIsActive(1);

            // Insert into table `vat_settings`
            boolean result = taxationMapper.insert(taxation);

            if (result) {
                // Insert into table `vat_setting_rates`
                boolean insertPercentage = taxationMapper.insertVatSettingRate(taxation.getId(), taxationRequest.getRateForTax(), taxation);

                if(!insertPercentage) {
                    LocalTime endDuration = LocalTime.now();
                    activityLogService.insert("/taxation/add", 146L, "Failed to insert into vat_setting_rates", "VAT Setting", "VAT Setting (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Failed to insert into vat_setting_rates", false));
                }

                // Insert into table `vat_modules`
                boolean insertModules = true;
                for (Long moduleTypeId : taxationRequest.getModuleTypeId()) {
                    if (!taxationMapper.insertVatModule(taxation.getId(), moduleTypeId, taxation)) {
                        insertModules = false;
                        break;
                    }
                }

                if (!insertModules) {
                    LocalTime endDuration = LocalTime.now();
                    activityLogService.insert("/taxation/add", 161L, "Failed to insert into vat_modules", "VAT Setting", "VAT Setting (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Failed to insert into vat_modules", false));
                }

                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/taxation/add", null, null, "VAT Setting", "VAT Setting (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
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
    public ResponseMessage<BaseResult> update(TaxationUpdateRequest taxationUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "VAT Setting (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Duplicate name
            if (taxationMapper.checkDuplicate(taxationUpdateRequest.getName(), taxationUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name of Taxation", false));
            }

            // Create new Taxation object and set properties
            Taxation taxation = new Taxation();
            taxation.setId(taxationUpdateRequest.getId());
            taxation.setCompanyId(taxationUpdateRequest.getCompanyId());
            taxation.setBranchId(taxationUpdateRequest.getBranchId());
            taxation.setTypeId(taxationUpdateRequest.getTypeId());
            taxation.setName(taxationUpdateRequest.getName());
            taxation.setPercentage(taxationUpdateRequest.getPercentage());
            taxation.setChartOfAccountId(taxationUpdateRequest.getChartOfAccountId());
            taxation.setRateForTax(taxationUpdateRequest.getRateForTax());
            taxation.setModifiedBy(userId);


            // Deactivate previous data in vat_setting_rates
            boolean deactivateVatSettingRates = taxationMapper.deactivatePreviousVatSettingRate(taxation.getId(), taxation.getModifiedBy());
            // Deactivate previous data in vat_modules
            boolean deactivateVatModules = taxationMapper.deactivatePreviousVatModules(taxation.getId());

            // Update vat_setting with new data
            boolean result = taxationMapper.update(taxation);
            if (result && deactivateVatSettingRates && deactivateVatModules) {
                boolean moduleInsertSuccess = true;
                boolean rateInsertSuccess = true;
                // Insert module type into vat_modules
                for (Long moduleTypeId : taxationUpdateRequest.getModuleTypeId()) {
                    if (!taxationMapper.insertVatModule(taxation.getId(), moduleTypeId, taxation)) {
                        moduleInsertSuccess = false;
                        break; // Exit loop if insertion fails
                    }
                }
                // Insert rate value into vat_setting_rates
                if (moduleInsertSuccess) {
                    if (!taxationMapper.insertVatSettingRate(taxation.getId(), taxation.getRateForTax(), taxation)) {
                        rateInsertSuccess = false;
                    }
                }
                // Check if both insertions were successful
                if (moduleInsertSuccess && rateInsertSuccess) {
                    // System Activity
                    LocalTime endDuration = LocalTime.now();
                    activityLogService.insert("/taxation/update", null, null, "VAT Setting", "VAT Setting (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
                } else {
                    LocalTime endDuration = LocalTime.now();
                    activityLogService.insert("/taxation/update", 1033L, "Failed to insert vat_setting_rates or vat_modules", "VAT Setting", "VAT Setting (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Fail to insert vat_setting_rates or vat_modules", false));
                }
            } else {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/taxation/update", 1033L, "Failed to update vat_settings or deactivate previous settings", "VAT Setting", "VAT Setting (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail to update vat_settings or deactivate previous settings", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/update", 1033L, error.toString(), "VAT Setting", "VAT Setting (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error occurred during update", false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "VAT Setting (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = taxationMapper.delete(id, userId);
            LocalTime endDuration = LocalTime.now();

            // System Activity
            activityLogService.insert("/taxation/delete/{id}", null, null, "VAT Setting", "VAT Setting (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);

            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (RuntimeException error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/delete/{id}", 1033L, error.toString(), "VAT Setting", "VAT Setting (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateExchangeRate(TaxationExchangeRateRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "VAT Setting (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (request == null || request.getExchangeRateAmount() == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("amount is required.", false));
            }
            boolean result = false;
            List<Long> vatSales = taxationMapper.getVatSettingTypeSales();
            if (vatSales != null) {
                Taxation taxation = new Taxation();
                taxation.setCreatedBy(userId);
                for (Long vatSettingId : vatSales) {
                    taxationMapper.deactivatePreviousVatSettingRate(vatSettingId, userId);
                    taxationMapper.insertVatSettingRate(vatSettingId, request.getExchangeRateAmount(), taxation);
                }
                result = true;
            }
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/taxation/update-exchange-rate", null, null, "VAT Setting", "VAT Setting (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/taxation/update-exchange-rate", 1033L, error.toString(), "VAT Setting", "VAT Setting (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}

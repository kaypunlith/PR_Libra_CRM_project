package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.Telegram.CheckNull;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.entity.SalesTarget.SalesTarget;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.SalesTarget.SalesTargetEmployee;
import com.ut.nlSystemAPi.model.request.SalesTarget.SalesTargetEmployeeRequest;
import com.ut.nlSystemAPi.model.request.SalesTarget.SalesTargetRequest;
import com.ut.nlSystemAPi.model.request.SalesTarget.SalesTargetUpdateRequest;
import com.ut.nlSystemAPi.model.response.Competitor.CompetitorResponse;
import com.ut.nlSystemAPi.model.response.SalesTarget.SalesTargetEmployeeResponse;
import com.ut.nlSystemAPi.model.response.SalesTarget.SalesTargetResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.SalesTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class SalesTargetServiceImpl implements SalesTargetService {

    @Autowired
    private SalesTargetMapper salesTargetMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private HelperMapper helperMapper;

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1035L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Target (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Sales Target (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesTargetMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesTargetResponse> responses = salesTargetMapper.getList(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/list", null, null, "Sales Target", "Sales Target (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/list", line, error.toString(), "Sales Target", "Sales Target (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1035L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Target (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SalesTargetResponse> responses = salesTargetMapper.getOne(id);

            if (!responses.isEmpty()) {
                responses.get(0).setMarkets(salesTargetMapper.getCustomerMarkets(id));
                responses.get(0).setMonths(salesTargetMapper.getDetails(id));
                responses.get(0).setTargetEmployee(salesTargetMapper.getEmployeeTarget(id));
                if (responses.get(0).getTargetEmployee() != null && !responses.get(0).getTargetEmployee().isEmpty()) {
                    for (SalesTargetEmployeeResponse employee : responses.get(0).getTargetEmployee()) {
                        employee.setOrganizations(salesTargetMapper.getEmployeeTargetOrganization(employee.getId()));
                    }
                }

            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/find/{id}", null, null, "Sales Target", "Sales Target (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/find/{id}", line, error.toString(), "Sales Target", "Sales Target (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(SalesTargetRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1035L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Target (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            SalesTarget salesTarget = new SalesTarget();
            salesTarget.setCompanyId(request.getCompanyId());
            salesTarget.setEmployeeId(request.getEmployeeId());
            salesTarget.setTargetName(request.getTargetName());
            salesTarget.setTargetAmount(request.getTargetAmount());
            salesTarget.setCurrencyId(request.getCurrencyId());
            salesTarget.setOrganizationTarget(request.getOrganizationTarget());
            salesTarget.setIndicatorFirst(request.getIndicatorFirst());
            salesTarget.setIndicatorSecond(request.getIndicatorSecond());
            salesTarget.setIndicatorThird(request.getIndicatorThird());
            salesTarget.setPeriodId(request.getPeriodId());
            salesTarget.setCreatedBy(userId);
            Boolean result = salesTargetMapper.insert(salesTarget);

            if (result) {

                String code = generateCode.generateAutoCode("sale_targets", "sale_target_code", 7, "ST", true, "is_active = 1");
                helperMapper.updateCode("sale_targets", "sale_target_code", code, salesTarget.getId());

                if (request.getMonths() != null) {
                    for (Long monthId : request.getMonths()) {
                        salesTargetMapper.insertDetails(salesTarget.getId(), monthId, userId);
                    }
                }

                if (request.getMarketIds() != null) {
                    for (Long marketId : request.getMarketIds()) {
                        salesTargetMapper.insertCustomerMarket(salesTarget.getId(), marketId, userId);
                    }
                }

                if (request.getEmployeeTarget() != null) {
                    SalesTargetEmployee salesTargetEmployee = new SalesTargetEmployee();
                    for (SalesTargetEmployeeRequest employeeTarget : request.getEmployeeTarget()) {
                        salesTargetEmployee.setSaleTargetId(salesTarget.getId());
                        salesTargetEmployee.setEmployeeId(employeeTarget.getEmployeeId());
                        salesTargetEmployee.setOrganizationTargetAmountExisting(employeeTarget.getOrganizationTargetAmountExisting());
                        salesTargetEmployee.setTargetAmountExisting(employeeTarget.getTargetAmountExisting());
                        salesTargetEmployee.setOrganizationTargetAmountNew(employeeTarget.getOrganizationTargetAmountNew());
                        salesTargetEmployee.setTargetAmountNew(employeeTarget.getTargetAmountNew());
                        salesTargetEmployee.setCreatedBy(userId);
                        salesTargetMapper.insertEmployeeTarget(salesTargetEmployee);

                        if (employeeTarget.getOrganizations() != null) {
                            for (Long organizationId : employeeTarget.getOrganizations()) {
                                salesTargetMapper.insertEmployeeTargetOrganization(salesTargetEmployee.getId(), organizationId, userId);
                            }
                        }
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-target/add", null, null, "Sales Target", "Sales Target (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/add", line, error.toString(), "Sales Target", "Sales Target (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(SalesTargetUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1035L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Target (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            SalesTarget salesTarget = new SalesTarget();
            salesTarget.setCompanyId(request.getCompanyId());
            salesTarget.setEmployeeId(request.getEmployeeId());
            salesTarget.setTargetName(request.getTargetName());
            salesTarget.setTargetAmount(request.getTargetAmount());
            salesTarget.setCurrencyId(request.getCurrencyId());
            salesTarget.setOrganizationTarget(request.getOrganizationTarget());
            salesTarget.setIndicatorFirst(request.getIndicatorFirst());
            salesTarget.setIndicatorSecond(request.getIndicatorSecond());
            salesTarget.setIndicatorThird(request.getIndicatorThird());
            salesTarget.setPeriodId(request.getPeriodId());
            salesTarget.setCreatedBy(userId);
            Boolean result = salesTargetMapper.insert(salesTarget);

            if (result) {

                helperMapper.archive("sale_targets", "is_active", 2, request.getId(), userId);

                // Get the reference code
                String code = helperMapper.getCurrentCode("sale_targets", "sale_target_code", request.getId());
                helperMapper.updateCode("sale_targets", "sale_target_code", code, salesTarget.getId());

                if (request.getMonths() != null) {
                    salesTargetMapper.deleteDetails(2, request.getId(), userId);
                    System.out.println(salesTargetMapper.deleteDetails(2, request.getId(), userId));
                    for (Long monthId : request.getMonths()) {
                        salesTargetMapper.insertDetails(salesTarget.getId(), monthId, userId);
                    }
                }

                if (request.getMarketIds() != null) {
                    salesTargetMapper.deleteCustomerMarkets(2, request.getId(), userId);
                    for (Long marketId : request.getMarketIds()) {
                        salesTargetMapper.insertCustomerMarket(salesTarget.getId(), marketId, userId);
                    }
                }

                if (request.getEmployeeTarget() != null) {
                    List<Long> employeeTargetIds = salesTargetMapper.getEmployeeTargetId(request.getId());
                    salesTargetMapper.deleteEmployeeTarget(2, request.getId(), userId);
                    SalesTargetEmployee salesTargetEmployee = new SalesTargetEmployee();
                    for (SalesTargetEmployeeRequest employeeTarget : request.getEmployeeTarget()) {
                        salesTargetEmployee.setSaleTargetId(salesTarget.getId());
                        salesTargetEmployee.setEmployeeId(employeeTarget.getEmployeeId());
                        salesTargetEmployee.setOrganizationTargetAmountExisting(employeeTarget.getOrganizationTargetAmountExisting());
                        salesTargetEmployee.setTargetAmountExisting(employeeTarget.getTargetAmountExisting());
                        salesTargetEmployee.setOrganizationTargetAmountNew(employeeTarget.getOrganizationTargetAmountNew());
                        salesTargetEmployee.setTargetAmountNew(employeeTarget.getTargetAmountNew());
                        salesTargetEmployee.setCreatedBy(userId);
                        salesTargetMapper.insertEmployeeTarget(salesTargetEmployee);

                        if (employeeTarget.getOrganizations() != null) {
                            if (employeeTargetIds != null){
                                for (Long employeeTargetId : employeeTargetIds) {
                                    salesTargetMapper.deleteEmployeeTargetOrganization(2, employeeTargetId, userId);
                                }
                            }
                            for (Long organizationId : employeeTarget.getOrganizations()) {
                                salesTargetMapper.insertEmployeeTargetOrganization(salesTargetEmployee.getId(), organizationId, userId);
                            }
                        }
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-target/update", null, null, "Sales Target", "Sales Target (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/update", line, error.toString(), "Sales Target", "Sales Target (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1035L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Target (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = salesTargetMapper.delete(id, userId);

            if (result) {
                salesTargetMapper.deleteDetails(0, id, userId);
                salesTargetMapper.deleteCustomerMarkets(0, id, userId);
                List<Long> employeeTargetIds = salesTargetMapper.getEmployeeTargetId(id);
                salesTargetMapper.deleteEmployeeTarget(0, id, userId);
                if (employeeTargetIds != null){
                    for (Long employeeTargetId : employeeTargetIds) {
                        salesTargetMapper.deleteEmployeeTargetOrganization(0, employeeTargetId, userId);
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-target/delete/{id}", null, null, "Sales Target", "Sales Target (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/delete/{id}", line, error.toString(), "Sales Target", "Sales Target (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> approve(UpdateStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (request.getStatus() == 1) {
                if (permissionMapper.checkPermission(userId, "Sales Target (Approve)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Sales Target (Disapprove)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            Boolean result = helperMapper.updateStatus("sale_targets", "is_approve", request.getStatus(), userId, request.getId());

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-target/approve", null, null, "Sales Target", "Sales Target (Approve)", "Approve", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/approve", line, error.toString(), "Sales Target", "Sales Target (Approve)", "Approve", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> close(UpdateStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (request.getStatus() == 1) {
                if (permissionMapper.checkPermission(userId, "Sales Target (Close)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Sales Target (Open)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            Boolean result = helperMapper.updateStatus("sale_targets", "is_close", request.getStatus(), userId, request.getId());

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-target/close", null, null, "Sales Target", "Sales Target (Close)", "Close", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/close", line, error.toString(), "Sales Target", "Sales Target (Close)", "Close", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getEmployeeTarget(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1035L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Target (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SalesTargetEmployeeResponse> responses = salesTargetMapper.getEmployeeTarget(id);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/list", null, null, "Sales Target", "Sales Target (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target/list", line, error.toString(), "Sales Target", "Sales Target (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}

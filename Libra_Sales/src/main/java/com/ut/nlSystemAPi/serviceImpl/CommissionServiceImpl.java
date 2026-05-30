package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.CommissionMapper;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Commission.Commission;
import com.ut.nlSystemAPi.model.entity.Commission.CommissionDetail;
import com.ut.nlSystemAPi.model.entity.Commission.CommissionEmployee;
import com.ut.nlSystemAPi.model.filter.CommissionFilter;
import com.ut.nlSystemAPi.model.request.Commission.CommissionDetailRequest;
import com.ut.nlSystemAPi.model.request.Commission.CommissionRequest;
import com.ut.nlSystemAPi.model.request.Commission.CommissionUpdateRequest;
import com.ut.nlSystemAPi.model.response.Commission.CommissionResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.CommissionService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class CommissionServiceImpl implements CommissionService {

    @Autowired
    private CommissionMapper commissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

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

    @Override
    public ResponseMessage<BaseResult> getList(CommissionFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Commission (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(commissionMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CommissionResponse> responses = commissionMapper.getList(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commission/list", null, null, "Commission", "Commission (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commission/list", line, error.toString(), "Commission", "Commission (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Commission (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<CommissionResponse> responses = commissionMapper.getOne(id);

            if (!responses.isEmpty()) {
                responses.get(0).setDetails(commissionMapper.getDetails(id));
                responses.get(0).setEmployees(commissionMapper.getEmployees(id));
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commission/find/{id}", null, null, "Commission", "Commission (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commission/find/{id}", line, error.toString(), "Commission", "Commission (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(CommissionRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Commission (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            Commission commission = new Commission();
            commission.setLocationGroupId(request.getWarehouseId());
            commission.setDescription(request.getDescription());
            commission.setStartDate(request.getStartDate());
            commission.setEndDate(request.getEndDate());
            commission.setNote(request.getNote());
            commission.setType(request.getType());
            commission.setRecurring(request.getRecurring());
            commission.setTarget(request.getTarget());
            commission.setAmount(request.getAmount());
            commission.setPercent(request.getPercent());
            commission.setCreatedBy(userId);
            if (request.getWarehouseId() == null){
                commission.setLocationGroupApply(0);
            } else {
                commission.setLocationGroupApply(1);
            }
            Boolean result = commissionMapper.insert(commission);

            if (result) {
                String code = generateCode.generateAutoCode("commissions", "code", 7, "", true, "status = 1");
                helperMapper.updateCode("commissions", "code", code, commission.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    CommissionDetail detail = new CommissionDetail();
                    for (CommissionDetailRequest detailRequest : request.getDetails()) {
                        detail.setCommissionId(commission.getId());
                        detail.setProductId(detailRequest.getProductId());
                        detail.setQty(detailRequest.getQty());
                        detail.setUomId(detailRequest.getUomId());
                        detail.setAmount(detailRequest.getAmount());
                        detail.setPercent(detailRequest.getPercent());
                        detail.setUnitPrice(detailRequest.getUnitPrice());
                        commissionMapper.insertDetail(detail);
                    }
                }

                if (request.getEmployees() != null && !request.getEmployees().isEmpty()) {
                    CommissionEmployee employee = new CommissionEmployee();
                    for (Long employeeId : request.getEmployees()) {
                        employee.setCommissionId(commission.getId());
                        employee.setEmployeeId(employeeId);
                        commissionMapper.insertEmployee(employee);
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/commission/add", null, null, "Commission", "Commission (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commission/add", line, error.toString(), "Commission", "Commission (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(CommissionUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Commission (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (helperMapper.checkDataExisting("commissions", request.getId()) == 0) {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Data Cannot Be Found.", false));
            }

            // Check Data
            Commission commission = new Commission();
            commission.setId(request.getId());
            commission.setLocationGroupId(request.getWarehouseId());
            commission.setDescription(request.getDescription());
            commission.setStartDate(request.getStartDate());
            commission.setEndDate(request.getEndDate());
            commission.setNote(request.getNote());
            commission.setType(request.getType());
            commission.setRecurring(request.getRecurring());
            commission.setTarget(request.getTarget());
            commission.setAmount(request.getAmount());
            commission.setPercent(request.getPercent());
            commission.setModifiedBy(userId);
            if (request.getWarehouseId() == null){
                commission.setLocationGroupApply(0);
            } else {
                commission.setLocationGroupApply(1);
            }
            Boolean result = commissionMapper.update(commission);

            if (result) {

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    commissionMapper.deleteDetails(request.getId());
                    CommissionDetail detail = new CommissionDetail();
                    for (CommissionDetailRequest detailRequest : request.getDetails()) {
                        detail.setCommissionId(request.getId());
                        detail.setProductId(detailRequest.getProductId());
                        detail.setQty(detailRequest.getQty());
                        detail.setUomId(detailRequest.getUomId());
                        detail.setAmount(detailRequest.getAmount());
                        detail.setPercent(detailRequest.getPercent());
                        detail.setUnitPrice(detailRequest.getUnitPrice());
                        commissionMapper.insertDetail(detail);
                    }
                }

                if (request.getEmployees() != null && !request.getEmployees().isEmpty()) {
                    commissionMapper.deleteEmployees(request.getId());
                    CommissionEmployee employee = new CommissionEmployee();
                    for (Long employeeId : request.getEmployees()) {
                        employee.setCommissionId(request.getId());
                        employee.setEmployeeId(employeeId);
                        commissionMapper.insertEmployee(employee);
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/commission/update", null, null, "Commission", "Commission (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/commission/update", line, error.toString(), "Commission", "Commission (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Commission (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = helperMapper.archive("commissions", "status", 2, id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/commission/delete/{id}", null, null, "Commission", "Commission (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commission/delete/{id}", line, error.toString(), "Commission", "Commission (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }
}

package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.StaffLoanMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.StaffLoan;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.StaffLoan.StaffLoanRequest;
import com.ut.nlSystemAPi.model.request.StaffLoan.StaffLoanUpdateStatusRequest;
import com.ut.nlSystemAPi.model.request.StaffLoan.StaffLoanUpdateRequest;
import com.ut.nlSystemAPi.model.response.StaffLoan.StaffLoanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class StaffLoanServiceImpl implements StaffLoanService {

    @Autowired
    private StaffLoanMapper staffLoanMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Staff Loan (View)") == 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal((long) staffLoanMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<StaffLoanResponse> responses = staffLoanMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/list", null, null, "Staff Loan", "Staff Loan (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/list", line, error.toString(), "Staff Loan", "Staff Loan (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Staff Loan (View)") == 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
            }

            List<StaffLoanResponse> responses = staffLoanMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/find/{id}", null, null, "Staff Loan", "Staff Loan (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/find/{id}", line, error.toString(), "Staff Loan", "Staff Loan (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(StaffLoanRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Staff Loan (Add)") == 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
            }

            StaffLoan staffLoan = new StaffLoan();
            staffLoan.setEmployeeId(request.getEmployeeId());
            staffLoan.setPositionId(request.getPositionId());
            staffLoan.setPaybackPeriod(request.getPaybackPeriod() == null ? 0 : request.getPaybackPeriod());
            staffLoan.setAmount(request.getAmount());
            staffLoan.setCreatedBy(userId);

            Boolean result = staffLoanMapper.insert(staffLoan);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/staff-loan/add", null, null, "Staff Loan", "Staff Loan (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/add", line, error.toString(), "Staff Loan", "Staff Loan (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(StaffLoanUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Staff Loan (Edit)") == 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
            }

            StaffLoan staffLoan = new StaffLoan();
            staffLoan.setId(request.getId());
            staffLoan.setEmployeeId(request.getEmployeeId());
            staffLoan.setPositionId(request.getPositionId());
            staffLoan.setPaybackPeriod(request.getPaybackPeriod());
            staffLoan.setAmount(request.getAmount());
            staffLoan.setModifiedBy(userId);

            Boolean result = staffLoanMapper.update(staffLoan);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/staff-loan/update", null, null, "Staff Loan", "Staff Loan (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/update", line, error.toString(), "Staff Loan", "Staff Loan (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> updateStatus(StaffLoanUpdateStatusRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Staff Loan (Edit)") == 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
            }

            StaffLoan staffLoan = new StaffLoan();
            staffLoan.setId(request.getId());
            if (request.getStatus() != null) {
                staffLoan.setStatus(request.getStatus());
            }
            staffLoan.setModifiedBy(userId);

            Boolean result = staffLoanMapper.updateStatus(staffLoan);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/staff-loan/update-status", null, null, "Staff Loan", "Staff Loan (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/update-status", line, error.toString(), "Staff Loan", "Staff Loan (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Staff Loan (Delete)") == 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
            }

            Boolean result = staffLoanMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/staff-loan/delete/{id}", null, null, "Staff Loan", "Staff Loan (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/staff-loan/delete/{id}", line, error.toString(), "Staff Loan", "Staff Loan (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}

package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.VoucherMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Voucher.Voucher;
import com.ut.nlSystemAPi.model.filter.VoucherFilter;
import com.ut.nlSystemAPi.model.request.Voucher.VoucherRequest;
import com.ut.nlSystemAPi.model.request.Voucher.VoucherUpdateRequest;
import com.ut.nlSystemAPi.model.response.Voucher.VoucherResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(VoucherFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Voucher (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(voucherMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<VoucherResponse> responses = voucherMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/voucher/list", null, null, "Voucher", "Voucher (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/voucher/list", line, error.toString(), "Voucher", "Voucher (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Voucher (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<VoucherResponse> responses = voucherMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/voucher/find/{id}", null, null, "Voucher", "Voucher (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/voucher/find/{id}", line, error.toString(), "Voucher", "Voucher (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(VoucherRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Voucher (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (request.getVoucherType() == null || (request.getVoucherType() != 1 && request.getVoucherType() != 2)) {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Invalid Voucher Type", false));
            }

            if (voucherMapper.checkDuplicate(request.getVoucherCode(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Voucher Code", false));
            }

            Voucher voucher = normalizeVoucher(request);
            voucher.setCreatedBy(userId);
            voucher.setStatus(1);

            Boolean result = voucherMapper.insert(voucher);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/voucher/add", null, null, "Voucher", "Voucher (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/voucher/add", line, error.toString(), "Voucher", "Voucher (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(VoucherUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Voucher (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (request.getVoucherType() == null || (request.getVoucherType() != 1 && request.getVoucherType() != 2)) {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Invalid Voucher Type", false));
            }

            List<VoucherResponse> existing = voucherMapper.getOne(request.getId());
            if (existing.isEmpty()) {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Data Cannot Be Found.", false));
            }

            if (voucherMapper.checkDuplicate(request.getVoucherCode(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Voucher Code", false));
            }

            Voucher voucher = normalizeVoucher(request);
            voucher.setId(request.getId());
            voucher.setModifiedBy(userId);

            Boolean result = voucherMapper.update(voucher);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/voucher/update", null, null, "Voucher", "Voucher (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/voucher/update", line, error.toString(), "Voucher", "Voucher (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Voucher (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = voucherMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/voucher/delete/{id}", null, null, "Voucher", "Voucher (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/voucher/delete/{id}", line, error.toString(), "Voucher", "Voucher (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    private Voucher normalizeVoucher(VoucherRequest request) {
        Voucher voucher = new Voucher();
        voucher.setBranchId(request.getBranchId());
        voucher.setVoucherCode(request.getVoucherCode());
        voucher.setDescription(request.getDescription());
        voucher.setDate(request.getDate());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        voucher.setUserType(request.getUserType() == null ? 0 : request.getUserType());
        voucher.setVoucherType(request.getVoucherType());
        voucher.setNote(request.getNote());
        if (request.getVoucherType() == 1) {
            voucher.setDiscountAmount(request.getDiscountAmount());
            voucher.setDiscountPercent(null);
        } else {
            voucher.setDiscountAmount(null);
            voucher.setDiscountPercent(request.getDiscountPercent());
        }
        return voucher;
    }
}

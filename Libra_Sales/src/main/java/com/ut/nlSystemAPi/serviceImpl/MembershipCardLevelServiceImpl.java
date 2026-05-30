package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.MembershipCardLevelMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.MembershipCardLevel.MembershipCardLevel;
import com.ut.nlSystemAPi.model.request.MembershipCardLevel.MembershipCardLevelRequest;
import com.ut.nlSystemAPi.model.request.MembershipCardLevel.MembershipCardLevelUpdateRequest;
import com.ut.nlSystemAPi.model.response.MembershipCardLevel.MembershipCardLevelResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.MembershipCardLevelService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class MembershipCardLevelServiceImpl implements MembershipCardLevelService {

    @Autowired
    private MembershipCardLevelMapper membershipCardLevelMapper;

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
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(membershipCardLevelMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<MembershipCardLevelResponse> responses = membershipCardLevelMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card-level/list", null, null, "Membership Card Level", "Membership Card Level (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card-level/list", line, error.toString(), "Membership Card Level", "Membership Card Level (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card Level (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<MembershipCardLevelResponse> responses = membershipCardLevelMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card-level/find/{id}", null, null, "Membership Card Level", "Membership Card Level (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card-level/find/{id}", line, error.toString(), "Membership Card Level", "Membership Card Level (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(MembershipCardLevelRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card Level (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            MembershipCardLevel membershipCardLevel = new MembershipCardLevel();
            membershipCardLevel.setName(request.getName());
            membershipCardLevel.setDiscountPercent(request.getDiscountPercent());
            membershipCardLevel.setBdDiscountPercent(request.getBdDiscountPercent());
            membershipCardLevel.setAmount(request.getAmount());
            membershipCardLevel.setPoint(request.getPoint());
            membershipCardLevel.setRequiredPoint(request.getRequiredPoint());
            membershipCardLevel.setMemo(request.getMemo());
            membershipCardLevel.setColor(request.getColor());
            membershipCardLevel.setCreatedBy(userId);
            membershipCardLevel.setIsActive(1);

            Boolean result = membershipCardLevelMapper.insert(membershipCardLevel);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/membership-card-level/add", null, null, "Membership Card Level", "Membership Card Level (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card-level/add", line, error.toString(), "Membership Card Level", "Membership Card Level (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(MembershipCardLevelUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card Level (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            MembershipCardLevel membershipCardLevel = new MembershipCardLevel();
            membershipCardLevel.setId(request.getId());
            membershipCardLevel.setName(request.getName());
            membershipCardLevel.setDiscountPercent(request.getDiscountPercent());
            membershipCardLevel.setBdDiscountPercent(request.getBdDiscountPercent());
            membershipCardLevel.setAmount(request.getAmount());
            membershipCardLevel.setPoint(request.getPoint());
            membershipCardLevel.setRequiredPoint(request.getRequiredPoint());
            membershipCardLevel.setMemo(request.getMemo());
            membershipCardLevel.setColor(request.getColor());
            membershipCardLevel.setModifiedBy(userId);

            Boolean result = membershipCardLevelMapper.update(membershipCardLevel);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/membership-card-level/update", null, null, "Membership Card Level", "Membership Card Level (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card-level/update", line, error.toString(), "Membership Card Level", "Membership Card Level (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card Level (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = membershipCardLevelMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/membership-card-level/delete/{id}", null, null, "Membership Card Level", "Membership Card Level (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card-level/delete/{id}", line, error.toString(), "Membership Card Level", "Membership Card Level (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}

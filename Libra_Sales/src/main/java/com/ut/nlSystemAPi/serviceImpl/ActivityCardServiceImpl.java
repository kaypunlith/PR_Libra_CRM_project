package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ActivityCardMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.ActivityCard.ActivityCard;
import com.ut.nlSystemAPi.model.filter.ActivityCardFilter;
import com.ut.nlSystemAPi.model.request.ActivityCard.ActivityCardRequest;
import com.ut.nlSystemAPi.model.response.ActivityCard.ActivityCardCustomerResponse;
import com.ut.nlSystemAPi.model.response.ActivityCard.ActivityCardResponse;
import com.ut.nlSystemAPi.service.ActivityCardService;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ActivityCardServiceImpl implements ActivityCardService {

    @Autowired
    private ActivityCardMapper activityCardMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(ActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Customer (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(activityCardMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ActivityCardCustomerResponse> responses = activityCardMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/list", null, null, "Customer", "Customer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/list", line, error.toString(), "Customer", "Customer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListReport(ActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(activityCardMapper.countListReport(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ActivityCardResponse> responses = activityCardMapper.getListReport(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/list-report", null, null, "Customer", "Customer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/list-report", line, error.toString(), "Customer", "Customer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ActivityCardResponse> responses = activityCardMapper.getOne(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/find/{id}", null, null, "Customer", "Customer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/find/{id}", line, error.toString(), "Customer", "Customer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            ActivityCard activityCard = new ActivityCard();
            activityCard.setCustomerContactId(request.getCustomerContactId());
            activityCard.setPosition(request.getPosition());
            activityCard.setActionStatusId(request.getActionStatusId());
            activityCard.setQuotationId(request.getQuotationId());
            activityCard.setIssueDate(request.getIssueDate());
            activityCard.setTypeId(request.getTypeId());
            activityCard.setSubject(request.getSubject());
            activityCard.setResultAction(request.getResultAction());
            activityCard.setOther(request.getOther());
            activityCard.setIsActive(1);
            activityCard.setCreatedBy(userId);

            Boolean result = activityCardMapper.insert(activityCard);
            if (result) {
                if (request.getPosition() != null && !request.getPosition().trim().isEmpty()) {
                    activityCardMapper.updateCustomerContactPosition(request.getCustomerContactId(), request.getPosition().trim());
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/activity-card/add", null, null, "Customer", "Customer (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/add", line, error.toString(), "Customer", "Customer (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = activityCardMapper.delete(id);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/activity-card/delete/{id}", null, null, "Customer", "Customer (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-card/delete/{id}", line, error.toString(), "Customer", "Customer (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}

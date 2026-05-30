package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.EmployeeCalendarMapper;
import com.ut.nlSystemAPi.model.EmployeeCalendar;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.EmployeeCalendarFilter;
import com.ut.nlSystemAPi.model.request.EmployeeCalendar.EmployeeCalendarRequest;
import com.ut.nlSystemAPi.model.request.EmployeeCalendar.EmployeeCalendarUpdateRequest;
import com.ut.nlSystemAPi.model.response.EmployeeCalendar.EmployeeCalendarResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeCalendarServiceImpl implements EmployeeCalendarService {

    @Autowired
    private EmployeeCalendarMapper employeeCalendarMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(EmployeeCalendarFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1037L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(employeeCalendarMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<EmployeeCalendarResponse> responses = employeeCalendarMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-calendar/list", null, null, "Employee Calendar", "Employee Calendar", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-calendar/list", line, error.toString(), "Employee Calendar", "Employee Calendar", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1037L;
        try {
            List<EmployeeCalendarResponse> responses = employeeCalendarMapper.getOne(id);
            if (!responses.isEmpty()) {
                EmployeeCalendarResponse response = responses.get(0);
                response.setMarketIds(employeeCalendarMapper.getMarketIds(id));
                response.setRepeatDayList(employeeCalendarMapper.getRepeatDayList(id));
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-calendar/find/{id}", null, null, "Employee Calendar", "Employee Calendar", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-calendar/find/{id}", line, error.toString(), "Employee Calendar", "Employee Calendar", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> insert(EmployeeCalendarRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1037L;
        try {
            Long userId = userService.getUserAuth().getId();

            EmployeeCalendar employeeCalendar = new EmployeeCalendar();
            employeeCalendar.setEmployeeId(request.getEmployeeId());
            employeeCalendar.setIsRepeatable(request.getIsRepeatable() == null ? 0 : request.getIsRepeatable());
            employeeCalendar.setRepeatType(request.getRepeatType() == null ? 0 : request.getRepeatType());
            employeeCalendar.setRepeatDays(request.getRepeatDays());
            employeeCalendar.setMarketIds(request.getMarketIds());
            employeeCalendar.setDateFrom(request.getDateFrom());
            employeeCalendar.setDateTo(request.getDateTo());
            employeeCalendar.setDescription(request.getDescription());
            employeeCalendar.setCreatedBy(userId);
            employeeCalendar.setIsActive(1);

            Boolean result = employeeCalendarMapper.insert(employeeCalendar);
            if (Boolean.TRUE.equals(result) && employeeCalendar.getId() != null) {
                insertDetails(employeeCalendar.getId(), employeeCalendar.getRepeatDays(), employeeCalendar.getMarketIds());
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/employee-calendar/add", null, null, "Employee Calendar", "Employee Calendar", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-calendar/add", line, error.toString(), "Employee Calendar", "Employee Calendar", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> update(EmployeeCalendarUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1037L;
        try {
            Long userId = userService.getUserAuth().getId();

            EmployeeCalendar employeeCalendar = new EmployeeCalendar();
            employeeCalendar.setId(request.getId());
            employeeCalendar.setEmployeeId(request.getEmployeeId());
            employeeCalendar.setIsRepeatable(request.getIsRepeatable() == null ? 0 : request.getIsRepeatable());
            employeeCalendar.setRepeatType(request.getRepeatType() == null ? 0 : request.getRepeatType());
            employeeCalendar.setRepeatDays(request.getRepeatDays());
            employeeCalendar.setMarketIds(request.getMarketIds());
            employeeCalendar.setDateFrom(request.getDateFrom());
            employeeCalendar.setDateTo(request.getDateTo());
            employeeCalendar.setDescription(request.getDescription());
            employeeCalendar.setModifiedBy(userId);

            Boolean result = employeeCalendarMapper.update(employeeCalendar);
            if (Boolean.TRUE.equals(result)) {
                employeeCalendarMapper.deleteDetails(request.getId());
                insertDetails(request.getId(), employeeCalendar.getRepeatDays(), employeeCalendar.getMarketIds());
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/employee-calendar/update", null, null, "Employee Calendar", "Employee Calendar", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-calendar/update", line, error.toString(), "Employee Calendar", "Employee Calendar", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1037L;
        try {
            Long userId = userService.getUserAuth().getId();

            Boolean result = employeeCalendarMapper.delete(id, userId);
            if (Boolean.TRUE.equals(result)) {
                employeeCalendarMapper.deleteDetails(id);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/employee-calendar/delete/{id}", null, null, "Employee Calendar", "Employee Calendar", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-calendar/delete/{id}", line, error.toString(), "Employee Calendar", "Employee Calendar", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void insertDetails(Long employeeCalendarId, List<String> repeatDays, List<Long> marketIds) {
        List<String> cleanRepeatDays = normalizeRepeatDays(repeatDays);
        List<Long> cleanMarketIds = normalizeMarketIds(marketIds);

        if (cleanRepeatDays.isEmpty() && cleanMarketIds.isEmpty()) {
            return;
        }

        if (cleanRepeatDays.isEmpty()) {
            for (Long marketId : cleanMarketIds) {
                employeeCalendarMapper.insertDetail(employeeCalendarId, marketId, null);
            }
            return;
        }

        if (cleanMarketIds.isEmpty()) {
            for (String repeatDay : cleanRepeatDays) {
                employeeCalendarMapper.insertDetail(employeeCalendarId, null, repeatDay);
            }
            return;
        }

        for (String repeatDay : cleanRepeatDays) {
            for (Long marketId : cleanMarketIds) {
                employeeCalendarMapper.insertDetail(employeeCalendarId, marketId, repeatDay);
            }
        }
    }

    private List<String> normalizeRepeatDays(List<String> repeatDays) {
        List<String> values = new ArrayList<>();
        if (repeatDays == null) {
            return values;
        }

        for (String repeatDay : repeatDays) {
            if (repeatDay == null) {
                continue;
            }

            String value = repeatDay.trim();
            if (!value.isEmpty() && !values.contains(value)) {
                values.add(value);
            }
        }
        return values;
    }

    private List<Long> normalizeMarketIds(List<Long> marketIds) {
        List<Long> values = new ArrayList<>();
        if (marketIds == null) {
            return values;
        }

        for (Long marketId : marketIds) {
            if (marketId != null && !values.contains(marketId)) {
                values.add(marketId);
            }
        }
        return values;
    }
}

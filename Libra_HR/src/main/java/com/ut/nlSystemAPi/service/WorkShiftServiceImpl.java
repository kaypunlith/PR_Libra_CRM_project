package com.ut.nlSystemAPi.service;

import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ut.nlSystemAPi.model.response.WorkShiftDropDownResponse;
import com.ut.nlSystemAPi.model.response.WorkShiftTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.WorkShiftMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.WorkShift;
import com.ut.nlSystemAPi.model.base.BaseId;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.WorkShiftRequest;
import com.ut.nlSystemAPi.model.request.WorkShiftUpdateRequest;
import com.ut.nlSystemAPi.model.response.WorkShiftResponse;

@Service
public class WorkShiftServiceImpl implements WorkShiftService {

  @Autowired
  private WorkShiftMapper workShiftMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;

  @Autowired
  private ActivityLogService activityLogService;

  public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest)
      throws UnknownHostException {
    // LocalTime startDuration = LocalTime.now();
    try {
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Work Shift (View)") == 0) {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      if (filter != null) {
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
      }

      pagination.setTotal(this.workShiftMapper.getCount(filter));

      List<WorkShiftResponse> workShiftResponse = this.workShiftMapper.getList(filter);
      if (workShiftResponse.size() > 0) {
        for (WorkShiftResponse workshift : workShiftResponse) {
          workshift.setDays(this.workShiftMapper.getListDays(workshift.getId()));
        }
      }

      // * return to front-end
      return ResponseMessageUtils.makeResponse(
          true,
          this.messageService.message("success", workShiftResponse, pagination, true));
    } catch (Exception e) {
      // * return to front-end
      System.out.println(e.toString());
      return ResponseMessageUtils.makeResponse(
          true,
          messageService.message(
              "Error",
              null,
              false));
    }

  }

  // * get one by id
  @Override
  public ResponseMessage<BaseResult> getOne(Long id) {
    try {
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Work Shift (View)") == 0) {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
      }

      List<WorkShiftResponse> workShiftResponse = this.workShiftMapper.getOne(id);
      if (workShiftResponse.size() > 0) {
        for (WorkShiftResponse workshift : workShiftResponse) {
          workshift.setDays(this.workShiftMapper.getListDays(id));
        }
      }
      // * return to front-end
      return ResponseMessageUtils.makeResponse(
          true,
          this.messageService.message("success", workShiftResponse, true));
    } catch (Exception e) {
      // * return to front-end
      System.out.println(e.toString());
      return ResponseMessageUtils.makeResponse(
          true,
          messageService.message(
              "Error",
              null,
              false));
    }

  }

  // * insert a new record
  @Override
  public ResponseMessage<BaseResult> insert(WorkShiftRequest request, Long creatorId, HttpServletRequest httpServletRequest) throws UnknownHostException {

    LocalTime startDuration = LocalTime.now();

    Long userId = userService.getUserAuth().getId();
    if (permissionMapper.checkPermission(userId, "Work Shift (Add)") == 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
    }

    // * check whether the days list is empty
    if (request.getDays() == null || request.getDays().size() <= 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Day list is empty!", false));
    }

    // * Check Duplicate
    if(workShiftMapper.checkDuplicate(request.getName(), null) > 0){
      return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name.", false));
    }

    WorkShift workShift = new WorkShift();
    workShift.setName(request.getName());
    workShift.setBeginningCheckIn(request.getBeginningCheckIn());
    workShift.setBeginningCheckOut(request.getBeginningCheckOut());
    workShift.setTimeFrom(request.getTimeFrom());
    workShift.setTimeTo(request.getTimeTo());
    workShift.setLateCheckingTime(request.getLateCheckingTime());
    workShift.setLeaveEarlyTime(request.getLeaveEarlyTime());
    workShift.setEndingCheckIn(request.getEndingCheckIn());
    workShift.setEndingCheckOut(request.getEndingCheckOut());
    workShift.setDescription(request.getDescription());
    try {
      Boolean result = workShiftMapper.insert(workShift, creatorId);
      // Insert day
      if(result){
        for (Long dayId : request.getDays()) {
          workShiftMapper.insertWorkShiftDay(workShift.getId(), dayId, creatorId);
        }
      }
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    } catch (Exception e) {
      /* System Activity */
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/work-shift/insert", 117L, "Error", "Work Shift", "Work shift (ADD)", "ADD", 2, e.getLocalizedMessage(), startDuration, endDuration, httpServletRequest);
      // * return to front-end
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error",null,false));
    }
  }

  // * update by id 
  @Override
  public ResponseMessage<BaseResult> update(WorkShiftUpdateRequest request, Long modifierId) {

    Long userId = userService.getUserAuth().getId();
    if (permissionMapper.checkPermission(userId, "Work Shift (Edit)") == 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
    }

    if ((request.getTimeFrom() != null && request.getTimeTo() == null) ||
        (request.getTimeFrom() == null && request.getTimeTo() != null)) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Time From or Time To is empty", false));
    }

    // * Check Duplicate
    if(workShiftMapper.checkDuplicate(request.getName(), request.getId()) > 0){
      return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name.", false));
    }

    // Check Data
    WorkShift workShift = new WorkShift();
    workShift.setId(request.getId());
    workShift.setName(request.getName());
    workShift.setBeginningCheckIn(request.getBeginningCheckIn());
    workShift.setBeginningCheckOut(request.getBeginningCheckOut());
    workShift.setTimeFrom(request.getTimeFrom());
    workShift.setTimeTo(request.getTimeTo());
    workShift.setLateCheckingTime(request.getLateCheckingTime());
    workShift.setLeaveEarlyTime(request.getLeaveEarlyTime());
    workShift.setEndingCheckIn(request.getEndingCheckIn());
    workShift.setEndingCheckOut(request.getEndingCheckOut());
    workShift.setDescription(request.getDescription());

    Boolean result = workShiftMapper.update(workShift, modifierId);
    if (!result) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Insert Work Shift Failed", false));
    }
    if (!request.getDays().isEmpty()) {
      workShiftMapper.deleteListDays(request.getId(), modifierId);
      for (Long dayId : request.getDays()) {
        if (!workShiftMapper.insertWorkShiftDay(workShift.getId(), dayId, modifierId)) {
          return ResponseMessageUtils.makeResponse(true, messageService.message("Insert Days Failed", false));
        }
      }
    }
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
  }

  // * insert a new record
  @Override
  public ResponseMessage<BaseResult> delete(Long id, Long userId) {

    if (permissionMapper.checkPermission(userId, "Work Shift (Delete)") == 0) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
    }

    Boolean result = this.workShiftMapper.delete(id, userId);
    if (!result) {
      return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));

  }

  public ResponseMessage<BaseResult> getListDropDown(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    try {

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      if (filter != null) {
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
      }

      List<WorkShiftDropDownResponse> workShiftResponse = this.workShiftMapper.getListWithOutPermission(filter);

      pagination.setTotal((long) workShiftResponse.size());

      // * return to front-end
      return ResponseMessageUtils.makeResponse(true,this.messageService.message("success", workShiftResponse, pagination, true));
    } catch (Exception e) {
      // * return to front-end
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error",null,false));
    }

  }

  public ResponseMessage<BaseResult> getListWorkShiftType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    try {

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      if (filter != null) {
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
      }

      List<WorkShiftTypeResponse> workShiftResponse = this.workShiftMapper.getListWorkShiftType(filter);

      pagination.setTotal((long) workShiftResponse.size());

      // * return to front-end
      return ResponseMessageUtils.makeResponse(true,this.messageService.message("success", workShiftResponse, pagination, true));
    } catch (Exception e) {
      // * return to front-end
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error",null,false));
    }

  }


}

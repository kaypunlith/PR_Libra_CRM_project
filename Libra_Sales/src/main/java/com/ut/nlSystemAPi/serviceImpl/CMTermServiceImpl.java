package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.CMTerm.CMTerm;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.request.CMTerm.CMTermRequest;
import com.ut.nlSystemAPi.model.request.CMTerm.CMTermUpdateRequest;
import com.ut.nlSystemAPi.model.response.CMTerm.CMTermResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.CMTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class CMTermServiceImpl implements CMTermService {

  @Autowired
  private CMTermMapper cmTermMapper;

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

  public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Reason (View)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(cmTermMapper.countList(filter));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<CMTermResponse> responses = cmTermMapper.getList(filter);

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/cm-term/list",null,null,"CM Term","CM Term (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/cm-term/list",line, error.toString(),"CM Term","CM Term (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Reason (View)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      List<CMTermResponse> responses = cmTermMapper.getOne(id);

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/cm-term/find/{id}",null,null,"CM Term","CM Term (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/cm-term/find/{id}",line, error.toString(),"CM Term","CM Term (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> insert(CMTermRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
     // Check Permission
     Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Reason (Add)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      // Check Duplicate
      if(cmTermMapper.checkDuplicate(request.getName(), null) > 0){
       return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
      }

      // Check Data
      CMTerm cmTerm = new CMTerm();
      cmTerm.setName(request.getName());
      cmTerm.setCreatedBy(userId);
      cmTerm.setIsActive(1);
      Boolean result = cmTermMapper.insert(cmTerm);

     if (result) {
       /*System Activity*/
       LocalTime endDuration = LocalTime.now();
       activityLogService.insert("/cm-term/add",null,null,"CM Term","CM Term (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
       return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
     } else {
       return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
     }
   } catch (Exception error) {
     /*System Activity*/
     LocalTime endDuration = LocalTime.now();
     activityLogService.insert("/cm-term/add",line, error.toString(),"CM Term","CM Term (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
     return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
   }
  }

  public ResponseMessage<BaseResult> update(CMTermUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Reason (Edit)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      // Check Data
      CMTerm cmTerm = new CMTerm();
      cmTerm.setId(request.getId());
      cmTerm.setName(request.getName());
      cmTerm.setModifiedBy(userId);
      Boolean result = cmTermMapper.update(cmTerm);

      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/cm-term/update",null,null,"CM Term","CM Term (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/cm-term/update",line, error.toString(),"CM Term","CM Term (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Reason (Delete)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Boolean result = cmTermMapper.delete(id, userId);
      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/cm-term/delete/{id}",null,null,"CM Term","CM Term (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/cm-term/delete/{id}",line, error.toString(),"CM Term","CM Term (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }
}
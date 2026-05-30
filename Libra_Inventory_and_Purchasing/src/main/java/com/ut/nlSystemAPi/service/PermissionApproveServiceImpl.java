package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.mapper.utscrum.ScrumMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.DepartmentFilter;
import com.ut.nlSystemAPi.model.request.Login.PermissionApprove.PermissionApproveRequest;
import com.ut.nlSystemAPi.model.request.Login.PermissionApprove.PermissionApproveUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.DepartmentDropDownResponse;
import com.ut.nlSystemAPi.model.response.PermissionApprove.DepartmentPermissionApproveResponse;
import com.ut.nlSystemAPi.model.response.PermissionApprove.PermissionApproveResponse;
import com.ut.nlSystemAPi.model.response.PermissionApprove.UserPermissionApproveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class PermissionApproveServiceImpl implements PermissionApproveService {

  @Autowired
  private ScrumMapper scrumMapper;

  @Autowired
  private PermissionApproveMapper permissionApproveMapper;

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
    private DropdownMapper dropdownMapper;

  public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Permission Approves (view)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(permissionApproveMapper.countList(filter));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<PermissionApproveResponse> responses = permissionApproveMapper.getList(filter);
      if (responses.size() > 0){
        for(int i =0; i<responses.size(); i++){
          List<UserPermissionApproveResponse> userList = permissionApproveMapper.getUserList(responses.get(i).getId());
          responses.get(i).setUserList(userList);
        }
        DepartmentFilter departmentFilter = new DepartmentFilter();
        for(int i =0; i<responses.size(); i++){
          departmentFilter.setId(responses.get(i).getId());
          List<DepartmentDropDownResponse> departmentList = dropdownMapper.getListDepartment(departmentFilter);
          responses.get(i).setDepartmentList(departmentList);
        }
      }

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/permission-approve/list",null,null,"Permission Approves","Permission Approves (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/permission-approve/list",line, error.toString(),"Permission Approves","Permission Approves (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Permission Approves (view)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      List<PermissionApproveResponse> responses = permissionApproveMapper.getOne(id);
      if (responses.size() > 0){
        for(int i =0; i<responses.size(); i++){
          List<UserPermissionApproveResponse> userList = permissionApproveMapper.getUserList(responses.get(i).getId());
          responses.get(i).setUserList(userList);
        }

        DepartmentFilter departmentFilter = new DepartmentFilter();
        for(int i =0; i<responses.size(); i++){
          departmentFilter.setId(responses.get(i).getId());
          List<DepartmentDropDownResponse> departmentList = dropdownMapper.getListDepartment(departmentFilter);
          responses.get(i).setDepartmentList(departmentList);
        }
      }

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/permission-approve/find/{id}",null,null,"Permission Approves","Permission Approves (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/permission-approve/find/{id}",line, error.toString(),"Permission Approves","Permission Approves (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> insert(PermissionApproveRequest permissionApproveRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
     // Check Permission
     Long userId = userService.getUserAuth().getId();
     if (permissionMapper.checkPermission(userId, "Permission Approves (Add)") == 0) {
       return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
     }

      // Check Data
      PermissionApprove permissionApprove = new PermissionApprove();
      permissionApprove.setName(permissionApproveRequest.getName());
      permissionApprove.setType(permissionApproveRequest.getType());
      permissionApprove.setFromAmount(permissionApproveRequest.getFromAmount());
      permissionApprove.setToAmount(permissionApproveRequest.getToAmount());
      permissionApprove.setIsCashAdvance(permissionApproveRequest.getIsCashAdvance());
      permissionApprove.setIsCod(permissionApproveRequest.getIsCod());
      permissionApprove.setCreatedBy(userId);
      permissionApprove.setIsActive(1);
      Boolean result = permissionApproveMapper.insert(permissionApprove);

     if (result) {
       List<Long> users = permissionApproveRequest.getUserList();
       for (int i = 0; i< users.size(); i++) {
         UserPermissionApprove userPermissionApproveResponse = new UserPermissionApprove();
         userPermissionApproveResponse.setUserId(users.get(i));
         userPermissionApproveResponse.setPermissionApproveId(permissionApprove.getId());
         permissionApproveMapper.insertUserPermissionApprove(userPermissionApproveResponse);
       }

       List<Long> departments = permissionApproveRequest.getDepartmentList();
       for (int i = 0; i< departments.size(); i++) {
         DepartmentPermissionApprove departmentPermissionApprove = new DepartmentPermissionApprove();
         departmentPermissionApprove.setDepartmentId(departments.get(i));
         departmentPermissionApprove.setPermissionApproveId(permissionApprove.getId());
         permissionApproveMapper.insertDepartmentPermissionApprove(departmentPermissionApprove);
       }

       /*System Activity*/
       LocalTime endDuration = LocalTime.now();
       activityLogService.insert("/warehouse/add",null,null,"Warehouse","Warehouse (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
       return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
     } else {
       return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
     }
   } catch (Exception error) {
     /*System Activity*/
     LocalTime endDuration = LocalTime.now();
     activityLogService.insert("/warehouse/add",line, error.toString(),"Warehouse","Warehouse (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
     return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
   }
  }

  public ResponseMessage<BaseResult> update(PermissionApproveUpdateRequest permissionApproveUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Permission Approves (Edit)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      // Check Data
      PermissionApprove permissionApprove = new PermissionApprove();
      permissionApprove.setId(permissionApproveUpdateRequest.getId());
      permissionApprove.setName(permissionApproveUpdateRequest.getName());
      permissionApprove.setType(permissionApproveUpdateRequest.getType());
      permissionApprove.setFromAmount(permissionApproveUpdateRequest.getFromAmount());
      permissionApprove.setToAmount(permissionApproveUpdateRequest.getToAmount());
      permissionApprove.setIsCashAdvance(permissionApproveUpdateRequest.getIsCashAdvance());
      permissionApprove.setIsCod(permissionApproveUpdateRequest.getIsCod());
      permissionApprove.setModifiedBy(userId);
      Boolean result = permissionApproveMapper.update(permissionApprove);
      if (result) {
        permissionApproveMapper.deleteUserPermissionApprove(permissionApprove.getId());
        List<Long> users = permissionApproveUpdateRequest.getUserList();
        for (int i = 0; i< users.size(); i++) {
          UserPermissionApprove userPermissionApproveResponse = new UserPermissionApprove();
          userPermissionApproveResponse.setUserId(users.get(i));
          userPermissionApproveResponse.setPermissionApproveId(permissionApprove.getId());
          permissionApproveMapper.insertUserPermissionApprove(userPermissionApproveResponse);
        }

        permissionApproveMapper.deleteDepartmentPermissionApprove(permissionApprove.getId());
        List<Long> departments = permissionApproveUpdateRequest.getDepartmentList();
        for (int i = 0; i< departments.size(); i++) {
          DepartmentPermissionApprove departmentPermissionApprove = new DepartmentPermissionApprove();
          departmentPermissionApprove.setDepartmentId(departments.get(i));
          departmentPermissionApprove.setPermissionApproveId(permissionApprove.getId());
          permissionApproveMapper.insertDepartmentPermissionApprove(departmentPermissionApprove);
        }

        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/permission-approve/update",null,null,"Permission Approves","Permission Approves (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/permission-approve/update",line, error.toString(),"Permission Approves","Permission Approves (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Permission Approves (Delete)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Boolean result = permissionApproveMapper.delete(id, userId);
      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/permission-approve/delete/{id}",null,null,"Permission Approves","Permission Approves (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/permission-approve/delete/{id}",line, error.toString(),"Permission Approves","Permission Approves (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }
}
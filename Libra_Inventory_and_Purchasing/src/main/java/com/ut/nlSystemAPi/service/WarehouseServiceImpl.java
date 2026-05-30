package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.Users.UserList;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.Warehouse.WarehouseRequest;
import com.ut.nlSystemAPi.model.request.Login.Warehouse.WarehouseUpdateRequest;
import com.ut.nlSystemAPi.model.response.Role.RoleResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.LocationWarehouseResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.UserWarehouseResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.WarehouseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

  @Autowired
  private WarehouseMapper warehouseMapper;

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
//      if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(warehouseMapper.countList(filter));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<WarehouseResponse> warehouseResponses = warehouseMapper.getList(filter);
      if (warehouseResponses.size() > 0){
        for(int i =0; i<warehouseResponses.size(); i++){
          Long warehouseId = warehouseResponses.get(i).getWarehouseId();
          List<UserWarehouseResponse> userWarehouseResponses = warehouseMapper.getUserList(warehouseId);
          warehouseResponses.get(i).setUsers(userWarehouseResponses);
        }

        for(int i =0; i<warehouseResponses.size(); i++){
          Long warehouseId = warehouseResponses.get(i).getWarehouseId();
          List<LocationWarehouseResponse> locationWarehouseResponses = warehouseMapper.getLocationList(warehouseId);
          warehouseResponses.get(i).setLocations(locationWarehouseResponses);
        }
      }
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/warehouse/list",null,null,"System Role","System Role (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", warehouseResponses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/warehouse/list",line, error.toString(),"System Role","System Role (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

      List<WarehouseResponse> warehouseResponses = warehouseMapper.getOne(id);
      if (warehouseResponses.size() > 0){
        for(int i =0; i<warehouseResponses.size(); i++){
          Long warehouseId = warehouseResponses.get(i).getWarehouseId();
          List<UserWarehouseResponse> userWarehouseResponses = warehouseMapper.getUserList(warehouseId);
          warehouseResponses.get(i).setUsers(userWarehouseResponses);
        }
      }

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/find/{id}",null,null,"System Role","System Role (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", warehouseResponses, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/find/{id}",line, error.toString(),"System Role","System Role (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> insert(WarehouseRequest warehouseRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
     // Check Permission
     Long userId = userService.getUserAuth().getId();
//     if (permissionMapper.checkPermission(userId, "System Role (Add)") == 0) {
//       return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//     }

     // Check Duplicate
     if(warehouseMapper.checkDuplicate(warehouseRequest.getName(), null) > 0){
       return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
     }

      // Check Data
      Warehouse warehouse = new Warehouse();
      warehouse.setName(warehouseRequest.getName());
      warehouse.setDescription(warehouseRequest.getDescription());
      warehouse.setStockLevelId(warehouseRequest.getStockLevelId());
      warehouse.setCreatedBy(userId);
      warehouse.setIsActive(1);
      Boolean result = warehouseMapper.insert(warehouse);

      String groupTotalsTable = warehouse.getId() + "_group_totals";
      String groupTotalDetailsTable = warehouse.getId() + "_group_total_details";

      Long checkGroupTotalsTable = warehouseMapper.checkTableExists(groupTotalsTable);
      Long checkGroupTotalDetailsTable = warehouseMapper.checkTableExists(groupTotalDetailsTable);

      if (checkGroupTotalsTable == 0 && checkGroupTotalDetailsTable == 0) {
          // Create the table
          warehouseMapper.insertGroupTotal(warehouse.getId());
          warehouseMapper.insertGroupTotalDetail(warehouse.getId());
      } else {
          return ResponseMessageUtils.makeResponse(false, messageService.message("Failed to create table.", false));
      }

     if (result) {
       List<Long> users = warehouseRequest.getUsers();
       System.out.println(users);
       for (int i = 0; i< users.size(); i++) {
         UserWarehouse userWarehouse = new UserWarehouse();
         userWarehouse.setUserId(users.get(i));
         userWarehouse.setWarehouseId(warehouse.getId());
         warehouseMapper.insertUserWarehouse(userWarehouse);
       }

         ClassWarehouse classWarehouse = new ClassWarehouse();
         classWarehouse.setClassId(warehouseRequest.getClassId());
         classWarehouse.setWarehouseId(warehouse.getId());
         warehouseMapper.insertClassWarehouse(classWarehouse);

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

  public ResponseMessage<BaseResult> update(WarehouseUpdateRequest warehouseUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (Edit)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

      // Check Duplicate
      if(warehouseMapper.checkDuplicate(warehouseUpdateRequest.getName(), warehouseUpdateRequest.getId()) > 0){
        return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
      }

      // Check Data
      Warehouse warehouse = new Warehouse();
      warehouse.setId(warehouseUpdateRequest.getId());
      warehouse.setName(warehouseUpdateRequest.getName());
      warehouse.setDescription(warehouseUpdateRequest.getDescription());
      warehouse.setStockLevelId(warehouseUpdateRequest.getStockLevelId());
      warehouse.setModifiedBy(userId);
      Boolean result = warehouseMapper.update(warehouse);
      if (result) {
        warehouseMapper.deleteUserWarehouse(warehouse.getId());
        List<Long> users = warehouseUpdateRequest.getUsers();
        for (int i = 0; i< users.size(); i++) {
          UserWarehouse userWarehouse = new UserWarehouse();
          userWarehouse.setUserId(users.get(i));
          userWarehouse.setWarehouseId(warehouse.getId());
          warehouseMapper.insertUserWarehouse(userWarehouse);
        }

        warehouseMapper.deleteClassWarehouse(warehouse.getId());
        ClassWarehouse classWarehouse = new ClassWarehouse();
        classWarehouse.setClassId(warehouseUpdateRequest.getClassId());
        classWarehouse.setWarehouseId(warehouse.getId());
        warehouseMapper.insertClassWarehouse(classWarehouse);

        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/role/update",null,null,"System Role","System Role (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/update",line, error.toString(),"System Role","System Role (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (Delete)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

      Boolean result = warehouseMapper.delete(id, userId);
      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/role/delete/{id}",null,null,"System Role","System Role (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/delete/{id}",line, error.toString(),"System Role","System Role (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }
}

package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.Section.SectionListRequest;
import com.ut.nlSystemAPi.model.request.Login.Section.SectionRequest;
import com.ut.nlSystemAPi.model.request.Login.Section.SectionUpdateRequest;
import com.ut.nlSystemAPi.model.response.Section.SectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

  @Autowired
  private SectionMapper sectionMapper;

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
      if (permissionMapper.checkPermission(userId, "Section (view)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(sectionMapper.countList(filter));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<SectionResponse> sectionResponses = sectionMapper.getList(filter);
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/section/list",null,null,"Section","Section (view)","view",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", sectionResponses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/section/list",line, error.toString(),"Section","Section (view)","view",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Section (view)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }
      List<SectionResponse> sectionResponses = sectionMapper.getOne(id);

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/section/find/{id}",null,null,"Section","Section (view)","view",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", sectionResponses, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/section/find/{id}",line, error.toString(),"Section","Section (view)","view",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> insert(SectionRequest sectionRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Section (add)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      for (int i = 0; i< sectionRequest.getSections().size(); i++) {
        // Check Duplicate
        if(sectionMapper.checkDuplicate(sectionRequest.getSections().get(i).getName(), null) > 0){
          return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
        }
      }

      Warehouse warehouse = new Warehouse();
      warehouse.setId(sectionRequest.getWarehouseId());
      List<SectionListRequest> sections = sectionRequest.getSections();

      if (sections.size() > 0) {
        for (int i = 0; i< sections.size(); i++) {
          // Check Duplicate
          if(sectionMapper.checkDuplicate(sectionRequest.getSections().get(i).getName(), null) > 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
          }
          Section section = new Section();
          section.setWarehouseId(warehouse.getId());
          section.setLocationName(sections.get(i).getName());
          section.setIsForSale(sections.get(i).getIsForSale());
          section.setCreatedBy(userId);
          section.setIsActive(1);
          sectionMapper.insert(section);

          String inventoryTable = section.getId() + "_inventories";
          String inventoryTotalsTable = section.getId() + "_inventory_totals";
          String inventoryTotalDetailsTable = section.getId() + "_inventory_total_details";

          Long checkInventoryTable = sectionMapper.checkTableExists(inventoryTable);
          Long checkInventoryTotalsTable = sectionMapper.checkTableExists(inventoryTotalsTable);
          Long checkInventoryTotalDetailsTable = sectionMapper.checkTableExists(inventoryTotalDetailsTable);

          Long sectionId = section.getId();
          if (checkInventoryTable == 0 && checkInventoryTotalsTable == 0 && checkInventoryTotalDetailsTable == 0) {
            // Create the table
            sectionMapper.insertInventory(sectionId);
            sectionMapper.insertInventoryTotal(sectionId);
            sectionMapper.insertInventoryTotalDetail(sectionId);
            sectionMapper.insertUserLocations(warehouse.getId(), sectionId);
          } else {
            return ResponseMessageUtils.makeResponse(false, messageService.message("Failed to create table.", false));
          }
        }
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/section/add",null,null,"Section","Section (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/section/add",line, error.toString(),"Section","Section (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> update(SectionUpdateRequest sectionUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Section (edit)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      // Check Duplicate
      if(sectionMapper.checkDuplicate(sectionUpdateRequest.getName(), sectionUpdateRequest.getId()) > 0){
        return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
      }

      // Check Data
      Section section = new Section();
      section.setId(sectionUpdateRequest.getId());
      section.setLocationName(sectionUpdateRequest.getName());
      section.setWarehouseId(sectionUpdateRequest.getWarehouseId());
      section.setIsForSale(sectionUpdateRequest.getIsForSale());
      section.setModifiedBy(userId);
      Boolean result = sectionMapper.update(section);
      if (result) {
        sectionMapper.deleteUserLocations(section.getId());
        sectionMapper.insertUserLocations(section.getWarehouseId(), section.getId());
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/section/update",null,null,"Section","Section (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/section/update",line, error.toString(),"Section","Section (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Section (delete)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Boolean result = sectionMapper.delete(id, userId);
      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/section/delete/{id}",null,null,"Section","Section (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/section/delete/{id}",line, error.toString(),"Section","Section (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }
}

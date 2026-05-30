package com.ut.nlSystemAPi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.DropdownMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.DropdownFilter;
import com.ut.nlSystemAPi.model.response.DropdownResponse;

@Service
public class DropdownServiceImpl implements DropdownService {

  @Autowired
  private DropdownMapper dropdownMapper;

  @Autowired
  private PermissionMapper permissionMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Override
  public ResponseMessage<BaseResult> getGroupDropdown(DropdownFilter filter) {
    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
//    pagination.setTotal(dropdownMapper.countDepartmentDropdown(filter));
    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

    List<DropdownResponse> groups = dropdownMapper.getGroupDropdown(filter);

    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", groups, true));
  }

  @Override
  public ResponseMessage<BaseResult> getDepartmentDropdown(DropdownFilter filter) {

    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(dropdownMapper.countDepartmentDropdown(filter));
    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

    List<DropdownResponse> departments = dropdownMapper.getDepartmentDropdown(filter);

    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", departments, pagination, true));
  }

  @Override
  public ResponseMessage<BaseResult> getDayDropdown(DropdownFilter filter) {
    // Long userId = userService.getUserAuth().getId();
    // if (permissionMapper.checkPermission(userId, "Day (View)") == 0) {
    // return ResponseMessageUtils.makeResponse(true,
    // messageService.message("Authorization", false));
    // }

    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(dropdownMapper.countDayDropdown(filter));
    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

    List<DropdownResponse> days = dropdownMapper.getDayDropdown(filter);

    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", days, pagination, true));
  }

  @Override
  public ResponseMessage<BaseResult> getPositionDropdown(DropdownFilter filter) {
    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(dropdownMapper.countPositionDropdown(filter));
    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

    List<DropdownResponse> positions = dropdownMapper.getPositionDropdown(filter);

    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", positions, pagination, true));
  }

  @Override
  public ResponseMessage<BaseResult> getEmployeeTypeDropdown(DropdownFilter filter) {
    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(dropdownMapper.countEmployeeTypeDropdown(filter));
    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

    List<DropdownResponse> employeeTypes = dropdownMapper.getEmployeeTypeDropdown(filter);

    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeeTypes, pagination, true));
  }

  @Override
  public ResponseMessage<BaseResult> getEmployeeStatusDropdown(DropdownFilter filter) {
    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(dropdownMapper.countEmployeeStatusDropdown(filter));
    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

    List<DropdownResponse> employeeStatuses = dropdownMapper.getEmployeeStatusDropdown(filter);

    return ResponseMessageUtils.makeResponse(true,
        messageService.message("Success", employeeStatuses, pagination, true));
  }

  @Override
  public ResponseMessage<BaseResult> getMaritalStatusDropdown(DropdownFilter filter) {
    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(dropdownMapper.countMaritalStatusDropdown(filter));
    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

    List<DropdownResponse> maritalStatuses = dropdownMapper.getMaritalStatusDropdown(filter);

    return ResponseMessageUtils.makeResponse(true,
        messageService.message("Success", maritalStatuses, pagination, true));
  }

  @Override
  public ResponseMessage<BaseResult> getLeaveRequestTypeDropdown(DropdownFilter filter) {
    List<DropdownResponse> responses = dropdownMapper.getLeaveRequestTypeDropdown(filter);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
  }

  @Override
  public ResponseMessage<BaseResult> getWorkShiftDropdown(DropdownFilter filter) {
    List<DropdownResponse> responses = dropdownMapper.getWorkShiftDropdown(filter);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
  }

  @Override
  public ResponseMessage<BaseResult> getMissionMeanDropdown(DropdownFilter filter) {
    List<DropdownResponse> responses = dropdownMapper.getMissionMeanDropdown(filter);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
  }

  @Override
  public ResponseMessage<BaseResult> getMissionDestinationDropdown(DropdownFilter filter) {
    List<DropdownResponse> responses = dropdownMapper.getMissionDestinationDropdown(filter);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
  }

  @Override
  public ResponseMessage<BaseResult> getMissionOrganizationDropdown(DropdownFilter filter) {
    List<DropdownResponse> responses = dropdownMapper.getMissionOrganizationDropdown(filter);
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
  }
}

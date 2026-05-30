package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.DropdownFilter;

public interface DropdownService {

  ResponseMessage<BaseResult> getGroupDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getDepartmentDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getLeaveRequestTypeDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getDayDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getWorkShiftDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getPositionDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getEmployeeTypeDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getEmployeeStatusDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getMaritalStatusDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getMissionMeanDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getMissionDestinationDropdown(DropdownFilter filter);

  ResponseMessage<BaseResult> getMissionOrganizationDropdown(DropdownFilter filter);
}

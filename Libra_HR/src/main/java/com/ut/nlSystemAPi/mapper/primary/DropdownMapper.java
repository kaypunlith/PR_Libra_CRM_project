package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.filter.DropdownFilter;
import com.ut.nlSystemAPi.model.response.DropdownResponse;

@Repository
public interface DropdownMapper {

  List<DropdownResponse> getGroupDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getDepartmentDropdown(@Param("filter") DropdownFilter filter);

  Long countDepartmentDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getDayDropdown(@Param("filter") DropdownFilter filter);

  Long countDayDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getLeaveRequestTypeDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getWorkShiftDropdown(@Param("filter") DropdownFilter filter);

  Long countWorkShiftDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getPositionDropdown(@Param("filter") DropdownFilter filter);

  Long countPositionDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getEmployeeTypeDropdown(@Param("filter") DropdownFilter filter);

  Long countEmployeeTypeDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getEmployeeStatusDropdown(@Param("filter") DropdownFilter filter);

  Long countEmployeeStatusDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getMaritalStatusDropdown(@Param("filter") DropdownFilter filter);

  Long countMaritalStatusDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getMissionMeanDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getMissionDestinationDropdown(@Param("filter") DropdownFilter filter);

  List<DropdownResponse> getMissionOrganizationDropdown(@Param("filter") DropdownFilter filter);

}

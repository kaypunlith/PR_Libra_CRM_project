package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Request payload for inserting an attendance record manually.
 */
@Data
public class InsertAttendanceRequest {

  @ApiModelProperty(position = 10, required = true, value = "Employee identifier")
  @NotNull(message = "EmployeeId must not be null")
  private Long employeeId;

  @ApiModelProperty(position = 20, required = true, value = "Working location identifier")
  @NotNull(message = "WorkingLocationId must not be null")
  private Long workingLocationId;

  @ApiModelProperty(position = 30, required = true, value = "Attendance type (1: Check In; 2: Check Out)")
  @NotNull(message = "Type must not be null")
  private Integer type;

  @ApiModelProperty(position = 40, required = true, value = "Status (1: On-Time; 2: Late; 3: Early)")
  @NotNull(message = "Status must not be null")
  private Integer status;

  @ApiModelProperty(position = 50, required = true, value = "Minutes difference compared to the shift")
  @NotNull(message = "Minute must not be null")
  private Integer minute;

  @ApiModelProperty(position = 60, required = true, value = "Work shift identifier")
  @NotNull(message = "WorkShiftId must not be null")
  private Long workShiftId;

  @ApiModelProperty(position = 70, value = "Datetime for the attendance scan (optional, default to now). Format yyyy-MM-dd HH:mm:ss")
  private String datetimeScan;

}

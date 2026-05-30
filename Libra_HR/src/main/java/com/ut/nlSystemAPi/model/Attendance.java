package com.ut.nlSystemAPi.model;

import java.time.LocalDateTime;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Attendance extends BaseModel {

	@ApiModelProperty(position = 1)
	private Long id;

	@ApiModelProperty(position = 2)
	private Long employeeId;

	@ApiModelProperty(position = 3)
	private Long workingLocationId;

	@ApiModelProperty(position = 4)
	private Integer type;

	@ApiModelProperty(position = 5)
	private int status;

	@ApiModelProperty(position = 6)
	private Integer minute;

	@ApiModelProperty(position = 7)
	private Long workShiftId;

	@ApiModelProperty(position = 8)
	private String shiftTimeFrom;

	@ApiModelProperty(position = 9)
	private String shiftTimeTo;

	@ApiModelProperty(position = 10)
	private Long lateCheckInTime;

	@ApiModelProperty(position = 11)
	private Long leaveEarlyTime;

	@ApiModelProperty(position = 12)
	private String beginningCheckIn;

	@ApiModelProperty(position = 13)
	private String endingCheckIn;

	@ApiModelProperty(position = 14)
	private String beginningCheckOut;

	@ApiModelProperty(position = 15)
	private String endingCheckOut;

	@ApiModelProperty(position = 16)
	private Long totalWorkingMinute;

	@ApiModelProperty(position = 17)
	private String day;

	@ApiModelProperty(position = 18)
	private LocalDateTime datetimeScan;

}

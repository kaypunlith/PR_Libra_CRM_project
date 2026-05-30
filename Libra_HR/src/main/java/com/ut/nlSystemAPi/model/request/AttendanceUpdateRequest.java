package com.ut.nlSystemAPi.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AttendanceUpdateRequest extends InsertAttendanceRequest {

	@ApiModelProperty(position = 5, required = true, value = "Attendance identifier")
	@NotNull(message = "Id must not be null")
	private Long id;

}

package com.ut.nlSystemAPi.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LeaveRequestUpdate extends LeaveRequestAdd {

	@ApiModelProperty(position = 1)
	@NotNull(message = "Value must not null")
	private Long id;

}

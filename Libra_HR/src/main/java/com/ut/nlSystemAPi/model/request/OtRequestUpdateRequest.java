package com.ut.nlSystemAPi.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OtRequestUpdateRequest extends OtRequestRequest {

	@ApiModelProperty(position = 1)
	@NotNull(message = "Id must not be null")
	private Long id;

}

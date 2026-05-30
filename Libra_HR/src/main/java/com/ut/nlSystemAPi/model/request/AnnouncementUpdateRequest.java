package com.ut.nlSystemAPi.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnnouncementUpdateRequest extends AnnouncementRequest {

	@ApiModelProperty(position = 1)
	@NotNull(message = "Value must not null")
	private Long id;

}

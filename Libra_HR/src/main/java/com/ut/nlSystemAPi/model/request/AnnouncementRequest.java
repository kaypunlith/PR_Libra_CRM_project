package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnnouncementRequest extends BaseModel {

	@ApiModelProperty(position = 1)
	@NotNull(message = "Value must not null")
	@NotEmpty(message = "Value must not empty")
	private String title;

	@ApiModelProperty(position = 2)
	@NotNull(message = "Value must not null")
	@NotEmpty(message = "Value must not empty")
	private String content;

	@ApiModelProperty(position = 3)
	@NotNull(message = "Value must not null")
	@NotEmpty(message = "Value must not empty")
	private String schedule;

	@ApiModelProperty(position = 4)
	private String fileName;

	@ApiModelProperty(position = 5)
	private String fileUrl;

	@ApiModelProperty(position = 6)
	private Integer isPublic;

}

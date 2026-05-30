package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class DepartmentUpdateRequest extends DepartmentRequest {

    @ApiModelProperty(position = 10)
    private Long id;

}

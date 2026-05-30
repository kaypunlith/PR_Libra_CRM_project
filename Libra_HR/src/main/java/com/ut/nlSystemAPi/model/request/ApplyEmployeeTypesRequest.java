package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApplyEmployeeTypesRequest extends BaseModel {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private Long employeeTypeId;

    @ApiModelProperty(position = 3, hidden = true)
    private String employeeTypeName;

    @ApiModelProperty(position = 4)
    private String dateFrom;

    @ApiModelProperty(position = 5)
    private String dateTo;

}

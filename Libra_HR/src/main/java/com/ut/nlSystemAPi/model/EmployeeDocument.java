package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeDocument extends BaseModel {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2, hidden = true)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private String fileDocument;

    @ApiModelProperty(position = 4)
    private String filePath;
}

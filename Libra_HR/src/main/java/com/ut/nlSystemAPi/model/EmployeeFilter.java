package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 5)
    private Long groupId;

    @ApiModelProperty(position = 6)
    private Long departmentId;

    @ApiModelProperty(position = 7)
    private Long statusId;

    @ApiModelProperty(position = 8)
    private String dateOfWork;

    @ApiModelProperty(position = 9)
    private String dateOfEmpStatus;

    @ApiModelProperty(position = 7)
    private Long intern;

}

package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeMistake {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private String attachFile;

    @ApiModelProperty(position = 6)
    private String date;

    @ApiModelProperty(position = 7)
    private Boolean isEditMistakeByUser;

    @ApiModelProperty(position = 101, hidden = true)
    private String created;

    @ApiModelProperty(position = 102, hidden = true)
    private Long createdBy;

    @ApiModelProperty(position = 103, hidden = true)
    private String modified;

    @ApiModelProperty(position = 104, hidden = true)
    private Long modifiedBy;

    @ApiModelProperty(position = 106, hidden = true)
    private int isActive;

}

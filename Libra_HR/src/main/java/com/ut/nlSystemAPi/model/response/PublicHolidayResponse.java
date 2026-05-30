package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublicHolidayResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String dateFrom;

    @ApiModelProperty(position = 4)
    private String dateTo;

    @ApiModelProperty(position = 5)
    private String created;

    @ApiModelProperty(position = 6)
    private String modified;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 9)
    private Integer isActive;

}

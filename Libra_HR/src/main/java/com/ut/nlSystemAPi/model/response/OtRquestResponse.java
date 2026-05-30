package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OtRquestResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 1)
    private String employeeNameEn;

    @ApiModelProperty(position = 1)
    private String employeeNameKh;

    @ApiModelProperty(position = 1)
    private String dateTimeFrom;

    @ApiModelProperty(position = 1)
    private String dateTimeTo;

    @ApiModelProperty(position = 1)
    private String dateForm;

    @ApiModelProperty(position = 1)
    private String dateTo;

    @ApiModelProperty(position = 1)
    private String duration;

    @ApiModelProperty(position = 1)
    private String reason;

    @ApiModelProperty(position = 1)
    private Long status;

    @ApiModelProperty(position = 1)
    private String timeFrom;


    @ApiModelProperty(position = 1)
    private String timeTo;

    @ApiModelProperty(position = 1)
    private Float bonusSalary;

    @ApiModelProperty(position = 1)
    private Float totalBonusSalary;

    @ApiModelProperty(position = 1)
    private Long minute;

    @ApiModelProperty(position = 1)
    private Long attendanceType;

    @ApiModelProperty(position = 1)
    private Long attendanceStatus;

    @ApiModelProperty(position = 1)
    private String recordType;

    @ApiModelProperty(position = 1)
    private Float shiftDay;

    @ApiModelProperty(position = 1)
    private Long employeeStatusId;

    @ApiModelProperty(position = 1)
    private String date;

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

package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import org.apache.ibatis.annotations.Mapper;
@Mapper
@Data
public class PaySlipResponse implements Serializable {
    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 1)
    private String employeeNameEn;

    @ApiModelProperty(position = 1)
    private String employeeNameKh;

    @ApiModelProperty(position = 2)
    private Float totalMonFri;

    @ApiModelProperty(position = 3)
    private Float totalSatSun;

    @ApiModelProperty(position = 3)
    private Float totalNight;

    @ApiModelProperty(position = 3)
    private Double baseTotal;

    @ApiModelProperty(position = 3)
    private Float boneScan;

    @ApiModelProperty(position = 4)
    private Float ctScan;

    @ApiModelProperty(position = 4)
    private Float mri;

    @ApiModelProperty(position = 4)
    private Float roll;

    @ApiModelProperty(position = 4)
    private Float smallSurg;

    @ApiModelProperty(position = 4)
    private Float bloc;

    @ApiModelProperty(position = 4)
    private Float other;

    @ApiModelProperty(position = 4)
    private Double AllTotal;



}

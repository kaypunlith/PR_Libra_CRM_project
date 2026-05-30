package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AnnualLeaveResponse {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 6)
    private String employeeNameKh;

    @ApiModelProperty(position = 7)
    private String employeeNameEn;

    @ApiModelProperty(position = 4)
    private Float total;

    @ApiModelProperty(position = 5)
    private String employeeCode;

    @ApiModelProperty(position = 10)
    private List<AnnualLeaveDetailResponse> leaveTypes;

}

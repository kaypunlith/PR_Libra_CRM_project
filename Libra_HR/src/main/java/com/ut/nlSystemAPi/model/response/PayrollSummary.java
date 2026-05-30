package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PayrollSummary implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String departmentName;

    @ApiModelProperty(position = 3)
    private Long settingGroupId;

    @ApiModelProperty(position = 4)
    private Float amountAtm;

    @ApiModelProperty(position = 5)
    private Float amountManual;

//    @ApiModelProperty(position = 4)
//    private List<PayrollSummaryType> amountTypes;

    @ApiModelProperty(position = 5)
    private Float currentMonth;

    @ApiModelProperty(position = 6)
    private Float previousMonth;

    @ApiModelProperty(position = 7)
    private Float amountDifferent;

    @ApiModelProperty(position = 8)
    private String reasonOfIncreased;

    @ApiModelProperty(position = 10)
    private String datePreviousMonth;
}

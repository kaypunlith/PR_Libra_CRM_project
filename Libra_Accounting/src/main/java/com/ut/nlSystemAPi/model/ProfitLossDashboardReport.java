package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfitLossDashboardReport extends BaseModel implements Serializable {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private Long locationId;

    @ApiModelProperty(position = 6)
    private Long customerId;

    @ApiModelProperty(position = 7)
    private Long vendorId;

    @ApiModelProperty(position = 8)
    private Long employeeId;

    @ApiModelProperty(position = 9)
    private Long otherId;

    @ApiModelProperty(position = 10)
    private Long classId;

    @ApiModelProperty(position = 11)
    private Double debit;

    @ApiModelProperty(position = 12)
    private Double credit;

    @ApiModelProperty(position = 12)
    private Long branchId;

}

package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Deposit extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String requestDate;

    @ApiModelProperty(position = 2)
    private String filterMonth;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private Float amountRequest;

    @ApiModelProperty(position = 5, hidden = true)
    private String createdByName;

    @ApiModelProperty(position = 5, hidden = true)
    private String createdDate;

    @ApiModelProperty(position = 6, hidden = true)
    private String modifiedByName;

    @ApiModelProperty(position = 6, hidden = true)
    private String modifiedDate;

    @ApiModelProperty(position = 7, hidden = true)
    private Boolean valid;

    @ApiModelProperty(position = 6)
    private List<DepositDetail> depositDetails;

    @ApiModelProperty(position = 7)
    private List<DepositListByEmployees> depositDetailList;

}

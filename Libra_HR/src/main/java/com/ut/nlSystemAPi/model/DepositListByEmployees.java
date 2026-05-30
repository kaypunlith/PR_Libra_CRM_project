package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.response.DepositHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DepositListByEmployees implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeesId;

    @ApiModelProperty(position = 3)
    private String filterMonth;

    @ApiModelProperty(position = 3)
    private String employeesCode;

    @ApiModelProperty(position = 4)
    private String nameKh;

    @ApiModelProperty(position = 5)
    private String nameEn;

    @ApiModelProperty(position = 6)
    private String positionName;

    @ApiModelProperty(position = 7)
    private Float amountRequest;

    @ApiModelProperty(position = 8)
    private Float depositAmount;

    @ApiModelProperty(position = 8)
    private Long status;

    @ApiModelProperty(position = 9)
    private String remark;

    @ApiModelProperty(position = 10)
    private List<DepositHistory> depositHistoryList;

}

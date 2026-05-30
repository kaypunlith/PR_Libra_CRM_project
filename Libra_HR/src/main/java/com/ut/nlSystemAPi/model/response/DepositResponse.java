package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DepositResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

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

    @ApiModelProperty(position = 6)
    private List<DepositHistory> depositHistoryList;

}

package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProvidentFundDetailResponse implements Serializable {

    @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 20)
    private Long employeeId;

    @ApiModelProperty(position = 30)
    private String employeesCode;

    @ApiModelProperty(position = 40)
    private String filterMonth;

    @ApiModelProperty(position = 50)
    private String nameKh;

    @ApiModelProperty(position = 60)
    private String nameEn;

    @ApiModelProperty(position = 70)
    private String positionName;

    @ApiModelProperty(position = 71)
    private String departmentName;

    @ApiModelProperty(position = 72)
    private String bankAccount;

    @ApiModelProperty(position = 73)
    private String accountNumber;

    @ApiModelProperty(position = 80)
    private String telephone;

    @ApiModelProperty(position = 80)
    private String gender;

    @ApiModelProperty(position = 90)
    private Long totalOfMonth;

    @ApiModelProperty(position = 100)
    private Float providentFundAmount;

    @ApiModelProperty(position = 110)
    private Float bonusHaftProfundAmount;

    @ApiModelProperty(position = 120)
    private Float amountRequest;

    @ApiModelProperty(position = 130)
    private Long status;

    @ApiModelProperty(position = 140)
    private List<ProvidentFundHistory> providentFundHistoryList;

}

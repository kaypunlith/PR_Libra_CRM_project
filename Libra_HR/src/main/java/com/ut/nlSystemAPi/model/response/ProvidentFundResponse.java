package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProvidentFundResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private String requestDate;

    @ApiModelProperty(position = 2)
    private String filterMonth;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 3)
    private String employeesCode;

    @ApiModelProperty(position = 4)
    private String nameKh;

    @ApiModelProperty(position = 5)
    private String nameEn;

    @ApiModelProperty(position = 6)
    private String positionName;

    @ApiModelProperty(position = 7)
    private String departmentName;

    @ApiModelProperty(position = 8)
    private String bankAccount;

    @ApiModelProperty(position = 9)
    private String accountNumber;

    @ApiModelProperty(position = 10)
    private String telephone;

    @ApiModelProperty(position = 9)
    private Long gender;

    @ApiModelProperty(position = 11)
    private Long totalOfMonth;

    @ApiModelProperty(position = 12)
    private Float providentFundAmount;

    @ApiModelProperty(position = 13)
    private Float bonusHaftProfundAmount;

    @ApiModelProperty(position = 15)
    private List<ProvidentFundDetailResponse> providentFundDetailList;

    @ApiModelProperty(position = 16)
    private List<ProvidentFundHistory> providentFundHistoryList;

}

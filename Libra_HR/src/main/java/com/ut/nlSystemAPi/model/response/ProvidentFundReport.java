package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProvidentFundReport implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String employeesCode;

    @ApiModelProperty(position = 3)
    private String nameKh;

    @ApiModelProperty(position = 4)
    private String nameEn;

    @ApiModelProperty(position = 5)
    private String gender;

    @ApiModelProperty(position = 6)
    private String dateOfWork;

    @ApiModelProperty(position = 7)
    private String positionName;

    @ApiModelProperty(position = 8)
    private Float balance;

    @ApiModelProperty(position = 9)
    private Float providentRequest;

    @ApiModelProperty(position = 10)
    private String dateRequest;

    @ApiModelProperty(position = 11)
    private List<ProFundTotalYearList> totalAmountList;

    @ApiModelProperty(position = 12)
    private List<ProFundYearList> yearLists;

}

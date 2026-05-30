package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyCodeDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String adjCode;

    @ApiModelProperty(position = 4)
    private String requestCode;

    @ApiModelProperty(position = 5)
    private String toCode;

    @ApiModelProperty(position = 6)
    private String trCode;

    @ApiModelProperty(position = 7)
    private String posCode;

    @ApiModelProperty(position = 8)
    private String posRepCode;

    @ApiModelProperty(position = 9)
    private String quoteCode;

    @ApiModelProperty(position = 10)
    private String soCode;

    @ApiModelProperty(position = 11)
    private String invCode;

    @ApiModelProperty(position = 12)
    private String invRepCode;

    @ApiModelProperty(position = 13)
    private String dnCode;

    @ApiModelProperty(position = 14)
    private String receivePayCode;

    @ApiModelProperty(position = 15)
    private String cmCode;

    @ApiModelProperty(position = 16)
    private String cmRepCode;

    @ApiModelProperty(position = 17)
    private String prCode;

    @ApiModelProperty(position = 18)
    private String poCode;

    @ApiModelProperty(position = 19)
    private String pbCode;

    @ApiModelProperty(position = 20)
    private String pbRepCode;

    @ApiModelProperty(position = 21)
    private String brCode;

    @ApiModelProperty(position = 22)
    private String brRepCode;

    @ApiModelProperty(position = 23)
    private String payBillCode;

    @ApiModelProperty(position = 24)
    private String landedCostCode;

    @ApiModelProperty(position = 25)
    private String landedCostReceiptCode;

    @ApiModelProperty(position = 26)
    private String bomCode;

    @ApiModelProperty(position = 27)
    private String boqCode;

}

package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesInvoiceDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 6)
    private Double balance;

    @ApiModelProperty(position = 7)
    private Integer status;

    @ApiModelProperty(position = 8)
    private List<SalesInvoiceDropdownDetailResponse> details;

}

package com.ut.nlSystemAPi.model.response.Logistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LogisticResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 4)
    private String deliveryDate;

    @ApiModelProperty(position = 5)
    private String logisticDeliveryDate;

    @ApiModelProperty(position = 6)
    private String code;

    @ApiModelProperty(position = 6)
    private String invoiceCode;

    @ApiModelProperty(position = 7)
    private String dnCode;

    @ApiModelProperty(position = 7)
    private String note;

    @ApiModelProperty(position = 7)
    private String date;

    @ApiModelProperty(position = 8)
    private Long organizationId;

    @ApiModelProperty(position = 8)
    private String organizationName;

    @ApiModelProperty(position = 8)
    private String warehouseName;

    @ApiModelProperty(position = 9)
    private String logisticReceiveNumber;

    @ApiModelProperty(position = 10)
    private Integer status;

    @ApiModelProperty(position = 11)
    private String created;

    @ApiModelProperty(position = 12)
    private String createdBy;

    @ApiModelProperty(position = 13)
    private String modified;

    @ApiModelProperty(position = 14)
    private String modifiedBy;

    @ApiModelProperty(position = 15)
    private List<LogisticDetailResponse> details;

    // Temporary Status
    private Integer statusLogistic;

    private Integer statusInvoice;

}

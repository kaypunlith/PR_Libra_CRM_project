package com.ut.nlSystemAPi.model.response.ARSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ARScheduleTotalResponse {

    @ApiModelProperty(position = 2)
    private Long week;

    @ApiModelProperty(position = 9)
    private String name;

    @ApiModelProperty(position = 2)
    private Long total;

    @ApiModelProperty(position = 9)
    private Double amount;

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private Double totalAmountBookingOverdue;

    @ApiModelProperty(position = 4)
    private Double totalAmountBookingOverdueSW;

    @ApiModelProperty(position = 5)
    private Double totalAmountBooked;

    @ApiModelProperty(position = 6)
    private Double totalAmountBookedByUser;

    @ApiModelProperty(position = 7)
    private Long totalOrganization;

    @ApiModelProperty(position = 8)
    private Double totalOrganizationAmount;

    @ApiModelProperty(position = 9)
    private Double totalAmountThisWeek;

    @ApiModelProperty(position = 9)
    private List<ARScheduleTotalResponse> bookingOverdue;

    @ApiModelProperty(position = 9)
    private List<ARScheduleTotalResponse> invoiceBooked;

    @ApiModelProperty(position = 9)
    private List<ARScheduleTotalResponse> invoiceBookedByUser;

    @ApiModelProperty(position = 9)
    private List<ARScheduleTotalResponse> invoiceByWeek;

}

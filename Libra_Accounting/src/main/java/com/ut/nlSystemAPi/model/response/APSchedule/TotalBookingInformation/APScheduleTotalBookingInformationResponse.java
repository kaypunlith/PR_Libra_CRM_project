package com.ut.nlSystemAPi.model.response.APSchedule.TotalBookingInformation;

import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleTotalResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class APScheduleTotalBookingInformationResponse {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(position = 9)
    private String name;

    @ApiModelProperty(position = 2)
    private Long total;

    @ApiModelProperty(position = 1)
    private Long customerId;

    @ApiModelProperty(position = 9)
    private Double amount;

    @ApiModelProperty(position = 1)
    private Double totalAmountBookingOverdue;

    @ApiModelProperty(position = 1)
    private Double totalAmountPBBookingOverdue;

    @ApiModelProperty(position = 2)
    private Double totalAmountPBBooked;

    @ApiModelProperty(position = 3)
    private Double totalAmountPBBookedByUser;

    @ApiModelProperty(position = 4)
    private Double totalAmountPBBookedByWeek;

    @ApiModelProperty(position = 3)
    private Double totalBalance;

    @ApiModelProperty(position = 3)
    private Long totalOrganization;

    @ApiModelProperty(position = 3)
    private Double totalOrganizationAmount;

    @ApiModelProperty(position = 9)
    private List<APScheduleTotalBookingInformationResponse> bookingOverdue;

    @ApiModelProperty(position = 9)
    private List<APScheduleTotalBookingInformationResponse> invoiceBooked;

    @ApiModelProperty(position = 9)
    private List<APScheduleTotalBookingInformationResponse> invoiceBookedByUser;

    @ApiModelProperty(position = 9)
    private List<APScheduleTotalBookingInformationResponse> invoiceByWeek;


}


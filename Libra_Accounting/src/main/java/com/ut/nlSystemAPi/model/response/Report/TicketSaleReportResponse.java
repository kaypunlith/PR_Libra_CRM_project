package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TicketSaleReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private Long tourist1Day;

    @ApiModelProperty(position = 4)
    private Long tourist3Days;

    @ApiModelProperty(position = 5)
    private Long tourist7Days;

    @ApiModelProperty(position = 6)
    private Long totalTourist;

    @ApiModelProperty(position = 7)
    private Double revenue1Day;

    @ApiModelProperty(position = 8)
    private Double revenue3Days;

    @ApiModelProperty(position = 9)
    private Double revenue7Days;

    @ApiModelProperty(position = 10)
    private Double totalTouristRevenue;

    @ApiModelProperty(position = 11)
    private Long touristOnline1Day;

    @ApiModelProperty(position = 12)
    private Long touristOnline3Days;

    @ApiModelProperty(position = 13)
    private Long touristOnline7Days;

    @ApiModelProperty(position = 14)
    private Long totalTouristOnline;

    @ApiModelProperty(position = 15)
    private Double totalTouristOnlineRevenue;

    @ApiModelProperty(position = 16)
    private Long touristLongPass100;

    @ApiModelProperty(position = 17)
    private Long touristLongPass150;

    @ApiModelProperty(position = 18)
    private Long touristLongPass200;

    @ApiModelProperty(position = 19)
    private Long totalTouristLongPass;

    @ApiModelProperty(position = 20)
    private Double totalTouristLongPassRevenue;

    @ApiModelProperty(position = 21)
    private Long isActive;

}
















package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TouristByTourAgentOrFitReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private Long type;

    @ApiModelProperty(position = 4)
    private String tourAgent;

    @ApiModelProperty(position = 5)
    private Long teenager1DayTicket;

    @ApiModelProperty(position = 6)
    private Long teenager3DaysTicket;

    @ApiModelProperty(position = 7)
    private Long teenager7DaysTicket;

    @ApiModelProperty(position = 8)
    private Long adult1DayTicket;

    @ApiModelProperty(position = 9)
    private Long adult3DaysTicket;

    @ApiModelProperty(position = 10)
    private Long adult7DaysTicket;

    @ApiModelProperty(position = 11)
    private Long pensioner1DayTicket;

    @ApiModelProperty(position = 12)
    private Long pensioner3DaysTicket;

    @ApiModelProperty(position = 13)
    private Long pensioner7DaysTicket;

    @ApiModelProperty(position = 14)
    private Long totalTeenagerTicket;

    @ApiModelProperty(position = 15)
    private Long totalAdultTicket;

    @ApiModelProperty(position = 16)
    private Long totalPensionerTicket;

    @ApiModelProperty(position = 17)
    private Long totalAllAge;

}

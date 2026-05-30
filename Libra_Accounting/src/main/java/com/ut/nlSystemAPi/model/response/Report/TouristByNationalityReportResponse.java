package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TouristByNationalityReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private Long nationalityId;

    @ApiModelProperty(position = 3)
    private String nationalityName;

    @ApiModelProperty(position = 4)
    private Long underAge12;

    @ApiModelProperty(position = 5)
    private Long age12To25;

    @ApiModelProperty(position = 5)
    private Long age26To59;

    @ApiModelProperty(position = 6)
    private Long age60Up;

    @ApiModelProperty(position = 7)
    private Long totalAge;

    @ApiModelProperty(position = 8)
    private Long female;

    @ApiModelProperty(position = 9)
    private Long male;

    @ApiModelProperty(position = 10)
    private Long totalGender;

}

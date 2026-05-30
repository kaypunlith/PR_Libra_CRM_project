package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProfileNationalVisitorReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long userMobileId;

    @ApiModelProperty(position = 3)
    private String userMobileName;

    @ApiModelProperty(position = 4)
    private String clientId;

    @ApiModelProperty(position = 5)
    private String visitorCode;

    @ApiModelProperty(position = 6)
    private String qrCode;

    @ApiModelProperty(position = 7)
    private Long zoneId;

    @ApiModelProperty(position = 8)
    private String zoneName;

    @ApiModelProperty(position = 9)
    private Long provinceId;

    @ApiModelProperty(position = 10)
    private String provinceName;

    @ApiModelProperty(position = 11)
    private Long durationId;

    @ApiModelProperty(position = 12)
    private String durationName;

    @ApiModelProperty(position = 13)
    private Long purposeOfVisitId;

    @ApiModelProperty(position = 14)
    private String purposeOfVisitName;

    @ApiModelProperty(position = 15)
    private Long transportationId;

    @ApiModelProperty(position = 16)
    private String transportationName;

    @ApiModelProperty(position = 17)
    private Long people;

    @ApiModelProperty(position = 18)
    private Long under12Year;

    @ApiModelProperty(position = 19)
    private Long age12To25;

    @ApiModelProperty(position = 20)
    private Long age26To59;

    @ApiModelProperty(position = 21)
    private Long age60AndUp;

    @ApiModelProperty(position = 22)
    private String datetimeScan;

    @ApiModelProperty(position = 23)
    private String datetimeScanFormat;

}

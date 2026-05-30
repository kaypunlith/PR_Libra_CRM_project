package com.ut.nlSystemAPi.model.entity.Lead;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Lead extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 3)
    private String leadCode;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private Long leadGroupId;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 7)
    private String nameKh;

    @ApiModelProperty(position = 8)
    private String lats;

    @ApiModelProperty(position = 9)
    private String longs;

    @ApiModelProperty(position = 10)
    private String telephone;

    @ApiModelProperty(position = 11)
    private String mobile;

    @ApiModelProperty(position = 12)
    private String alternateMobile;

    @ApiModelProperty(position = 13)
    private String email;

    @ApiModelProperty(position = 14)
    private String address;

    @ApiModelProperty(position = 15)
    private Long leadContactId;

    @ApiModelProperty(position = 16)
    private Long businessTypeId;

    @ApiModelProperty(position = 17)
    private Long businessActivityId;

    @ApiModelProperty(position = 18)
    private Integer customerType;

    @ApiModelProperty(position = 19)
    private Long countryId;

    @ApiModelProperty(position = 20)
    private String fax;

    @ApiModelProperty(position = 21)
    private String vat;

    @ApiModelProperty(position = 22)
    private Long paymentTermId;

    @ApiModelProperty(position = 23)
    private String paymentMonthly;

    @ApiModelProperty(position = 24)
    private Double limitCredit;

    @ApiModelProperty(position = 25)
    private Long limitNumberInvoice;

    @ApiModelProperty(position = 26)
    private Integer recurrence;

    @ApiModelProperty(position = 27)
    private Integer displayOnInvoice;

    @ApiModelProperty(position = 28)
    private Integer settingInvoiceTax;

    @ApiModelProperty(position = 29)
    private String houseNo;

    @ApiModelProperty(position = 30)
    private Long streetId;

    @ApiModelProperty(position = 31)
    private Long provinceId;

    @ApiModelProperty(position = 32)
    private Long districtId;

    @ApiModelProperty(position = 33)
    private Long communeId;

    @ApiModelProperty(position = 34)
    private Long villageId;

    @ApiModelProperty(position = 35)
    private String customerContract;

    @ApiModelProperty(position = 36)
    private String customerContractUrl;

    @ApiModelProperty(position = 37)
    private String customerTerminate;

    @ApiModelProperty(position = 38)
    private String customerTerminateUrl;

    @ApiModelProperty(position = 39)
    private String customerSurvey;

    @ApiModelProperty(position = 40)
    private String customerSurveyUrl;
}

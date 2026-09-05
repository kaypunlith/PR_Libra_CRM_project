package com.ut.nlSystemAPi.model.response.Lead;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeadResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String leadGroupName;

    @ApiModelProperty(position = 3)
    private String leadCode;

    @ApiModelProperty(position = 3)
    private Long sourceId;


    @ApiModelProperty(position = 3)
    private String sourceName;


    @ApiModelProperty(position = 4)
    private String leadName;

    @ApiModelProperty(position = 5)
    private String leadNameKh;

    @ApiModelProperty(position = 6)
    private String telephone;

    @ApiModelProperty(position = 7)
    private String mobile;

    @ApiModelProperty(position = 8)
    private String alternateMobile;

    @ApiModelProperty(position = 9)
    private String email;

    @ApiModelProperty(position = 10)
    private String contactName;

    @ApiModelProperty(position = 11)
    private String lats;

    @ApiModelProperty(position = 12)
    private String longs;

    @ApiModelProperty(position = 13)
    private String photo;

    @ApiModelProperty(position = 14)
    private String address;

    @ApiModelProperty(position = 15)
    private String created;

    @ApiModelProperty(position = 16)
    private String createdBy;

    @ApiModelProperty(position = 17)
    private String modified;

    @ApiModelProperty(position = 18)
    private String modifiedBy;

    @ApiModelProperty(position = 19)
    private List<LeadDetailResponse> companies;

    @ApiModelProperty(position = 20)
    private List<LeadDetailResponse> leadGroups;

    @ApiModelProperty(position = 21)
    private List<LeadDetailResponse> leadContacts;

    @ApiModelProperty(position = 22)
    private List<LeadDetailResponse> divisionInformation;

    @ApiModelProperty(position = 23)
    private Long businessTypeId;

    @ApiModelProperty(position = 24)
    private String businessTypeName;

    @ApiModelProperty(position = 25)
    private Long businessActivityId;

    @ApiModelProperty(position = 26)
    private String businessActivityName;

    @ApiModelProperty(position = 27)
    private Integer customerType;

    @ApiModelProperty(position = 28)
    private Long countryId;

    @ApiModelProperty(position = 29)
    private String countryName;

    @ApiModelProperty(position = 30)
    private String fax;

    @ApiModelProperty(position = 31)
    private String vat;

    @ApiModelProperty(position = 32)
    private Long paymentTermId;

    @ApiModelProperty(position = 33)
    private String paymentTermName;

    @ApiModelProperty(position = 34)
    private String paymentMonthly;

    @ApiModelProperty(position = 35)
    private Double limitCredit;

    @ApiModelProperty(position = 36)
    private Long limitNumberInvoice;

    @ApiModelProperty(position = 37)
    private Integer recurrence;

    @ApiModelProperty(position = 38)
    private Integer displayOnInvoice;

    @ApiModelProperty(position = 39)
    private Integer settingInvoiceTax;

    @ApiModelProperty(position = 40)
    private String houseNo;

    @ApiModelProperty(position = 41)
    private Long streetId;

    @ApiModelProperty(position = 42)
    private String streetName;

    @ApiModelProperty(position = 43)
    private Long provinceId;

    @ApiModelProperty(position = 44)
    private String provinceName;

    @ApiModelProperty(position = 45)
    private Long districtId;

    @ApiModelProperty(position = 46)
    private String districtName;

    @ApiModelProperty(position = 47)
    private Long communeId;

    @ApiModelProperty(position = 48)
    private String communeName;

    @ApiModelProperty(position = 49)
    private Long villageId;

    @ApiModelProperty(position = 50)
    private String villageName;

    @ApiModelProperty(position = 51)
    private List<LeadDetailResponse> customerContract;

    @ApiModelProperty(position = 52)
    private List<LeadDetailResponse> customerTerminate;

    @ApiModelProperty(position = 53)
    private List<LeadDetailResponse> customerSurvey;
}

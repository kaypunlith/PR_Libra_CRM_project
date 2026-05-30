package com.ut.nlSystemAPi.model.request.Lead;

import com.ut.nlSystemAPi.model.base.FileBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeadConvertRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 3)
    private List<Long> companies;

    @ApiModelProperty(position = 4)
    private Long businessTypeId;

    @ApiModelProperty(position = 5)
    private Long businessActivityId;

    @ApiModelProperty(position = 6)
    private Integer customerType;

    @ApiModelProperty(position = 7)
    private Long countryId;

    @ApiModelProperty(position = 8)
    private List<Long> organizationGroups;

    @ApiModelProperty(position = 9)
    private String name;

    @ApiModelProperty(position = 10)
    private String nameKh;

    @ApiModelProperty(position = 11)
    private String lats;

    @ApiModelProperty(position = 12)
    private String longs;

    @ApiModelProperty(position = 13)
    private String telephone;

    @ApiModelProperty(position = 14)
    private String mobile;

    @ApiModelProperty(position = 15)
    private String alternateMobile;

    @ApiModelProperty(position = 16)
    private String email;

    @ApiModelProperty(position = 17)
    private String fax;

    @ApiModelProperty(position = 18)
    private String vat;

    @ApiModelProperty(position = 19)
    private Long paymentTermId;

    @ApiModelProperty(position = 20)
    private String paymentMonthly;

    @ApiModelProperty(position = 21)
    private Double limitCredit;

    @ApiModelProperty(position = 22)
    private Long limitNumberInvoice;

    @ApiModelProperty(position = 23)
    private Integer recurrence;

    @ApiModelProperty(position = 24)
    private Integer displayOnInvoice;

    @ApiModelProperty(position = 25)
    private Integer settingInvoiceTax;

    @ApiModelProperty(position = 26)
    private List<Long> organizationContacts;

    @ApiModelProperty(position = 27)
    private String houseNo;

    @ApiModelProperty(position = 28)
    private Long streetId;

    @ApiModelProperty(position = 29)
    private Long provinceId;

    @ApiModelProperty(position = 30)
    private Long districtId;

    @ApiModelProperty(position = 31)
    private Long communeId;

    @ApiModelProperty(position = 32)
    private Long villageId;

    @ApiModelProperty(position = 33)
    private String address;

    @ApiModelProperty(position = 34)
    private List<FileBaseEntity> customerContract;

    @ApiModelProperty(position = 35)
    private List<FileBaseEntity> customerTerminate;

    @ApiModelProperty(position = 36)
    private List<FileBaseEntity> customerSurvey;
}

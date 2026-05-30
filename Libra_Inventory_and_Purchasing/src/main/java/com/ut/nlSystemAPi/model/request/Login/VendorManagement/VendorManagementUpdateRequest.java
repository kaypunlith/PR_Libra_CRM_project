package com.ut.nlSystemAPi.model.request.Login.VendorManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VendorManagementUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private List<Long> companies;

    @ApiModelProperty(position = 2)
    private String vendorCode;

    @ApiModelProperty(position = 3)
    private String workTelephone;

    @ApiModelProperty(position = 4)
    private String emailAddress;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 6)
    private String otherTelephone;

    @ApiModelProperty(position = 7)
    private String address;

    @ApiModelProperty(position = 8)
    private List<Long> vendorGroups;

    @ApiModelProperty(position = 9)
    private Long countryId;

    @ApiModelProperty(position = 10)
    private String faxNumber;

    @ApiModelProperty(position = 11)
    private Long paymentTermId;

    @ApiModelProperty(position = 12)
    private Long vatId;

    @ApiModelProperty(position = 13)
    private List<Long> contacts;

    @ApiModelProperty(position = 14)
    private List<Long>currencies;

    @ApiModelProperty(position = 15)
    private VendorPhoto photo;
}

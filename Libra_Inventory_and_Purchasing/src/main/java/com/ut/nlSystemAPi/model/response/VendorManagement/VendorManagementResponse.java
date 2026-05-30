package com.ut.nlSystemAPi.model.response.VendorManagement;

import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VendorManagementResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private List<VendorDetailResponse> companies;

    @ApiModelProperty(position = 2)
    private String vendorCode;

    @ApiModelProperty(position = 3)
    private String officeTelephone;

    @ApiModelProperty(position = 4)
    private String emailAddress;

    @ApiModelProperty(position = 5)
    private String vendorName;

    @ApiModelProperty(position = 6)
    private String otherTelephone;

    @ApiModelProperty(position = 7)
    private String address;

    @ApiModelProperty(position = 8)
    private Double balance;

    @ApiModelProperty(position = 8)
    private List<VendorDetailResponse> vendorGroups;

    @ApiModelProperty(position = 9)
    private Long countryId;

    @ApiModelProperty(position = 9)
    private String countryName;

    @ApiModelProperty(position = 10)
    private String faxNumber;

    @ApiModelProperty(position = 11)
    private Long paymentTermId;

    @ApiModelProperty(position = 11)
    private String paymentTermName;

    @ApiModelProperty(position = 12)
    private Long vatId;

    @ApiModelProperty(position = 12)
    private String vatName;

    @ApiModelProperty(position = 13)
    private List<VendorDetailResponse> contacts;

    @ApiModelProperty(position = 14)
    private List<VendorDetailResponse> currencies;

    @ApiModelProperty(position = 15)
    private VendorPhoto photo;

    @ApiModelProperty(position = 16)
    private String created;

    @ApiModelProperty(position = 17)
    private String createdBy;

    @ApiModelProperty(position = 18)
    private String modified;

    @ApiModelProperty(position = 19)
    private String modifiedBy;

}

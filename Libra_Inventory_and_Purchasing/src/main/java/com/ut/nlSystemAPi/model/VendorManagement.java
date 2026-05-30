package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VendorManagement extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String vendorCode;

    @ApiModelProperty(position = 4)
    private String workTelephone;

    @ApiModelProperty(position = 5)
    private String emailAddress;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 7)
    private String contactTelephone;

    @ApiModelProperty(position = 8)
    private String Address;

    @ApiModelProperty(position = 9)
    private Long vendorGroupId;

    @ApiModelProperty(position = 10)
    private Long countryId;

    @ApiModelProperty(position = 11)
    private String faxNumber;

    @ApiModelProperty(position = 11)
    private Long vatId;

    @ApiModelProperty(position = 12)
    private Long paymentTermId;

    @ApiModelProperty(position = 13)
    private List<Long> vendorContactId;

    @ApiModelProperty(position = 14)
    private List<Long> currencyId;

    @ApiModelProperty(position = 15)
    private String photo;

    @ApiModelProperty(position = 15)
    private String photoName;


}

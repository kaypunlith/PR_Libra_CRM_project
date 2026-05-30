package com.ut.nlSystemAPi.model.response.VendorGroup;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyDropdownResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.VendorDropdownResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VendorGroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long typeId;

    @ApiModelProperty(position = 3)
    private String typeName;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private List<CompanyDropdownResponse> companies;

    @ApiModelProperty(position = 6)
    private List<VendorDropdownResponse> vendors;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 9)
    private String createdBy;

    @ApiModelProperty(position = 10)
    private String modified;

    @ApiModelProperty(position = 11)
    private String modifiedBy;

}

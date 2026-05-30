package com.ut.nlSystemAPi.model.filter.Report.Organization;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CustomerAddressReportFilter extends Filter {

    @ApiModelProperty(position = 12)
    private Long createdBy;

    @ApiModelProperty(position = 13)
    private Long provinceId;

    @ApiModelProperty(position = 14)
    private Long organizationGroupId;

    @ApiModelProperty(position = 15)
    private Long organizationId;

    @ApiModelProperty(position = 16)
    private Long districtId;

    @ApiModelProperty(position = 17)
    private Long villageId;

    @ApiModelProperty(position = 18)
    private Long communeId;

}

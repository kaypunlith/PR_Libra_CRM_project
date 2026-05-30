package com.ut.nlSystemAPi.model.filter.Report.Organization;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerAddressListReportFilter extends Filter {

    @ApiModelProperty(position = 10)
    private String searchText;

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 11)
    private String dateTo;

    @ApiModelProperty(position = 12)
    private String code;

    @ApiModelProperty(position = 12)
    private String other;

    @ApiModelProperty(position = 13)
    private Long createdBy;

    @ApiModelProperty(position = 14)
    private Long provinceId;

    @ApiModelProperty(position = 15)
    private Long organizationGroupId;

    @ApiModelProperty(position = 16)
    private Long organizationId;

    @ApiModelProperty(position = 17)
    private Long districtId;

    @ApiModelProperty(position = 18)
    private Long villageId;

    @ApiModelProperty(position = 19)
    private Long communeId;
}
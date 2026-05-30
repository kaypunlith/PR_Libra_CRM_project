package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JournalEntryFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long companyId;

    @ApiModelProperty(position = 10)
    private Long createBy;

    @ApiModelProperty(position = 10)
    private Long customerId;

    @ApiModelProperty(position = 10)
    private Long vendorId;

    @ApiModelProperty(position = 10)
    private Long otherId;

    @ApiModelProperty(position = 10)
    private Long classId;

    @ApiModelProperty(position = 11)
    private String reference;

    @ApiModelProperty(position = 12)
    private String dateFrom;

    @ApiModelProperty(position = 13)
    private String dateTo;

    @ApiModelProperty(position = 14)
    private String status;

    @ApiModelProperty(position = 14)
    private Long branchId;

    @ApiModelProperty(position = 14)
    private Long chartAccountId;

    @ApiModelProperty(position = 14)
    private Long chartAccountGroupId;

    @ApiModelProperty(position = 15, hidden = true)
    private Long userId;

}

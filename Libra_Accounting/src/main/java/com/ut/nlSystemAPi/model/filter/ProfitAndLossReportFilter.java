package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfitAndLossReportFilter extends Filter {
    @ApiModelProperty(position = 10)
    private String dateFrom;

    @ApiModelProperty(position =12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long companyId;

    @ApiModelProperty(position = 14)
    private List<Long> branchId;

    @ApiModelProperty(position = 15)
    private Long organizationId;

    @ApiModelProperty(position = 16)
    private Long vendorId;

    @ApiModelProperty(position = 18)
    private Long other;

    @ApiModelProperty(position = 19)
    private Long classId;

    @ApiModelProperty(position = 19)
    private Long emptyValue;

    @ApiModelProperty(position = 19)
    private Long column;

    @ApiModelProperty(position = 21, hidden = true)
    private Long userId;

    @ApiModelProperty(position =12, hidden = true)
    private String tableName;


}

package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ARDetailReportFilter extends Filter {
    @ApiModelProperty(position = 6)
    private String date;

    @ApiModelProperty(position = 7)
    private Long intervalDay;

    @ApiModelProperty(position = 8)
    private Long throughDay;

    @ApiModelProperty(position = 9)
    private List<Long> branchId;

    @ApiModelProperty(position = 10)
    private Long groupId;

    @ApiModelProperty(position = 11)
    private Long vendorId;

    @ApiModelProperty(position = 12)
    private Long organizationId;

    @ApiModelProperty(position = 13, hidden = true)
    private Long userId;
}

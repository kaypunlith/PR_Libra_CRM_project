package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DashboardFilter extends Filter {
    @ApiModelProperty(position = 5,hidden = true)
    private Long userId;


    @ApiModelProperty(position = 6)
    private List<Long> branchId;

    @ApiModelProperty(position = 7)
    private Long month;

    @ApiModelProperty(position = 8)
    private String dateFrom;

    @ApiModelProperty(position = 8)
    private String dateTo;

}

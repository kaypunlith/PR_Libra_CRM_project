package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.FilterBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostToJournalFilter extends FilterBase {
    @ApiModelProperty(position = 6, hidden = true)
    private Long accountId;
    @ApiModelProperty(position = 6)
    private Long companyId;
    @ApiModelProperty(position = 6)
    private Long branchId;
    @ApiModelProperty(position = 7)
    private String date;
}

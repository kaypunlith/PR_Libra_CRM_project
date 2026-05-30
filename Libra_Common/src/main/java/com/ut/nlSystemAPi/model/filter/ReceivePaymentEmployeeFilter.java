package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.FilterBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReceivePaymentEmployeeFilter extends FilterBase {

    @ApiModelProperty(position = 5)
    private Long groupId;

    @ApiModelProperty(position = 6)
    private Long companyId;

    @ApiModelProperty(position = 7)
    private Long employeeId;

    @ApiModelProperty(position = 8)
    private Long branchId;
}

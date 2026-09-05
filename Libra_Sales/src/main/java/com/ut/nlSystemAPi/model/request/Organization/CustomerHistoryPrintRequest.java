package com.ut.nlSystemAPi.model.request.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerHistoryPrintRequest {
    @ApiModelProperty(position = 1, required = true, notes = "1: Close sale, 2: Increase, 3: Reduce, 4: Terminate, 5: Extra service")
    private Integer type;

    @ApiModelProperty(position = 2, required = true, notes = "quotationId or terminateId")
    private Long referenceId;
}

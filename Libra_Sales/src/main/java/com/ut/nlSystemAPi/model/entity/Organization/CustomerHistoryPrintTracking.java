package com.ut.nlSystemAPi.model.entity.Organization;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerHistoryPrintTracking extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2, notes = "1: Close sale, 2: Increase, 3: Reduce, 4: Terminate, 5: Extra service")
    private Integer type;

    @ApiModelProperty(position = 3, notes = "quotationId or terminateId")
    private Long referenceId;

}

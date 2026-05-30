package com.ut.nlSystemAPi.model.request.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionUpdate extends InspectionRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}

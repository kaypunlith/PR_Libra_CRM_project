package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityAddMoreSaveResponse {

    @ApiModelProperty(position = 1)
    private Boolean activityTaskSaved;
}

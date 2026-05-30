package com.ut.nlSystemAPi.model.request.LeadContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadContactUpdateRequest extends LeadContactRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}

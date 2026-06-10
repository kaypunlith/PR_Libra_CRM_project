package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTaskUpdateRequest extends SupportTaskRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}

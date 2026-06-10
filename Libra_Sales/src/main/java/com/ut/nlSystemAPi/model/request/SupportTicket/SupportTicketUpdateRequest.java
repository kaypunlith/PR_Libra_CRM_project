package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTicketUpdateRequest extends SupportTicketRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long oldStageId;
}

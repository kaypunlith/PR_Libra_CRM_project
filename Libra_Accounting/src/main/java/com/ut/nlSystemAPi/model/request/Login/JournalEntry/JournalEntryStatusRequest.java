package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class JournalEntryStatusRequest {

    @ApiModelProperty(position = 3)
    private Long id;

    @ApiModelProperty(position = 4)
    private Long status;

}

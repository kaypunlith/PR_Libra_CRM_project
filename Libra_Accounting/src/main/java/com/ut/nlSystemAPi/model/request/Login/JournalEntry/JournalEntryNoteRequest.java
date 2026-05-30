package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class JournalEntryNoteRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String note;

}

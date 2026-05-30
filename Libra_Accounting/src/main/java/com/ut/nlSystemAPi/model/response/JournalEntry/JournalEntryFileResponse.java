package com.ut.nlSystemAPi.model.response.JournalEntry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JournalEntryFileResponse {

    @JsonIgnore
    @ApiModelProperty(position = 1, hidden = true)
    private Long journalEntryId;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String url;

}

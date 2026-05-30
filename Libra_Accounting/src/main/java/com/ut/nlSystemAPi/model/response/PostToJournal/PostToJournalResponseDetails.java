package com.ut.nlSystemAPi.model.response.PostToJournal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostToJournalResponseDetails {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Double accumDeprAmount;

    @ApiModelProperty(position = 3)
    private Double depreciationDayAmount;

}

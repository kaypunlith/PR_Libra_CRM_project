package com.ut.nlSystemAPi.model.request.Login.PostToJournal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostToJournalRequest {
    @ApiModelProperty(position = 1)
    private String date;
    @ApiModelProperty(position = 2)
    private Long companyId;
    @ApiModelProperty(position = 2)
    private Long branchId;
    @ApiModelProperty(position = 3)
    private String reference;
    @ApiModelProperty(position = 4)
    private String note;
    @ApiModelProperty(position = 5)
    private List<PostToJournalRequestDetails> details;
}

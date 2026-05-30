package com.ut.nlSystemAPi.model.request.Login.PayBillJournal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayBillJournalFileRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 3)
    private String url;

}

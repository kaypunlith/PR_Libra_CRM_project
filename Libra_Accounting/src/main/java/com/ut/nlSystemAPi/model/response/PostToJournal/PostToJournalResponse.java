package com.ut.nlSystemAPi.model.response.PostToJournal;

import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostToJournalResponse {
    @ApiModelProperty(position = 1)
    private Long accountId;

    @ApiModelProperty(position = 2)
    private String accountName;

    @ApiModelProperty(position = 3)
    private Double lastPostedAmount;

    @ApiModelProperty(position = 4)
    private Double accumDeprAmount;

    @ApiModelProperty(position = 4)
    private Long isDepreAcc;

    @ApiModelProperty(position = 7)
    private Double debit;

    @ApiModelProperty(position = 8)
    private Double credit;


//    @ApiModelProperty(position = 5)
//    private List<PostToJournalResponseDetails> details;

    @ApiModelProperty(position = 5)
    private List<FixedAssetResponse> details;
}

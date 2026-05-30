package com.ut.nlSystemAPi.model.request.Login.AccountClosingDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountClosingDateSettingRequest {

    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long branchId;

    @ApiModelProperty(position = 4)
    private String reference;

    @ApiModelProperty(position = 8)
    private List<AccountClosingDateSettingDetailRequest> detailRequests;

}

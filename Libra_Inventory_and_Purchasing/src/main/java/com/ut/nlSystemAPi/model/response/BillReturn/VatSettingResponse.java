package com.ut.nlSystemAPi.model.response.BillReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VatSettingResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long type;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private Double vatPercent;

    @ApiModelProperty(position = 6)
    private Long chartAccountId;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private String createdBy;

    @ApiModelProperty(position = 9)
    private String modified;

    @ApiModelProperty(position = 10)
    private String modifiedBy;

}

package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
public class BanksAccount  {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 45)
    private Long branchId;

    @ApiModelProperty(position = 45)
    private Long bankAccountId;

    @ApiModelProperty(position = 45)
    private String accountNumber;

    @ApiModelProperty(position = 45)
    private String accountHolder;

    @ApiModelProperty(position = 45)
    private String bank;

    @ApiModelProperty(position = 45)
    private String address;


}

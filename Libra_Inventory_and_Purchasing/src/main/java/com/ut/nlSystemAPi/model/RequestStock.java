package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RequestStock extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String  memo;

    @ApiModelProperty(position = 5)
    private String  note;

    @ApiModelProperty(position = 6)
    private Long  formLocationGroupId;

    @ApiModelProperty(position = 7)
    private Long  toLocationGroupId;

    @ApiModelProperty(position = 7)
    private Long  isApprove;

}

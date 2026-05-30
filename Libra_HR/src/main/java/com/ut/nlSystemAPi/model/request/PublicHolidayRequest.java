package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublicHolidayRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 1)
    private String dateFrom;

    @ApiModelProperty(position = 1)
    private String dateTo;








}

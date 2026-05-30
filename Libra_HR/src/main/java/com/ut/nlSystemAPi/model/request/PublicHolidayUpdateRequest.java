package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublicHolidayUpdateRequest extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String dateFrom;

    @ApiModelProperty(position = 4)
    private String dateTo;


}

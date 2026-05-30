package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
@Data
public class DateResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private String title;

    @ApiModelProperty(position = 2)
    private String currentMonth;

    @ApiModelProperty(position = 3)
    private String previousMonth;

    @ApiModelProperty(position = 4)
    private String date;

}

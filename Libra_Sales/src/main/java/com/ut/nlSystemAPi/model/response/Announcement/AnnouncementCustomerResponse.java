package com.ut.nlSystemAPi.model.response.Announcement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnnouncementCustomerResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;
}

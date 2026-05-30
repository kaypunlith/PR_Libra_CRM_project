package com.ut.nlSystemAPi.model.request.Announcement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AnnouncementRequest {

    @ApiModelProperty(position = 2)
    private String title;

    @ApiModelProperty(position = 3)
    private String image;

    @ApiModelProperty(position = 4)
    private String imageUrl;

    @ApiModelProperty(position = 5)
    private String content;

    @ApiModelProperty(position = 6)
    private String schedule;

    @ApiModelProperty(position = 7)
    private Integer isPublic;

    @ApiModelProperty(position = 8)
    private List<Long> customerIds;

    @ApiModelProperty(position = 9)
    private List<Long> userAppIds;

}

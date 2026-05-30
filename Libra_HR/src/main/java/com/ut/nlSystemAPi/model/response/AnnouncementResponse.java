package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AnnouncementResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String title;

    @ApiModelProperty(position = 3)
    private String thumbnailName;

    @ApiModelProperty(position = 4)
    private String thumbnail;

    @ApiModelProperty(position = 5)
    private String content;

    @ApiModelProperty(position = 6)
    private String schedule;

    @ApiModelProperty(position = 7)
    private Integer isPublic;

    @ApiModelProperty(position = 8)
    private Integer isActive;

    @ApiModelProperty(position = 9)
    private String created;

    @ApiModelProperty(position = 10)
    private String createdBy;

    @ApiModelProperty(position = 11)
    private String modified;

    @ApiModelProperty(position = 12)
    private String modifiedBy;

}

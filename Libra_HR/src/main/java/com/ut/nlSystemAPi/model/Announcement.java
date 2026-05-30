package com.ut.nlSystemAPi.model;

import java.io.Serializable;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Announcement extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String title;

    @ApiModelProperty(position = 3)
    private String fileName;

    @ApiModelProperty(position = 4)
    private String fileUrl;

    @ApiModelProperty(position = 5)
    private String content;

    @ApiModelProperty(position = 6)
    private String schedule;

    @ApiModelProperty(position = 7)
    private Integer isPublic;

    @ApiModelProperty(position = 8)
    private Integer type;
}

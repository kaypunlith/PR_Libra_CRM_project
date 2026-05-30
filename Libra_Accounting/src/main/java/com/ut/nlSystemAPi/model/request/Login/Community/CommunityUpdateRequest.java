package com.ut.nlSystemAPi.model.request.Login.Community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommunityUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String nameKh;

    @ApiModelProperty(position = 3)
    private String nameEn;

    @ApiModelProperty(position = 4)
    private String nameCn;

    @ApiModelProperty(position = 5)
    private String lats;

    @ApiModelProperty(position = 6)
    private String longs;

    @ApiModelProperty(position = 7)
    private String 	thumbnail;

    @ApiModelProperty(position = 4)
    private String thumbnailName;

    @ApiModelProperty(position =8)
    private String 	descriptionKh;

    @ApiModelProperty(position = 9)
    private String 	descriptionEn;

    @ApiModelProperty(position = 10)
    private String 	descriptionCn;

    @ApiModelProperty(position = 6)
    private Long ordering;

    @ApiModelProperty(position = 11)
    private List<CommunitySlideRequest> communitySlideRequests;
}

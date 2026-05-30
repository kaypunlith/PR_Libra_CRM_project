package com.ut.nlSystemAPi.model.request.Announcement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnnouncementUpdateRequest extends AnnouncementRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}

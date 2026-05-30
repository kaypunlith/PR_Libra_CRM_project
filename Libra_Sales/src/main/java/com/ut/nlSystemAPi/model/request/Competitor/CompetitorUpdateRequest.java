package com.ut.nlSystemAPi.model.request.Competitor;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompetitorUpdateRequest extends CompetitorRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
package com.ut.nlSystemAPi.model.request.BOQ;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BOQUpdateRequest extends BOQRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}

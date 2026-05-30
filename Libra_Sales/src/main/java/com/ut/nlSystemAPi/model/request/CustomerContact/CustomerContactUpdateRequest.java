package com.ut.nlSystemAPi.model.request.CustomerContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContactUpdateRequest extends CustomerContactRequest{
    @ApiModelProperty(position = 1)
    private Long id;
}
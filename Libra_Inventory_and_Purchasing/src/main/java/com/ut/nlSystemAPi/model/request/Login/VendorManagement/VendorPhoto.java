package com.ut.nlSystemAPi.model.request.Login.VendorManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VendorPhoto {

    @ApiModelProperty(position = 2)
    private String url;

    @ApiModelProperty(position = 3)
    private String name;


}

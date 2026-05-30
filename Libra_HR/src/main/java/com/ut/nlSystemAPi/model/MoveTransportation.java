package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class MoveTransportation extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Long ROLE_ADMIN = 1L;

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String sysCode;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private Long branchId;

    @ApiModelProperty(position = 5)
    private Long destinationFromId;

    @ApiModelProperty(position = 6)
    private Long warehouseToId;

    @ApiModelProperty(position = 7)
    private String departure;

    @ApiModelProperty(position = 8)
    private String driverName;

    @ApiModelProperty(position = 9)
    private String driverTel;

    @ApiModelProperty(position = 10)
    private String totalWeight;

    @ApiModelProperty(position = 11)
    private String expense;

    @ApiModelProperty(position = 12)
    private String note;

}

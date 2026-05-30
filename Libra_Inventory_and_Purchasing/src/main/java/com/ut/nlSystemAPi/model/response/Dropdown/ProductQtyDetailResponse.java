package com.ut.nlSystemAPi.model.response.Dropdown;

import com.ut.nlSystemAPi.model.response.UomConversion.UomConversionDetailResponse;
import com.ut.nlSystemAPi.model.response.UomConversion.UomConversionResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductQtyDetailResponse {
    @ApiModelProperty(position = 1)
    private Long locationId;

    @ApiModelProperty(position = 2)
    private String locationName;

    @ApiModelProperty(position = 3)
    private Long warehouseId;

    @ApiModelProperty(position = 4)
    private String warehouseName;

    @ApiModelProperty(position = 5)
    private Double endingQty;

    @ApiModelProperty(position = 6)
    private String endingUomName;

    @ApiModelProperty(position = 7)
    private Double orderQty;

    @ApiModelProperty(position = 8)
    private String orderUomName;

    @ApiModelProperty(position = 9)
    private List<UomsDropdownResponse> details;
}

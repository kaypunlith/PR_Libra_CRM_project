package com.ut.nlSystemAPi.model.response;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.ApplyEmployeeTypesRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class EmployeeTableShiftRequestResponse extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long workShiftId;

    @ApiModelProperty(position = 2)
    private String workShiftName;

    @ApiModelProperty(position = 3)
    private Long totalDay;

    @ApiModelProperty(position = 3)
    private Float paid;


}

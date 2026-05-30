package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("HrDepartmentFilter")
@Data
public class DepartmentFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 4)
    private Long groupId;

}

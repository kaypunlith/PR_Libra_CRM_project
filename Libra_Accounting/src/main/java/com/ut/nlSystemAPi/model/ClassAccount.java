package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassAccount extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long parentId;

  @ApiModelProperty(position = 4)
  private String parentName;

  @ApiModelProperty(position = 11)
  private String name;

  @ApiModelProperty(position = 11)
  private String description;

  @ApiModelProperty(position = 2)
  private Long ordering;

  @ApiModelProperty(position = 10)
  private List<Long> companyId;

  @ApiModelProperty(position = 10)
  private List<Long> userId;

}

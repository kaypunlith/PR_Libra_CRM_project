package com.ut.nlSystemAPi.model.response;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionResponse implements Serializable {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 1)
  private String startDate;

  @ApiModelProperty(position = 1)
  private String endDate;

  @ApiModelProperty(position = 1)
  private String description;

  @ApiModelProperty(position = 1)
  private List<DropdownResponse> means;

  @ApiModelProperty(position = 1)
  private List<DropdownResponse> organizations;

  @ApiModelProperty(position = 1)
  private List<DropdownResponse> destinations;

  @ApiModelProperty(position = 7)
  private Integer status;

  @ApiModelProperty(position = 7)
  private String createdBy;

  @ApiModelProperty(position = 8)
  private String modifiedBy;

  @ApiModelProperty(position = 5)
  private String created;

  @ApiModelProperty(position = 6)
  private String modified;
}

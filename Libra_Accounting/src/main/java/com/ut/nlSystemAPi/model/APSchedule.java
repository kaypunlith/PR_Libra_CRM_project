package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class APSchedule extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;
  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long branchId;

  @ApiModelProperty(position = 2)
  private String startDate;

  @ApiModelProperty(position = 3)
  private String endDate;

  @ApiModelProperty(position = 4)
  private String startTime;

  @ApiModelProperty(position = 5)
  private String endTime;

  @ApiModelProperty(position = 7)
  private String title;

  @ApiModelProperty(position = 8)
  private String backgroundColor;

  @ApiModelProperty(position = 9)
  private String privacy;

  @ApiModelProperty(position = 14)
  private List<Long> details;

}

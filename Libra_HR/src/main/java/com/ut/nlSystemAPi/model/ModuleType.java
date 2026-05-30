package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ModuleType {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private String name;

  @ApiModelProperty(position = 3)
  private List<Module> moduleList;

}

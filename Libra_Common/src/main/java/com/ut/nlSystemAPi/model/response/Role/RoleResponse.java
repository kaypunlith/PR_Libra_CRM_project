package com.ut.nlSystemAPi.model.response.Role;

import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.RoleUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String Username;

  @ApiModelProperty(position = 9)
  private String createdBy;

  @ApiModelProperty(position = 10)
  private String createdDate;

  @ApiModelProperty(position = 11)
  private String modifiedBy;

  @ApiModelProperty(position = 12)
  private String modifiedDate;

    @ApiModelProperty(position = 3)
    private List<RoleUser> userList;

    @ApiModelProperty(position = 4)
    private List<ModuleType> moduleTypeList;

}

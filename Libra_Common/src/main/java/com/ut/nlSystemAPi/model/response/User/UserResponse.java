package com.ut.nlSystemAPi.model.response.User;

import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.Users.UserGroupList;
import com.ut.nlSystemAPi.model.response.Dropdown.WarehouseDropDownResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserResponse implements Serializable {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private String photo;

  @ApiModelProperty(position = 2)
  private String fullName;

  @ApiModelProperty(position = 2)
  private String firstName;

  @ApiModelProperty(position = 2)
  private String lastName;

  @ApiModelProperty(position = 3)
  private String username;

  @ApiModelProperty(position = 4)
  private String employeeCode;

  @ApiModelProperty(position = 5)
  private String employeeName;

  @ApiModelProperty(position = 6)
  private String employeeGender;

  @ApiModelProperty(position = 7)
  private String employeeDob;

  @ApiModelProperty(position = 8)
  private String employeePhoneNumber;

  @ApiModelProperty(position = 9)
  private String employeeEmail;

  @ApiModelProperty(position = 10)
  private Long provinceId;

  @ApiModelProperty(position = 11)
  private String provinceName;

  @ApiModelProperty(position = 12)
  private Long districtId;

  @ApiModelProperty(position = 13)
  private String districtName;

  @ApiModelProperty(position = 14)
  private Long communeId;

  @ApiModelProperty(position = 15)
  private String communeName;

  @ApiModelProperty(position = 16)
  private Long villageId;

  @ApiModelProperty(position = 17)
  private String villageName;

  @ApiModelProperty(position = 25)
  private String dateFormatName;

  @ApiModelProperty(position = 25)
  private String dateFormat;

  @ApiModelProperty(position = 9)
  private String createdBy;

  @ApiModelProperty(position = 10)
  private String createdDate;

  @ApiModelProperty(position = 11)
  private String modifiedBy;

  @ApiModelProperty(position = 12)
  private String modifiedDate;

  @ApiModelProperty(position = 13)
  private Long employeeId;

  @ApiModelProperty(position = 14)
  private Long companyId;

  @ApiModelProperty(position = 16)
  private Double rateToSell;

  @ApiModelProperty(position = 19)
  private List<ModuleType> moduleTypeList;

  @ApiModelProperty(position = 20)
  private List<UserGroupList> userRoleList;

  @ApiModelProperty(position = 21)
  private List<WarehouseDropDownResponse> freedomWarehouse;
}

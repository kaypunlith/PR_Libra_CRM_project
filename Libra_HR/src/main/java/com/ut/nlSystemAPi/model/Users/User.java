package com.ut.nlSystemAPi.model.Users;

import com.ut.nlSystemAPi.enums.AuthProvider;
import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final Long ROLE_ADMIN = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 1)
  private String fullName; //name

  @ApiModelProperty(position = 2)
  private String username;

  @ApiModelProperty(position = 3)
  private String password;

  @ApiModelProperty(position = 4)
  private String filename;

  @ApiModelProperty(position = 5)
  private Long type;

  @ApiModelProperty(position = 5)
  private String sex;

  @ApiModelProperty(position = 5)
  private Long employeeId;

  @ApiModelProperty(position = 5)
  private String dob;

  @ApiModelProperty(position = 5)
  private String address;

  @ApiModelProperty(position = 5)
  private String photo;

  @ApiModelProperty(position = 6)
  private String phoneVerifyToken;

  @ApiModelProperty(position = 7)
  private Date phoneVerifyTokenExpiredDate;

  @ApiModelProperty(position = 8)
  private String phoneVerifyCode;

  @ApiModelProperty(position = 9)
  private Date phoneVerifyCodeExpiredDate;

  @ApiModelProperty(position = 10)
  private String phoneResetPasswordToken;

  @ApiModelProperty(position = 11)
  private Date phoneResetPasswordTokenExpiredDate;

  @ApiModelProperty(position = 12)
  private String phoneResetPasswordCode;

  @ApiModelProperty(position = 13)
  private Date phoneResetPasswordCodeExpiredDate;

  @ApiModelProperty(position = 14)
  private String phoneNewPasswordToken;

  @ApiModelProperty(position = 15)
  private Date phoneNewPasswordTokenExpiredDate;

  @ApiModelProperty(position = 16)
  private AuthProvider provider;

  @ApiModelProperty(position = 17)
  private String providerId;

  @ApiModelProperty(position = 18)
  private String providerImageUrl;

  @ApiModelProperty(position = 19)
  private String providerData;

  @ApiModelProperty(position = 20)
  private String loginType;

  @ApiModelProperty(position = 22)
  private String email;

  @ApiModelProperty(position = 23)
  private String telephone;

  @ApiModelProperty(position = 25)
  private Long groupId;

  @ApiModelProperty(position = 26)
  private String groupName;

  @ApiModelProperty(position = 26)
  private String[] userGroup;

  @ApiModelProperty(position = 26)
  private String[] applyDepartment;

  @ApiModelProperty(position = 27)
  private String modifiedDate;

  @ApiModelProperty(position = 27)
  private String createdDate;

  @ApiModelProperty(position = 28)
  private Long applyEmployees;

  @ApiModelProperty(position = 28)
  private Long applyAll;

  @ApiModelProperty(position = 29)
  private List<UserGroupList> userGroupLists;

  @ApiModelProperty(position = 29)
  private List<UserDepartmentList> userDepartmentLists;
}

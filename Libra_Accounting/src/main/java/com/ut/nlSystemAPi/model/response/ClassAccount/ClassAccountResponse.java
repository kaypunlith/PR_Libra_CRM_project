package com.ut.nlSystemAPi.model.response.ClassAccount;

import com.ut.nlSystemAPi.model.Company;
import com.ut.nlSystemAPi.model.response.User.UserResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassAccountResponse {

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

    @ApiModelProperty(position = 14)
    private String createdDate;

    @ApiModelProperty(position = 15)
    private String modifiedDate;

    @ApiModelProperty(position = 16)
    private String createdBy;

    @ApiModelProperty(position = 17)
    private String modifiedBy;

    @ApiModelProperty(position = 18)
    private Long status;

    @ApiModelProperty(position = 19)
    private List<Company> companyResponses;

    @ApiModelProperty(position = 19)
    private List<UserResponse> userPermissionResponse;

}

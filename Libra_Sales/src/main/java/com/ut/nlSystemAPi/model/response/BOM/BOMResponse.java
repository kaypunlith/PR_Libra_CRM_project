package com.ut.nlSystemAPi.model.response.BOM;

import com.ut.nlSystemAPi.model.response.BOQ.BOQDetailResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BOMResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 2)
    private Long boqId;

    @ApiModelProperty(position = 2)
    private String boqCode;

    @ApiModelProperty(position = 2)
    private String pcCode;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private String companyName;

    @ApiModelProperty(position = 6)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private String organizationCode;

    @ApiModelProperty(position = 8)
    private String organizationName;

    @ApiModelProperty(position = 9)
    private Long organizationContactId;

    @ApiModelProperty(position = 10)
    private String organizationContact;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 11)
    private String invoiceNo;

    @ApiModelProperty(position = 11)
    private Long invoiceDateDiff;

    @ApiModelProperty(position = 12)
    private Double totalCost;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

    @ApiModelProperty(position = 14)
    private Long status;

    @ApiModelProperty(position = 15)
    private String approved;

    @ApiModelProperty(position = 15)
    private String approvedBy;

    @ApiModelProperty(position = 16)
    private String created;

    @ApiModelProperty(position = 17)
    private String createdBy;

    @ApiModelProperty(position = 18)
    private List<BOMDetailResponse> details;
}

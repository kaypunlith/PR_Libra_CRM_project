package com.ut.nlSystemAPi.model.response.Organization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerHistoryReportResponse {

    @ApiModelProperty(position = 1, example = "1")
    private Long customerId;

    @ApiModelProperty(position = 2)
    private String customerName;

    @ApiModelProperty(position = 3)
    private String customerCode;

    @ApiModelProperty(position = 4)
    private Integer latestQty = 0;

    @ApiModelProperty(position = 5)
    private List<CustomerHistoryMemoResponse> memos = new ArrayList<>();

    // The below fields are used by MyBatis to map the flat results, then we group them in the Service layer
    @JsonIgnore
    private String status; 
    @JsonIgnore
    private Integer qty;
    @JsonIgnore
    private String memoStatus;
    @JsonIgnore
    private String date;
    @JsonIgnore
    private String serviceNames; 
    @JsonIgnore
    private Long quotationId;
    @JsonIgnore
    private Long terminateId;
    @JsonIgnore
    private Integer printCount;

    @JsonIgnore
    public List<String> getServices() {
        if (this.serviceNames != null && !this.serviceNames.isEmpty()) {
            return java.util.Arrays.asList(this.serviceNames.split(","));
        }
        return null;
    }
}

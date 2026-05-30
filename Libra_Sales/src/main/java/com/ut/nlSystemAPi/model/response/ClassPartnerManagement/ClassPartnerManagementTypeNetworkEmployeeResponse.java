package com.ut.nlSystemAPi.model.response.ClassPartnerManagement;

import lombok.Data;

@Data
public class ClassPartnerManagementTypeNetworkEmployeeResponse {
    private Long classId;
    private Long typeNetworkId;
    private String typeNetworkName;
    private Long userId;
    private String employeeName;
}

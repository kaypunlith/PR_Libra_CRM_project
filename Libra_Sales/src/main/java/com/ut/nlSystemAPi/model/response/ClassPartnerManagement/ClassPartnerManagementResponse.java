package com.ut.nlSystemAPi.model.response.ClassPartnerManagement;

import lombok.Data;

import java.util.List;

@Data
public class ClassPartnerManagementResponse {
    private Long id;
    private String name;
    private String employeeGroupName;
    private List<Long> egroupIds;
    private List<ClassPartnerManagementTypeNetworkEmployeeResponse> typeNetworkEmployees;
    private String createdBy;
    private String created;
    private String modifiedBy;
    private String modified;
}

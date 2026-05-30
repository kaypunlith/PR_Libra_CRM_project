package com.ut.nlSystemAPi.model.response.PartnerManagement;

import lombok.Data;

@Data
public class PartnerManagementResponse {
    private Long id;
    private String partnerAreaExpertise;
    private String partnerName;
    private String keyContactName;
    private String keyContactTel;
    private String keyContactEmail;
    private String why;
    private Long typeNetworkId;
    private String typeNetworkName;
    private Long classId;
    private String className;
    private Long industryId;
    private String industryName;
    private Long positionId;
    private String positionName;
    private String origin;
    private String refBy;
    private Integer annualReview;
    private String annualReviewName;
    private String relationship;
    private Long employeeId;
    private String personInChargeName;
    private String expense;
    private String createdBy;
    private String created;
    private String modifiedBy;
    private String modified;
}

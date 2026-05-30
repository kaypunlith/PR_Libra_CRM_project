package com.ut.nlSystemAPi.model.entity.PartnerManagement;

import lombok.Data;

@Data
public class PartnerManagement {
    private Long id;
    private String partnerAreaExpertise;
    private String partnerName;
    private String keyContactName;
    private String keyContactTel;
    private String keyContactEmail;
    private String why;
    private Long typeNetworkId;
    private Long classId;
    private Long industryId;
    private Long positionId;
    private String origin;
    private String refBy;
    private Integer annualReview;
    private String relationship;
    private Long personInChargeId;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}

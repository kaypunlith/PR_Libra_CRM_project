package com.ut.nlSystemAPi.model.entity.ProjectEstimation;

import com.ut.nlSystemAPi.model.base.BaseModel;
import lombok.Data;

@Data
public class ProjectEstimation extends BaseModel {

    private Long id;

    private Long companyId;

    private String pcCode;

    private Long customerId;

    private Long customerContactId;

    private Long employeeId;

    private Long boqId;

    private String date;

    private Long duration;

    private String note;

    private Double totalSubAmount;

    private Double totalAmount;

    private Double totalPercent;

}
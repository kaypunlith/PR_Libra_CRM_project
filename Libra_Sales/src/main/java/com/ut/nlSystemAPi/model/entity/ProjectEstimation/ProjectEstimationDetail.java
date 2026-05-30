package com.ut.nlSystemAPi.model.entity.ProjectEstimation;

import com.ut.nlSystemAPi.model.base.BaseModel;
import lombok.Data;

@Data
public class ProjectEstimationDetail extends BaseModel {

    private Long id;

    private Long projectEstimationId;

    private Long projectEstimationTermId;

    private Long vendorId;

    private String description;

    private Double qty;

    private Long uomId;

    private Double unitPrice;

    private Double totalPrice;

    private String note;

}
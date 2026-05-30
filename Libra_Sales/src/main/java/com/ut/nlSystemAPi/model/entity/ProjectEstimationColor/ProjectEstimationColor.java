package com.ut.nlSystemAPi.model.entity.ProjectEstimationColor;

import com.ut.nlSystemAPi.model.base.BaseModel;
import lombok.Data;

@Data
public class ProjectEstimationColor extends BaseModel {


    private Long id;

    private String color;

    private String code;

    private Double percentFrom;

    private Double percentTo;
}
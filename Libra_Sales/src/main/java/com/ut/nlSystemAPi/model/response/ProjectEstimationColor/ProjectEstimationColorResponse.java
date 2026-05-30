package com.ut.nlSystemAPi.model.response.ProjectEstimationColor;

import lombok.Data;

@Data
public class ProjectEstimationColorResponse {

    private Long id;

    private String name;

    private String code;

    private Double percentFrom;

    private Double percentTo;
}
package com.ut.nlSystemAPi.model.entity.Quotation;

import lombok.Data;

@Data
public class QuotationLog {
    private Long id;
    private Long quotationId;
    private String action;
    private Double totalAmount;
    private String created;
    private Long createdBy;
    private String serviceId;
}

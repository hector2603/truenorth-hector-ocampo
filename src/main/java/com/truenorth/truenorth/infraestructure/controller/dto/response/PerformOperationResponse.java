package com.truenorth.truenorth.infraestructure.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PerformOperationResponse {

    private String result;
    private BigDecimal amount;
    private BigDecimal userBalance;

}

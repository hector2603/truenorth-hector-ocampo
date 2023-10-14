package com.truenorth.truenorth.infraestructure.controller.dto.request;

import com.truenorth.truenorth.domain.model.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PerformOperationRequest {

    private OperationType operation;
    private BigDecimal firstOperator;
    private BigDecimal secondOperator;

}

package com.truenorth.truenorth.infraestructure.controller.dto.response;

import com.truenorth.truenorth.domain.model.enums.OperationType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OperationResponse {

    private BigDecimal cost;
    private OperationType type;

}

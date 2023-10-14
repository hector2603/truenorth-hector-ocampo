package com.truenorth.truenorth.infraestructure.controller.dto.response;

import com.truenorth.truenorth.domain.model.enums.OperationType;
import com.truenorth.truenorth.domain.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
@Builder
public class RecordResponse {

    private Long id;
    private BigDecimal amount;
    private Timestamp date;
    private String operationResponse;
    private BigDecimal userBalance;
    private Status status;
    private OperationType operationType;

}

package com.truenorth.truenorth.domain.mapper;

import com.truenorth.truenorth.domain.model.Operation;
import com.truenorth.truenorth.domain.model.Record;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.infraestructure.controller.dto.response.OperationResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.RecordResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;

@Component
public class MapperUtils {

    public UserResponse convertUserToUserReponse(User user) {
        var record = new Record();
        record.setUserBalance(BigDecimal.valueOf(100));
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .status(user.getStatus())
                .role(user.getRole())
                .balance(user.getRecordsById().stream().max(Comparator.comparing(Record::getId)).orElse(record).getUserBalance())
                .build();
    }

    public OperationResponse convertOperationToOperationResponse(Operation operation){
        return OperationResponse.builder()
                .cost(operation.getCost())
                .type(operation.getType())
                .build();
    }

    public RecordResponse converRecordToRecordResponse(Record record){
        return RecordResponse.builder()
                .id(record.getId())
                .operationResponse(record.getOperationResponse())
                .operationType(record.getOperationByOperationId().getType())
                .amount(record.getAmount())
                .userBalance(record.getUserBalance())
                .date(record.getDate())
                .status(record.getStatus())
                .build();
    }

}

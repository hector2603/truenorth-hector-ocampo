package com.truenorth.truenorth.domain.service.impl;

import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionInvalidValue;
import com.truenorth.truenorth.domain.mapper.MapperUtils;
import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.model.Operation;
import com.truenorth.truenorth.domain.model.Record;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.model.enums.Status;
import com.truenorth.truenorth.domain.service.OperationService;
import com.truenorth.truenorth.domain.validator.Validator;
import com.truenorth.truenorth.infraestructure.api.consumer.RandomStringGenerator;
import com.truenorth.truenorth.infraestructure.controller.dto.request.PerformOperationRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.OperationResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.PerformOperationResponse;
import com.truenorth.truenorth.infraestructure.repository.OperationRepository;
import com.truenorth.truenorth.infraestructure.repository.RecordRepository;
import com.truenorth.truenorth.infraestructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    OperationRepository operationRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MapperUtils mapperUtils;
    @Autowired
    Validator validator;
    @Autowired
    RandomStringGenerator randomStringGenerator;

    @Override
    public PerformOperationResponse performOperation(PerformOperationRequest performOperationRequest, AuthUserDetails principal) throws Exception {
        validator.validatePerformOperation(performOperationRequest);
        Operation operation = operationRepository.findByType(performOperationRequest.getOperation())
                .orElseThrow(() -> new ExceptionInvalidValue(ErrorCode.EC0006.message));
        Record currentRecord = findCurrentUserRecord(principal);
        if (currentRecord.getUserBalance().compareTo(operation.getCost()) == -1) {
            throw new ExceptionInvalidValue(ErrorCode.EC0007.message);
        }
        String result = performOperation(performOperationRequest, operation);
        BigDecimal newBalance = currentRecord.getUserBalance().subtract(operation.getCost());
        Record newRecord = createRecord(operation, principal, newBalance, result);
        recordRepository.save(newRecord);
        return PerformOperationResponse.builder().result(result).amount(operation.getCost()).userBalance(newBalance).build();
    }

    private Record findCurrentUserRecord(AuthUserDetails principal) {
        var user = userRepository.findById(principal.getId()).orElseThrow();
        Record defaultRecord = new Record();
        defaultRecord.setUserBalance(BigDecimal.valueOf(100));
        return recordRepository.findTopByUserByUserId(user, Sort.by("id").descending()).orElse(defaultRecord);
    }

    private String performOperation(PerformOperationRequest performOperationRequest, Operation operation) throws Exception {
        return switch (operation.getType()) {
            case ADDITION ->
                    performOperationRequest.getFirstOperator().add(performOperationRequest.getSecondOperator()).toString();
            case SUBTRACTION ->
                    performOperationRequest.getFirstOperator().subtract(performOperationRequest.getSecondOperator()).toString();
            case DIVISION ->
                    performOperationRequest.getFirstOperator().divide(performOperationRequest.getSecondOperator()).toString();
            case SQUARE_ROOT ->
                    BigDecimal.valueOf(Math.sqrt(performOperationRequest.getFirstOperator().doubleValue())).toString();
            case MULTIPLICATION ->
                    performOperationRequest.getFirstOperator().multiply(performOperationRequest.getSecondOperator()).toString();
            case RANDOM_STRING ->
                    randomStringGenerator.generateRandomString(performOperationRequest.getFirstOperator().intValue());
        };
    }

    private Record createRecord(Operation operation, AuthUserDetails principal, BigDecimal newBalance, String result) {
        var newRecord = new Record();
        newRecord.setOperationByOperationId(operation);
        newRecord.setUserByUserId(userRepository.findById(principal.getId()).orElseThrow());
        newRecord.setUserBalance(newBalance);
        newRecord.setAmount(operation.getCost());
        newRecord.setOperationResponse(result);
        newRecord.setDate(new Timestamp(System.currentTimeMillis()));
        newRecord.setStatus(Status.ACTIVE);
        return newRecord;
    }

    @Override
    public Set<OperationResponse> getOperations() {
        return operationRepository.findAll().stream().map( operation -> mapperUtils.convertOperationToOperationResponse(operation)).collect(Collectors.toSet());
    }

}

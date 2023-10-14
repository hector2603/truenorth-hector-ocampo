package com.truenorth.truenorth.domain.validator;

import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionInvalidValue;
import com.truenorth.truenorth.infraestructure.controller.dto.request.PerformOperationRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public void validateOperationWithTwoOperators(PerformOperationRequest performOperationRequest){
        if(ObjectUtils.isEmpty(performOperationRequest.getFirstOperator()) || ObjectUtils.isEmpty(performOperationRequest.getSecondOperator())){
            throw new ExceptionInvalidValue(ErrorCode.EC0008.message);
        }
    }

    public void validateOperationWithOneOperators(PerformOperationRequest performOperationRequest) {
        if(ObjectUtils.isEmpty(performOperationRequest.getFirstOperator())){
            throw new ExceptionInvalidValue(ErrorCode.EC0009.message);
        }
    }

    public void validatePerformOperation(PerformOperationRequest performOperationRequest){
        switch (performOperationRequest.getOperation()){
            case ADDITION,SUBTRACTION,DIVISION,MULTIPLICATION -> validateOperationWithTwoOperators(performOperationRequest);
            case SQUARE_ROOT,RANDOM_STRING -> validateOperationWithOneOperators(performOperationRequest);
        }
    }
}

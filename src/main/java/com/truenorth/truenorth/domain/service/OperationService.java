package com.truenorth.truenorth.domain.service;

import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.infraestructure.controller.dto.request.PerformOperationRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.OperationResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.PerformOperationResponse;

import java.util.Set;

public interface OperationService {

    PerformOperationResponse performOperation(PerformOperationRequest performOperationRequest, AuthUserDetails principal) throws Exception;

    Set<OperationResponse> getOperations();
}

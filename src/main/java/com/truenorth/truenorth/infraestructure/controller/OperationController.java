package com.truenorth.truenorth.infraestructure.controller;

import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.service.OperationService;
import com.truenorth.truenorth.infraestructure.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.PerformOperationRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.OperationResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.PerformOperationResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.WrapperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;
import java.util.Set;

import static com.truenorth.truenorth.application.constants.PathConstants.OPERATION;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://truenorth-hector-ocampo-frontent-calculator.vercel.app/"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(OPERATION)
@Tag(name = "OperationController", description = "Controller to manage the operation that the user do")
public class OperationController {

    @Autowired
    OperationService operationService;

    @PostMapping()
    @Operation(summary = "Perform Operation", description = "This method execute a specific operation depend of the request")
    public ResponseEntity<WrapperResponse<PerformOperationResponse>> performOperation(@RequestBody PerformOperationRequest performOperationRequest, Authentication authentication) throws Exception {
        PerformOperationResponse performOperationResponse = operationService.performOperation(performOperationRequest, (AuthUserDetails)authentication.getPrincipal());
        return ResponseEntity.ok(new WrapperResponse<>(performOperationResponse));
    }

    @GetMapping
    @Operation(summary = "Fetch Operations", description = "use this method to fetch all the operations in the data base")
    public ResponseEntity<WrapperResponse<Set<OperationResponse>>> getOperations(){
        Set<OperationResponse> operations = operationService.getOperations();
        return ResponseEntity.ok(new WrapperResponse<>(operations));
    }
}

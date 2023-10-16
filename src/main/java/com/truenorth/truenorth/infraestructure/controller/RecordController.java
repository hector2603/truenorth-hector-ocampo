package com.truenorth.truenorth.infraestructure.controller;

import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.service.RecordService;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.RecordResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.WrapperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.truenorth.truenorth.application.constants.PathConstants.RECORD;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://truenorth-hector-ocampo-frontent-calculator.vercel.app/"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(RECORD)
@Tag(name = "RecordController", description = "Controller to manage the records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping()
    @Operation(summary = "this method is used to fecth the records of the current user", description = "this method takes the JWT and fetch the records of this person")
    public ResponseEntity<WrapperResponse<List<RecordResponse>>> findRecordsByUser(
            @Parameter(description = "The page number", example = "1") @RequestParam(value = "page") Integer page,
            @Parameter(description = "The number of records per page", example = "5") @RequestParam(value = "numberperpage") Integer numberPerPage,
            @Parameter(description = "The column to filter by", example = "amount") @RequestParam(value = "columnFilter", required = false) String columnFilter,
            @Parameter(description = "The value to filter by", example = "5") @RequestParam(value = "valueFilter", required = false) String valueFilter,
            @Parameter(description = "The column to sort by", example = "userBalance") @RequestParam(value = "columnSort", required = false) String columnSort,
            @Parameter(description = "The sort direction (asc or desc)", example = "ASC") @RequestParam(value = "sortDirection", required = false) String sortDirection,
            @Parameter(description = "The status of the records to fetch", example = "ACTIVE") @RequestParam("status") String status,
            Authentication authentication) throws Exception {
        AuthUserDetails principal = (AuthUserDetails) authentication.getPrincipal();
        List<RecordResponse> response = recordService.findRecordsByUser(principal.getId(), page, numberPerPage, columnFilter, valueFilter, columnSort, sortDirection, status);
        return ResponseEntity.ok(new WrapperResponse<>(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "this method is used to delete a record", description = "this method takes the JWT and delete a record of this person")
    public ResponseEntity<MessageResponse> deleteRecord(@PathVariable("id") Long id) throws Exception {
        MessageResponse response = recordService.deleteRecord(id);
        return ResponseEntity.ok(response);
    }

}

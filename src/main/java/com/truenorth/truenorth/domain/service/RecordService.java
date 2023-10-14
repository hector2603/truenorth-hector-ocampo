package com.truenorth.truenorth.domain.service;

import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.RecordResponse;

import java.util.List;

public interface RecordService {
    List<RecordResponse> findRecordsByUser(Long id, Integer page, Integer numberPerPage, String columnFilter, String valueFilter, String columnSort, String sortDirection, String status) throws NoSuchFieldException;
    MessageResponse deleteRecord(Long id);
}

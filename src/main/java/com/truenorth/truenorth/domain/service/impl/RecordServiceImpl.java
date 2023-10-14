package com.truenorth.truenorth.domain.service.impl;

import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionInvalidValue;
import com.truenorth.truenorth.domain.mapper.MapperUtils;
import com.truenorth.truenorth.domain.model.Record;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.model.enums.Status;
import com.truenorth.truenorth.domain.service.RecordService;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.RecordResponse;
import com.truenorth.truenorth.infraestructure.repository.RecordRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.truenorth.truenorth.application.constants.ConstantsMessages.RECORD_DELETED;
/**
 * @version 1.0.0
 * @autor hector.ocampo
 * @since 1.0.0
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    MapperUtils mapperUtils;

    /**
     * @param id
     * @param status
     * @return Set<RecordResponse>
     */
    @Override
    public List<RecordResponse> findRecordsByUser(Long id, Integer page, Integer numberPerPage, String columnFilter, String valueFilter, String columnSort, String sortDirection, String status) {
        User user = new User();
        user.setId(id);
        Stream<Record> records =  recordRepository.findAllByUserByUserId(user).stream();
        if (columnFilter != null && valueFilter != null ) {
            records = records.filter(record -> reflection(columnFilter, record).equalsIgnoreCase(valueFilter));
        }
        if(ObjectUtils.isNotEmpty(status)){
            records = records.filter(record -> reflection("status", record).equalsIgnoreCase(status));
        }
        if(columnSort != null && sortDirection != null){
            if(sortDirection.equalsIgnoreCase("DESC")){
                records = records.sorted((record1, record2) -> reflection(columnSort, record2).compareTo(reflection(columnSort, record1)));
            }else if(sortDirection.equalsIgnoreCase("ASC")){
                records = records.sorted((record1, record2) -> reflection(columnSort, record1).compareTo(reflection(columnSort, record2)));
            }
        }
        if(page != null && numberPerPage != null){
            Pageable pageable = PageRequest.of(page-1, numberPerPage);
            records = records.skip(pageable.getOffset()).limit(pageable.getPageSize());
        }
        return records.map(record -> mapperUtils.converRecordToRecordResponse(record)).collect(Collectors.toList());
    }

    private String reflection(String columnFilter, Record record){
        try {
            if(Objects.equals(columnFilter, "operationType")){
                return record.getOperationByOperationId().getType().toString();
            }
            Field field = Record.class.getDeclaredField(columnFilter);
            field.setAccessible(true);
            return field.get(record).toString();
        }catch (NoSuchFieldException | IllegalAccessException e){
            return "";
        }
    }


    /**
     * @param id
     * @return MessageResponse
     */
    @Override
    public MessageResponse deleteRecord(Long id) {
        Record record = recordRepository.findById(id).orElseThrow(() -> new ExceptionInvalidValue(ErrorCode.EC0006.message));
        record.setStatus(Status.INACTIVE);
        recordRepository.save(record);
        return new MessageResponse(RECORD_DELETED);
    }

}

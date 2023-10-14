package com.truenorth.truenorth.domain.service;

import com.truenorth.truenorth.config.TestConfig;
import com.truenorth.truenorth.domain.mapper.MapperUtils;
import com.truenorth.truenorth.domain.model.Operation;
import com.truenorth.truenorth.domain.model.Record;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.model.enums.OperationType;
import com.truenorth.truenorth.domain.model.enums.Status;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.RecordResponse;
import com.truenorth.truenorth.infraestructure.repository.RecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"app.jwtExpirationMs=123", "app.jwtSecret=1233213123123124124fcsdfsdfsdfsdfsd"})
public class RecordServiceImplTest {
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    MapperUtils mapperUtils;
    @Autowired
    RecordService recordService;


    @Test
    public void findRecordsByUserTest() throws NoSuchFieldException {
        Record record = new Record();
        record.setId(1L);
        record.setAmount(BigDecimal.ONE);
        record.setStatus(Status.ACTIVE);
        record.setUserBalance(BigDecimal.ONE);
        record.setOperationResponse("s");
        record.setOperationByOperationId( new Operation( 1L , BigDecimal.ONE, OperationType.ADDITION, null) );

        when(recordRepository.findAllByUserByUserId( any(User.class)) ).thenReturn(List.of(record));

        List<RecordResponse> result = recordService.findRecordsByUser(1L, 1, 1, "amount", "1", "amount", "ASC", "ACTIVE");

        Assertions.assertNotNull(result);
    }

    @Test
    public void deleteRecord() {
        Record record = new Record();
        record.setId(1L);
        record.setAmount(BigDecimal.ONE);
        record.setStatus(Status.ACTIVE);
        record.setUserBalance(BigDecimal.ONE);
        record.setOperationResponse("s");
        record.setOperationByOperationId( new Operation( 1L , BigDecimal.ONE, OperationType.ADDITION, null) );
        when(recordRepository.findById(1L)).thenReturn(java.util.Optional.of(record));
        MessageResponse result = recordService.deleteRecord(1L);
        Assertions.assertNotNull(result);
    }
}

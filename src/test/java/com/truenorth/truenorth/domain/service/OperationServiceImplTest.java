package com.truenorth.truenorth.domain.service;

import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionInvalidValue;
import com.truenorth.truenorth.config.TestConfig;
import com.truenorth.truenorth.domain.mapper.MapperUtils;
import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.model.Operation;
import com.truenorth.truenorth.domain.model.Record;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.model.enums.OperationType;
import com.truenorth.truenorth.domain.model.enums.Role;
import com.truenorth.truenorth.domain.model.enums.Status;
import com.truenorth.truenorth.domain.validator.Validator;
import com.truenorth.truenorth.infraestructure.api.consumer.RandomStringGenerator;
import com.truenorth.truenorth.infraestructure.controller.dto.request.PerformOperationRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.OperationResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.PerformOperationResponse;
import com.truenorth.truenorth.infraestructure.repository.OperationRepository;
import com.truenorth.truenorth.infraestructure.repository.RecordRepository;
import com.truenorth.truenorth.infraestructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"app.jwtExpirationMs=123", "app.jwtSecret=1233213123123124124fcsdfsdfsdfsdfsd"})
public class OperationServiceImplTest {


    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapperUtils mapperUtils;

    @Autowired
    private Validator validator;

    @Autowired
    private RandomStringGenerator randomStringGenerator;

    @Autowired
    private OperationService operationService;


    @Test
    public void testPerformOperation_Sum() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ROLE_USER);
        Record record = new Record();
        record.setUserBalance(BigDecimal.valueOf(100));
        user.setRecordsById(List.of(record));
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        when(operationRepository.findByType(OperationType.ADDITION)).thenReturn(Optional.of(new Operation(1L,BigDecimal.valueOf(30) , OperationType.ADDITION, null)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recordRepository.findTopByUserByUserId(any(), any(Sort.class))).thenReturn(Optional.of(record));
        when(randomStringGenerator.generateRandomString(10)).thenReturn("random-string");
        PerformOperationResponse performOperationResponse = operationService.performOperation(new PerformOperationRequest( OperationType.ADDITION, BigDecimal.valueOf(10), BigDecimal.valueOf(20)), principal);
        Assertions.assertEquals(BigDecimal.valueOf(30), performOperationResponse.getAmount());
        Assertions.assertEquals(BigDecimal.valueOf(70), performOperationResponse.getUserBalance());
        Assertions.assertEquals("30", performOperationResponse.getResult());
    }

    @Test
    public void testPerformOperation_InvalidOperation() {
        when(operationRepository.findByType(any())).thenReturn(Optional.empty());
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        try {
            operationService.performOperation(new PerformOperationRequest( OperationType.ADDITION, BigDecimal.valueOf(10), BigDecimal.valueOf(20)), principal);
        } catch (Exception e) {
            Assertions.assertEquals(ErrorCode.EC0006.message, e.getMessage());
        }
    }

    @Test
    public void testPerformOperation_InvalidBothOperators() {
        when(operationRepository.findByType(any())).thenReturn(Optional.empty());
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        try {
            operationService.performOperation(new PerformOperationRequest( OperationType.ADDITION, BigDecimal.valueOf(10), null), principal);
        } catch (Exception e) {
            Assertions.assertEquals(ErrorCode.EC0008.message, e.getMessage());
        }
    }

    @Test
    public void testPerformOperation_InvalidOneOperators() {
        when(operationRepository.findByType(any())).thenReturn(Optional.empty());
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        try {
            operationService.performOperation(new PerformOperationRequest( OperationType.SQUARE_ROOT, null, null), principal);
        } catch (Exception e) {
            Assertions.assertEquals(ErrorCode.EC0009.message, e.getMessage());
        }
    }

    @Test
    public void testPerformOperation_InsufficientBalance() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ROLE_USER);
        Record record = new Record();
        record.setUserBalance(BigDecimal.valueOf(0));
        user.setRecordsById(List.of(record));
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        when(operationRepository.findByType(OperationType.ADDITION)).thenReturn(Optional.of(new Operation(1L,BigDecimal.valueOf(30) , OperationType.ADDITION, null)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recordRepository.findTopByUserByUserId(any(), any(Sort.class))).thenReturn(Optional.of(record));
        try {
            operationService.performOperation(new PerformOperationRequest( OperationType.ADDITION, BigDecimal.valueOf(10), BigDecimal.valueOf(20)), principal);
        } catch (Exception e) {
            Assertions.assertEquals(ErrorCode.EC0007.message, e.getMessage());
        }
    }

    @Test
    public void testGetOperations() {
        when(operationRepository.findAll())
                .thenReturn(Set.of(
                        new Operation(1L, BigDecimal.valueOf(10), OperationType.ADDITION,null),
                        new Operation(1L, BigDecimal.valueOf(20),OperationType.SUBTRACTION, null),
                        new Operation(1L, BigDecimal.valueOf(30),OperationType.DIVISION, null),
                        new Operation(1L,BigDecimal.valueOf(40), OperationType.SQUARE_ROOT, null),
                        new Operation(1L, BigDecimal.valueOf(50),OperationType.MULTIPLICATION, null),
                        new Operation(1L, BigDecimal.valueOf(60), OperationType.RANDOM_STRING, null)
                ));

        Set<OperationResponse> operations = operationService.getOperations();

        Assertions.assertEquals(6, operations.size());
        Assertions.assertEquals(OperationType.ADDITION, operations.stream().filter(operation -> operation.getType().equals(OperationType.ADDITION)).findFirst().get().getType());
        Assertions.assertEquals(BigDecimal.valueOf(10), operations.stream().filter(operation -> operation.getType().equals(OperationType.ADDITION)).findFirst().get().getCost());
    }


}

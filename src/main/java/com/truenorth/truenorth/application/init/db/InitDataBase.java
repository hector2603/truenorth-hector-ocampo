package com.truenorth.truenorth.application.init.db;

import com.truenorth.truenorth.domain.model.Operation;
import com.truenorth.truenorth.domain.model.enums.OperationType;
import com.truenorth.truenorth.infraestructure.api.consumer.RandomStringGenerator;
import com.truenorth.truenorth.infraestructure.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Configuration
public class InitDataBase {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    OperationRepository operationRepository;

    CommandLineRunner runnerOperation() {
        return args -> {
            Set<Operation> operation = operationRepository.findAll();
            if (operation.size() < 6) {
                LOGGER.info("******* Inserting Operation to DB *******");
                Operation op1 = new Operation(0L, BigDecimal.valueOf(4.5) , OperationType.ADDITION, null);
                Operation op2 = new Operation(0L, BigDecimal.valueOf(5.5) , OperationType.SUBTRACTION, null);
                Operation op3 = new Operation(0L, BigDecimal.valueOf(6) , OperationType.MULTIPLICATION, null);
                Operation op4 = new Operation(0L, BigDecimal.valueOf(6) , OperationType.DIVISION, null);
                Operation op5 = new Operation(0L, BigDecimal.valueOf(9) , OperationType.SQUARE_ROOT, null);
                Operation op6 = new Operation(0L, BigDecimal.valueOf(10) , OperationType.RANDOM_STRING, null);
                operationRepository.saveAll(List.of(op1,op2,op3,op4,op5,op6));
            } else {
                LOGGER.info("******* Operation stored in DB Size :: {}", operation.size());
                LOGGER.info("******* Operation stored in DB :: {}", operation);
            }
        };
    }

    @Bean
    void listOfMovies() throws Exception {
        runnerOperation().run();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

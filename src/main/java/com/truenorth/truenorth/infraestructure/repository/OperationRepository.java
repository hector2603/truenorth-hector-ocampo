package com.truenorth.truenorth.infraestructure.repository;

import com.truenorth.truenorth.domain.model.Operation;
import com.truenorth.truenorth.domain.model.enums.OperationType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface OperationRepository extends CrudRepository<Operation, Long> {

    Optional<Operation> findByType(OperationType operationType);
    Set<Operation> findAll();
}

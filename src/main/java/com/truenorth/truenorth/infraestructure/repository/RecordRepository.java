package com.truenorth.truenorth.infraestructure.repository;

import com.truenorth.truenorth.domain.model.Record;
import com.truenorth.truenorth.domain.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends CrudRepository<Record, Long>{
    Optional<Record> findTopByUserByUserId(User user, Sort sort);
    List<Record> findAllByUserByUserId(User user );
}

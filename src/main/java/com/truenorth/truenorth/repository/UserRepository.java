package com.truenorth.truenorth.repository;

import com.truenorth.truenorth.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

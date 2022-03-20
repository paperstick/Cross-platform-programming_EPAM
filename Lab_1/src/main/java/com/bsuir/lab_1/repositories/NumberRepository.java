package com.bsuir.lab_1.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import com.bsuir.lab_1.models.CustomNumberModel;

public interface NumberRepository extends CrudRepository<CustomNumberModel, Integer> {
    Optional<CustomNumberModel> findByNumber(Double number);

    Optional<CustomNumberModel> findByProcessId(Integer processId);
}
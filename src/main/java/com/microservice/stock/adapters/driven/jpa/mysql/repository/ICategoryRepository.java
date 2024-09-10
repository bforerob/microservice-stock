package com.microservice.stock.adapters.driven.jpa.mysql.repository;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(String name);
    List<CategoryEntity> findByNameIn(List<String> names);

}

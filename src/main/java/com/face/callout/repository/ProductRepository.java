package com.face.callout.repository;

import com.face.callout.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, String> {

	@Override
    void delete(Products deleted);
}

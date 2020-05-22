package com.face.callout.repository;

import com.face.callout.entity.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    Dataset getByName(String name);
}

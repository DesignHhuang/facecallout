package com.face.callout.repository;

import com.face.callout.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findAllByIsGrouped(boolean isGrouped, Pageable pageable);
    Page<Person> findAllByIdBetween(Long start,  Long end, Pageable pageable);
    Page<Person> findAllByIdBetweenAndIsGrouped(Long start,  Long end, boolean isGrouped, Pageable pageable);
    Long countPeopleByIdBetweenAndIsGrouped(Long start,  Long end, boolean isGrouped);
    Long countPeopleByIdBetween(Long start,  Long end);
}

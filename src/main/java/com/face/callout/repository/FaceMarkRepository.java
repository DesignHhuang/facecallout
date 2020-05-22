package com.face.callout.repository;

import com.face.callout.entity.CallOut;
import com.face.callout.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaceMarkRepository extends JpaRepository<CallOut, Long> {

    Page<CallOut> findAllByIsMark(boolean ismark, Pageable pageable);

    List<CallOut> findAllByPerson(Person person);

    List<CallOut> findTop7ByPerson(Person person);

    Long countAllByPerson(Person person);

    Long countAllByPersonAndIsMark(Person person, boolean ismark);


}

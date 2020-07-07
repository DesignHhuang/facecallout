package com.face.callout.repository;

import com.face.callout.entity.User;
import com.face.callout.entity.Zan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZanRepository extends JpaRepository<Zan, Long> {
    Zan findZanByTypenameAndTypeidAndCreator(String type_name, Long type_id, User creater);
}

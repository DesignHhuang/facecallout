package com.face.callout.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //姓名 or ID
    private String name;

    //是否完成分组
    private Boolean isGrouped = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGrouped() {
        return isGrouped;
    }

    public void setGrouped(Boolean grouped) {
        isGrouped = grouped;
    }
}

package com.face.callout.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "attributes")
@EntityListeners(AuditingEntityListener.class)
public class Attributes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //扩展属性名称
    private String name;

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
}

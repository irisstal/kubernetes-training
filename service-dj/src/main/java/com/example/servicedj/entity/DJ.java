package com.example.servicedj.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DJ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long djId;
    private String name;
    private Integer age;
    private String style;

    public DJ() { // No-argument constructor
    }

    public DJ(Long djId, // Methods with parameters
              String name,
              Integer age,
              String style) {
        this.djId = djId;
        this.name = name;
        this.age = age;
        this.style = style;
    }

    public DJ(String name, //Method without Id (database will construct one for us)
              Integer age,
              String style) {
        this.name = name;
        this.age = age;
        this.style = style;
    }

    public Long getDjId() {
        return djId;
    }

    public void setDjId(Long djId) {
        this.djId = djId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "DJ{" +
                "dj_Id=" + djId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", style='" + style + '\'' +
                '}';
    }
}

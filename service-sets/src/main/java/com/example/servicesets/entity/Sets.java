package com.example.servicesets.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Sets")
public class Sets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setsId;
    private String setsName;

    public Sets() {
    }

    public Sets(Long setsId, String setsName) {
        this.setsId = setsId;
        this.setsName = setsName;
    }

    public Sets(String setsName) {
        this.setsName = setsName;
    }

    public Long getSetsId() {
        return setsId;
    }

    public void setSetsId(Long setsId) {
        this.setsId = setsId;
    }

    public String getSetsName() {
        return setsName;
    }

    public void setSetsName(String setsName) {
        this.setsName = setsName;
    }
}

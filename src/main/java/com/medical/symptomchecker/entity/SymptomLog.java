package com.medical.symptomchecker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "symptom_logs")
public class SymptomLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symptoms;

    @Column(columnDefinition = "LONGTEXT")   // 🔥 VERY IMPORTANT FIX
    private String result;

    public Long getId() {
        return id;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}


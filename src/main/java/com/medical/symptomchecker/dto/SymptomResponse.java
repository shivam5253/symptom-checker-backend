package com.medical.symptomchecker.dto;

public class SymptomResponse {

    private String message;
    private SymptomAnalysis analysis;

    public SymptomResponse(String message, SymptomAnalysis analysis) {
        this.message = message;
        this.analysis = analysis;
    }

    public String getMessage() {
        return message;
    }

    public SymptomAnalysis getAnalysis() {
        return analysis;
    }
}



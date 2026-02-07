package com.medical.symptomchecker.dto;

public class SymptomAnalysis {

    private String possibleConditions;
    private String riskLevel;
    private String advice;

    public SymptomAnalysis(String possibleConditions, String riskLevel, String advice) {
        this.possibleConditions = possibleConditions;
        this.riskLevel = riskLevel;
        this.advice = advice;
    }

    public String getPossibleConditions() {
        return possibleConditions;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getAdvice() {
        return advice;
    }
}


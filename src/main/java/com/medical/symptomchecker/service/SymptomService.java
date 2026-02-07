package com.medical.symptomchecker.service;

import com.medical.symptomchecker.dto.SymptomRequest;
import com.medical.symptomchecker.dto.SymptomResponse;

public interface SymptomService {
    SymptomResponse saveSymptoms(SymptomRequest request);
}

package com.medical.symptomchecker.service.impl;

import com.medical.symptomchecker.dto.SymptomAnalysis;
import com.medical.symptomchecker.dto.SymptomRequest;
import com.medical.symptomchecker.dto.SymptomResponse;
import com.medical.symptomchecker.entity.SymptomLog;
import com.medical.symptomchecker.repository.SymptomRepository;
import com.medical.symptomchecker.service.SymptomService;
import org.springframework.stereotype.Service;

@Service
public class SymptomServiceImpl implements SymptomService {

    private final SymptomRepository repository;

    public SymptomServiceImpl(SymptomRepository repository) {
        this.repository = repository;
    }

    @Override
    public SymptomResponse saveSymptoms(SymptomRequest request) {

        // --------- TEMPORARY: BYPASS AI COMPLETELY ---------
        // (This proves your frontend + backend + Railway are correct)

        SymptomAnalysis analysis = new SymptomAnalysis(
                "Likely viral infection (flu or cold)",
                "Medium",
                "Rest, hydration, and consult doctor if fever persists"
        );

        SymptomLog log = new SymptomLog();
        log.setSymptoms(request.getSymptoms());
        log.setResult("AI temporarily bypassed");
        repository.save(log);

        return new SymptomResponse("Symptoms analyzed", analysis);
    }
}







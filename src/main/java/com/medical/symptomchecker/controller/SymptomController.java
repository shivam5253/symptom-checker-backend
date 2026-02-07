package com.medical.symptomchecker.controller;

import com.medical.symptomchecker.dto.SymptomRequest;
import com.medical.symptomchecker.dto.SymptomResponse;
import com.medical.symptomchecker.service.SymptomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SymptomController {

    private final SymptomService service;

    public SymptomController(SymptomService service) {
        this.service = service;
    }

    @PostMapping("/symptoms")
    public SymptomResponse analyzeSymptoms(@RequestBody SymptomRequest request) {
        return service.saveSymptoms(request);
    }

    @GetMapping("/health")
    public String health() {
        return "Backend is alive";
    }
}



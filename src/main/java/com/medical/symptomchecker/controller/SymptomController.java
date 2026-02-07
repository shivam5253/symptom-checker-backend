package com.medical.symptomchecker.controller;

import com.medical.symptomchecker.dto.SymptomRequest;
import com.medical.symptomchecker.dto.SymptomResponse;
import com.medical.symptomchecker.service.SymptomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/symptoms")
@CrossOrigin(origins = "https://symptom-checker-psi.vercel.app/")
public class SymptomController {

    private final SymptomService service;

    public SymptomController(SymptomService service) {
        this.service = service;
    }

    @PostMapping("/symptoms")
    public SymptomResponse saveSymptoms(@RequestBody SymptomRequest request) {
        return service.saveSymptoms(request);
    }
}


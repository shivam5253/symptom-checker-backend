package com.medical.symptomchecker.service.impl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.medical.symptomchecker.config.HuggingFaceConfig;
import com.medical.symptomchecker.dto.SymptomAnalysis;
import com.medical.symptomchecker.dto.SymptomRequest;
import com.medical.symptomchecker.dto.SymptomResponse;
import com.medical.symptomchecker.entity.SymptomLog;
import com.medical.symptomchecker.repository.SymptomRepository;
import com.medical.symptomchecker.service.SymptomService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SymptomServiceImpl implements SymptomService {

    private final SymptomRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public SymptomServiceImpl(SymptomRepository repository) {
        this.repository = repository;
    }

    @Override
    public SymptomResponse saveSymptoms(SymptomRequest request) {

        String rawResponse = "";
        String aiText = "";

        try {

            String prompt =
                    "You are a medical assistant. Based on these symptoms, respond EXACTLY like this:\n\n" +
                            "Possible Conditions: <one line>\n" +
                            "Risk Level: Low / Medium / High\n" +
                            "Advice: <one line>\n\n" +
                            "Symptoms: " + request.getSymptoms();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + HuggingFaceConfig.HF_API_TOKEN);

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "meta-llama/llama-3.1-8b-instruct");
            body.put("messages", List.of(message));

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            rawResponse =
                    restTemplate.postForObject(
                            HuggingFaceConfig.HF_API_URL,
                            entity,
                            String.class
                    );

            JsonNode root = mapper.readTree(rawResponse);
            aiText = root.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {
            rawResponse = "LLM failed: " + e.getMessage();
            aiText = "AI unavailable right now.";
        }

        SymptomLog log = new SymptomLog();
        log.setSymptoms(request.getSymptoms());
        log.setResult(rawResponse);
        repository.save(log);

        String possibleConditions = extract(aiText, "Possible Conditions:");
        String riskLevel = extract(aiText, "Risk Level:");
        String advice = extract(aiText, "Advice:");

        SymptomAnalysis analysis = new SymptomAnalysis(
                possibleConditions,
                riskLevel,
                advice
        );

        return new SymptomResponse("Symptoms analyzed", analysis);
    }

    private String extract(String text, String key) {
        int start = text.indexOf(key);
        if (start == -1) return "Not available";

        int end = text.indexOf("\n", start);
        if (end == -1) end = text.length();

        return text.substring(start + key.length(), end).trim();
    }
}






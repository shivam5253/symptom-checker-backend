
package com.medical.symptomchecker.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.symptomchecker.config.HuggingFaceConfig;
import com.medical.symptomchecker.dto.SymptomAnalysis;
import com.medical.symptomchecker.dto.SymptomRequest;
import com.medical.symptomchecker.dto.SymptomResponse;
import com.medical.symptomchecker.repository.SymptomRepository;
import com.medical.symptomchecker.service.SymptomService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SymptomServiceImpl implements SymptomService {

    private static final Logger logger =
            LoggerFactory.getLogger(SymptomServiceImpl.class);

    private final SymptomRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public SymptomServiceImpl(SymptomRepository repository) {
        this.repository = repository;
    }

    @Override
    public SymptomResponse saveSymptoms(SymptomRequest request) {

        String aiText = "";

        try {

            // Check whether API key is configured
            if (HuggingFaceConfig.HF_API_TOKEN == null ||
                    HuggingFaceConfig.HF_API_TOKEN.isBlank()) {
                throw new IllegalStateException(
                        "OPENROUTER_API_KEY environment variable is missing"
                );
            }

            String prompt =
                    "You are a medical assistant. Based on these symptoms, respond like:\n\n" +
                    "Possible Conditions: <one line>\n" +
                    "Risk Level: Low / Medium / High\n" +
                    "Advice: <one line>\n\n" +
                    "Symptoms: " + request.getSymptoms();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization",
                    "Bearer " + HuggingFaceConfig.HF_API_TOKEN);

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "openrouter/free");
            body.put("messages", List.of(message));

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            logger.info("Sending symptom analysis request to OpenRouter");

            String rawResponse =
                    restTemplate.postForObject(
                            HuggingFaceConfig.HF_API_URL,
                            entity,
                            String.class
                    );

            JsonNode root = mapper.readTree(rawResponse);

            JsonNode content = root.path("choices")
                    .path(0)
                    .path("message")
                    .path("content");

            if (content.isMissingNode() || content.isNull()) {
                throw new IllegalStateException(
                        "AI response does not contain message content"
                );
            }

            aiText = content.asText();

            logger.info("OpenRouter response received successfully");

        } catch (HttpStatusCodeException e) {

            logger.error(
                    "OpenRouter HTTP error. Status: {}, Response: {}",
                    e.getStatusCode(),
                    e.getResponseBodyAsString()
            );

            aiText = fallbackResponse();

        } catch (Exception e) {

            logger.error("Symptom analysis failed: ", e);

            aiText = fallbackResponse();
        }

        String possibleConditions =
                extract(aiText, "Possible Conditions:");

        String riskLevel =
                extract(aiText, "Risk Level:");

        String advice =
                extract(aiText, "Advice:");

        SymptomAnalysis analysis = new SymptomAnalysis(
                possibleConditions,
                riskLevel,
                advice
        );

        return new SymptomResponse("Symptoms analyzed", analysis);
    }

    private String fallbackResponse() {
        return "Possible Conditions: AI temporarily unavailable\n" +
                "Risk Level: Medium\n" +
                "Advice: Please consult a doctor.";
    }

    private String extract(String text, String key) {

        int start = text.indexOf(key);

        if (start == -1) {
            return "Not available";
        }

        int end = text.indexOf("\n", start);

        if (end == -1) {
            end = text.length();
        }

        return text.substring(start + key.length(), end).trim();
    }
}








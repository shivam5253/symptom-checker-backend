
package com.medical.symptomchecker.config;

public class HuggingFaceConfig {

    // OpenRouter API endpoint
    public static final String HF_API_URL =
            "https://openrouter.ai/api/v1/chat/completions";

    // Read API key from environment variable
    public static final String HF_API_TOKEN =
            System.getenv("OPENROUTER_API_KEY");

}










🩺 Medical Symptom Checker
An AI-powered web application where users enter symptoms and receive AI-generated information about possible conditions, risk levels, and general health advice.
🔗 Live Application: https://symptom-checker-psi.vercel.app/
Features
•	Enter symptoms through a React interface.
•	Send symptoms to a Spring Boot REST API.
•	Generate AI responses using OpenRouter.
•	Display possible conditions, risk levels, and general advice.
•	Handle API errors with fallback responses.
Tech Stack
Technology	Purpose
Java	Backend
Spring Boot	REST API
React	Frontend
OpenRouter API	AI integration
Jackson	JSON parsing
Maven	Dependency management
Vercel	Frontend deployment
Render	Backend deployment
Architecture
USER
  |
React Frontend (Vercel)
  |
POST /api/symptoms
  |
Spring Boot Backend (Render)
  |
SymptomController
  |
SymptomServiceImpl
  |
OpenRouter API
  |
AI Model
  |
AI Generated Response
  |
JSON Response to React
  |
USER
How It Works
1.	User enters symptoms in the frontend.
2.	React sends a POST request to Spring Boot.
3.	SymptomController receives the request.
4.	SymptomServiceImpl prepares the AI prompt.
5.	Backend sends the prompt to OpenRouter.
6.	AI model generates a response.
7.	Jackson parses the JSON response.
8.	Backend returns the result to React.
9.	React displays the analysis.
API Endpoint
POST /api/symptoms
Example request:
{
  "symptoms": "headache and mild fever"
}
Configuration
•	API key stored securely in Render environment variables.
•	Environment variable: OPENROUTER_API_KEY
•	Model: openrouter/free
Challenges & Improvements
•	Deployed Spring Boot backend on Render.
•	Connected React frontend through REST APIs.
•	Configured secure API key management.
•	Fixed an unavailable OpenRouter model.
•	Added exception handling and logging.
•	Configured backend health checks.
Disclaimer
This project is for educational purposes only. AI-generated information is not a medical diagnosis or a substitute for professional medical advice.
Developed by Shivam Mittal

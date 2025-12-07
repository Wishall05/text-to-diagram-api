# Diagram Generator - Spring Boot (Groq-backed)

This project provides an API that converts natural-language system descriptions into:
- JSON graph (nodes + edges)
- Mermaid diagram
- PlantUML diagram

It uses Groq (OpenAI-compatible API) to parse text into structured JSON and diagram code.

## Quick start (local)

1. Add your Groq API key to `src/main/resources/application.properties`:
```
groq.api.key=YOUR_GROQ_API_KEY
```

2. Build:
```
mvn clean package
```

3. Run:
```
java -jar target/diagram-generator-0.0.1-SNAPSHOT.jar
```

4. Example request:
```
POST http://localhost:8080/api/diagram/generate
Content-Type: application/json

{ "text": "A load balancer routes traffic to two backend services which read from MongoDB." }
```

## Docker

Build:
```
mvn clean package
docker build -t diagram-generator .
docker run -p 8080:8080 -e GROQ_API_KEY=your_key diagram-generator
```


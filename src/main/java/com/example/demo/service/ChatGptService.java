package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private final String apiKey;

    public ChatGptService(WebClient.Builder webClientBuilder, @Value("${openai.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com").build();
        this.apiKey = apiKey;
    }

    // Metode til at bygge request-body for OpenAI API
    private Map<String, Object> createRequestBody(String ingredients) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo"); // Du kan vælge f.eks. "gpt-4" afhængigt af din API-adgang

        // Vi bruger "messages"-parameteren som krævet af OpenAI API
        body.put("messages", Arrays.asList(
                Map.of("role", "system", "content", "You are a helpful assistant."),
                Map.of("role", "user", "content", "Give me a recipe based on the ingredients: " + ingredients)
        ));

        body.put("max_tokens", 150);  // Maksimalt antal tokens i svaret
        body.put("temperature", 0.7); // Sætter temperaturparameteren for kreativitet

        return body; // Returnerer den opbyggede request body
    }

    // Metode til at få opskrift baseret på ingredienser
    public Mono<String> getRecipe(String ingredients) {
        return webClient.post()  // Sender en POST-anmodning til OpenAI API
                .uri("/v1/chat/completions")  // Specifik endpoint for chat completions
                .header("Authorization", "Bearer " + apiKey)  // Sætter API-nøglen som header
                .bodyValue(createRequestBody(ingredients))  // Sender request body
                .retrieve()  // Henter svaret
                .bodyToMono(Map.class)  // Modtager svaret som en Mono<Map> (hvor OpenAI svarer med en Map)
                .map(response -> {
                    // Henter svaret fra API'et og returnerer det som en String
                    Map<String, Object> choices = (Map<String, Object>) ((Map<String, Object>) response).get("choices");
                    return (String) ((Map<String, Object>) choices.get(0)).get("message");
                })
                .onErrorResume(e -> {
                    return Mono.just("Der opstod en fejl, prøv igen senere.");  // Fejlhåndtering
                });
    }
}

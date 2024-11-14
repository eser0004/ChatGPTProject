package com.example.demo.service;

import com.example.demo.dto.SpoonacularResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {

    private final WebClient webClient;

    @Value("${spoonacular.api.key}")
    private String apiKey;

    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> fetchFoodData() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https") // Angiv korrekt protokol (https://)
                        .host("api.spoonacular.com") // Angiv korrekt host
                        .path("/food/products/search")
                        .queryParam("query", "vegetarian")
                        .queryParam("number", 5)
                        .build())
                .header("x-api-key", apiKey)
                .retrieve()
                .bodyToMono(SpoonacularResponseDto.class)
                .map(response -> String.join(", ",
                        response.getProducts().stream()
                                .map(SpoonacularResponseDto.Product::getTitle)
                                .toArray(String[]::new)));
    }
}

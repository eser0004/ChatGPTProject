package com.example.demo.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RecipeService {

    private final ExternalApiService externalApiService;
    private final ChatGptService chatGptService;

    public RecipeService(ExternalApiService externalApiService, ChatGptService chatGptService) {
        this.externalApiService = externalApiService;
        this.chatGptService = chatGptService;
    }

    public Mono<String> generateRecipe(String foodPreference) {
        return externalApiService.fetchFoodData()
                .flatMap(data -> chatGptService.getRecipe(data + " " + foodPreference));
    }
}

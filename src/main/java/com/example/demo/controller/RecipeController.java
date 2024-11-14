package com.example.demo.controller;

import com.example.demo.service.ChatGptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RecipeController {

    private final ChatGptService chatGptService;

    public RecipeController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping("/generate-recipe")
    public Mono<String> generateRecipe(@RequestParam String foodPreference) {
        return chatGptService.getRecipe(foodPreference);  // Kald til ChatGptService for at få opskrift
    }
}

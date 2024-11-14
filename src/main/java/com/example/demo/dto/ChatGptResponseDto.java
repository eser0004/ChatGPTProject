package com.example.demo.dto;

public class ChatGptResponseDto {
    private String content;

    public ChatGptResponseDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

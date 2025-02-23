package com.spring.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AlController {

    private final ChatClient chatClient;

    public AlController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat/{message}")
    public String openAiChat(@PathVariable("message") String message) {
        return chatClient.prompt()
                        .user(message)
                        .call()
                        .content();
    }

    @GetMapping("/film/{actor}")
    public ActorsFilms openAiFilm(@PathVariable("actor") String actor) {
        return chatClient.prompt()
                .user(u -> u.text("The best 5 films for {actor}")
                    .param("actor", actor))
                .call()
                .entity(ActorsFilms.class);
    }

    record ActorsFilms(String actor, List<String> movies) { }
}

package com.zhaojh.research.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "BookApi")
public class AiController {

    ChatModel chatModel;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {

        Prompt prompt = new PromptTemplate("""
                你是一个可以操作数据库的助手。
                可用函数：
                - getAllBooks(pageable)
                
                用户输入:
                {input}
                """).create(Map.of("input", message));

        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}

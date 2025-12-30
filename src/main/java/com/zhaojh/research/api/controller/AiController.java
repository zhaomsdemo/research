package com.zhaojh.research.api.controller;

import com.zhaojh.research.api.request.Question;
import com.zhaojh.research.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "AiChat")
@Slf4j
public class AiController {

    final OllamaChatModel chatModel;
    final BookService bookService;
    ChatClient chatClient;
    final ChatClient.Builder chatClientBuilder;

    @Value( "classpath:/prompts/question.prompt")
    Resource questionResource;

    @PostConstruct
    public void init() {
        chatClient = chatClientBuilder.build();
    }

    @Operation(operationId = "chat", summary = "Chat with AI to get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody Mono<Map<String, String>> body) {

        return body.flatMap(message -> Mono.fromCallable(() -> {
            String input = message.get("input");
            Prompt prompt = new Prompt("""
                     你是一个数据库助手。
                    【重要规则】
                     1. 所有书籍数据【必须】来自函数返回结果
                     2. 【禁止】凭空编造、猜测或假设任何书籍数据
                     3. 如果需要书籍数据，必须调用对应函数
                     4. 如果函数返回空列表，请明确说明“当前没有书籍”
                     可用函数：
                     - getAllBooks(page, size))
                     - getBookById(id)
                    
                     用户输入:""" + input);
            ChatResponse chatResponse = chatModel.call(prompt);
            return chatResponse.getResults()
                    .get(chatResponse.getResults().size() - 1)
                    .getOutput().getText();
        }).subscribeOn(Schedulers.boundedElastic()));
    }

    @GetMapping("/books")
    public Mono<String> listBooks() {

        return bookService.getAllBooks(1, 10)          // Flux<Book>
                .collectList()             // Mono<List<Book>>
                .flatMap(books -> {

                    if (books.isEmpty()) {
                        return Mono.just("当前数据库中没有任何书籍。");
                    }

                    Prompt prompt = new Prompt("""
                            你是一个助手，只能基于以下真实数据进行说明。
                            【禁止】添加、猜测或修改任何数据。
                            
                            书籍数据：
                            %s
                            
                            请用中文列出所有书籍。
                            """.formatted(books));

                    return Mono.fromCallable(() -> {
                        ChatResponse response = chatModel.call(prompt);
                        return response.getResults()
                                .get(response.getResults().size() - 1)
                                .getOutput()
                                .getText();
                    }).subscribeOn(Schedulers.boundedElastic());
                });
    }

    @PostMapping("/ask")
    public Flux<String> askQuestion(@RequestBody Question question) {
        return chatClient.prompt()
                .user(spec -> spec.text(question.question())
                        .param("question", question.question())
                        .param("language", question.language())
                )
                .stream()
                .content();
    }
}

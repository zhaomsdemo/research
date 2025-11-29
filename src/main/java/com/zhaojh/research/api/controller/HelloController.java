package com.zhaojh.research.api.controller;

import com.zhaojh.research.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
@Tag(name = "Hello World")
public class HelloController {

    @RequestMapping("/world")
    @Operation(summary = "Hello World")
    public Mono<Result<String>> helloWorld() {
        return Mono.just(Result.success("Hello World!"));
    }
}

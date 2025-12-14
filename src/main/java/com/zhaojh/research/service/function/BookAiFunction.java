package com.zhaojh.research.service.function;

import com.zhaojh.research.common.request.BookRequest;
import com.zhaojh.research.dao.model.Book;
import com.zhaojh.research.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookAiFunction {

    BookService bookService;

    @Bean
    public Function<BookRequest, Mono<Book>> createBook() {
        return  bookRequest -> bookService.createBook(bookRequest);
    }

    @Bean
    public Function<String, Mono<Book>> getBookById() {
        return bookService::getBookById;
    }

    @Bean
    public Function<Pageable, Flux<Book>> getAllBooks() {
        return bookService::getAllBooks;
    }
}

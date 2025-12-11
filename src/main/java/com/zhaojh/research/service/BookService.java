package com.zhaojh.research.service;

import com.zhaojh.research.common.request.BookRequest;
import com.zhaojh.research.dao.model.Book;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Book> createBook(BookRequest bookDto);

    Mono<Book> getBookById(String id);

    Flux<Book> getAllBooks(Pageable pageable);

    Mono<Book> updateBook(String id, BookRequest bookDto);

    Mono<Book> deleteBookById(String id);
}

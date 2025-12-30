package com.zhaojh.research.service.impl;

import com.zhaojh.research.common.request.BookRequest;
import com.zhaojh.research.dao.model.Book;
import com.zhaojh.research.dao.repository.BookRepository;
import com.zhaojh.research.service.BookService;
import com.zhaojh.research.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Mono<Book> createBook(BookRequest bookDto) {
        return Mono.just(bookMapper.toBook(bookDto))
                .flatMap(bookRepository::save);
    }

    @Override
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id).switchIfEmpty(Mono.error(() -> new RuntimeException("Book not found")));
    }

    @Override
    public Flux<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAllBooks(pageable);
    }

    @Override
    public Flux<Book> getAllBooks(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return bookRepository.findAllBooks(pageable);
    }

    @Override
    public Mono<Book> updateBook(String id, BookRequest bookDto) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    Book updatedBook = bookMapper.toBook(bookDto);
                    updatedBook.setId(existingBook.getId());
                    return updatedBook;
                })
                .flatMap(bookRepository::save);
    }

    @Override
    public Mono<Book> deleteBookById(String id) {
        return bookRepository.findById(id)
                .flatMap(book -> bookRepository.deleteById(id)
                        .thenReturn(book));
    }
}

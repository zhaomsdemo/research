package com.zhaojh.research.dao.repository;

import com.zhaojh.research.dao.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByIsbnLike(String isbn, Pageable pageable);
    Flux<Book> findByTitleLike(String title, Pageable pageable);
    Flux<Book> findByAuthorLike(String author, Pageable pageable);
    Flux<Book> findByYear(String year, Pageable pageable);
    @Query(value = "{}")
    Flux<Book> findAllBooks(Pageable pageable);
}

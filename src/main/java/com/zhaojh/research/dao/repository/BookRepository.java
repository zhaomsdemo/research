package com.zhaojh.research.dao.repository;

import com.zhaojh.research.dao.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByIsbnLike(String isbn);
    Flux<Book> findByTitleLike(String title);
    Flux<Book> findByAuthorLike(String author);
    Flux<Book> findByYear(String year);
}

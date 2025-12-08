package com.zhaojh.research.dao.repository;

import com.zhaojh.research.dao.model.Rating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {

    Flux<Rating> findByUserId(Integer userId);
    Flux<Rating> findByIsbnLike(String isbn);
    Flux<Rating> findByRatingGreaterThan(Integer rating);
}

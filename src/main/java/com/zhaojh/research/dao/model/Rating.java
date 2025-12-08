package com.zhaojh.research.dao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "ratings")
public class Rating {

    @MongoId
    String id;
    @JsonProperty("User-ID")
    Integer userId;
    @JsonProperty("ISBN")
    String isbn;
    @JsonProperty("Book-Rating")
    Integer rating;
}

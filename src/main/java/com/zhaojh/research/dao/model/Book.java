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
@Document(collection = "books")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @MongoId
    String id;
    @JsonProperty("ISBN")
    String isbn;
    @JsonProperty("Book-Title")
    String title;
    @JsonProperty("Book-Author")
    String author;
    @JsonProperty("Year-Of-Publication")
    String year;
    @JsonProperty("Publisher")
    String publisher;
    @JsonProperty("Image-URL-S")
    String shortImageUrl;
    @JsonProperty("Image-URL-M")
    String mediumImageUrl;
    @JsonProperty("Image-URL-L")
    String longImageUrl;
}

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
@Document(collection = "users")
public class User {

    @MongoId
    String id;
    @JsonProperty("User-ID")
    Integer userId;
    @JsonProperty("Location")
    String location;
    @JsonProperty("Age")
    Integer age;
}

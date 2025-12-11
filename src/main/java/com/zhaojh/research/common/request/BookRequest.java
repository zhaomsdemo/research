package com.zhaojh.research.common.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class BookRequest {

    String id;
    String isbn;
    @Size(min = 1, max = 50)
    String title;
    String author;
    @Pattern(regexp = "^(19|20)\\d{2}$")
    String year;
    String publisher;
    String shortImageUrl;
    String mediumImageUrl;
    String longImageUrl;
}

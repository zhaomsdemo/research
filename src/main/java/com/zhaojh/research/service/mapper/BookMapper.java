package com.zhaojh.research.service.mapper;

import com.zhaojh.research.common.request.BookRequest;
import com.zhaojh.research.dao.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface BookMapper {

    Book toBook(BookRequest bookRequest);
}

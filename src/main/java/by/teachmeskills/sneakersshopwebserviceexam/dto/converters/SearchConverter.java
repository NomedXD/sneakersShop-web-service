package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Search;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SearchConverter {
    public SearchDto toDto(Search search) {
        return SearchDto.builder().searchString(search.getSearchString())
                .priceFrom(search.getPriceFrom())
                .priceTo(search.getPriceTo())
                .categoryName(search.getCategoryName()).build();
    }

    public Search fromDto(SearchDto searchDto) {
        return Search.builder().searchString(searchDto.getSearchString())
                .priceFrom(searchDto.getPriceFrom())
                .priceTo(searchDto.getPriceTo())
                .categoryName(searchDto.getCategoryName()).build();
    }
}

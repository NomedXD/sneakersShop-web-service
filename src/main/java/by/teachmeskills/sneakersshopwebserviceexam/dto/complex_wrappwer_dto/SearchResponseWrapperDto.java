package by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseWrapperDto {
    private SearchDto search;
    private Long totalSearchResults;
    private Integer currentPage;
    private Integer totalPaginatedVisiblePages;
    private Integer lastPageNumber;
    private List<ProductDto> products;
}

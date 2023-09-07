package by.teachmeskills.sneakersshopwebserviceexam.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private String searchString;
    private Float priceFrom;
    private Float priceTo;
    private String categoryName;
}

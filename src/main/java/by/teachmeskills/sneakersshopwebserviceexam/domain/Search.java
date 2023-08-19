package by.teachmeskills.sneakersshopwebserviceexam.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Search {
    // Additional fields while filtering in search page
    private String searchString;

    public Search(String searchString) {
        this.searchString = searchString;
    }

}

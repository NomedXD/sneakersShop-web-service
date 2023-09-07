package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;


import by.teachmeskills.sneakersshopwebserviceexam.domain.Product;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.NoSuchOrderException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.CategoryRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Data
public class ProductConverter {
    private final CategoryRepository categoryRepository;
    private final ImageConverter imageConverter;

    @Autowired
    public ProductConverter(CategoryRepository categoryRepository, ImageConverter imageConverter) {
        this.categoryRepository = categoryRepository;
        this.imageConverter = imageConverter;
    }

    public ProductDto toDto(Product product) {
        return Optional.ofNullable(product).map(p -> ProductDto.builder()
                .id(p.getId())
                .name(p.getName()).image(Optional.ofNullable(p.getImage()).map(imageConverter::toDto).orElse(null))
                .description(p.getDescription())
                .price(p.getPrice())
                .categoryId(p.getCategory().getId())
                .build()).orElse(null);
    }

    public Product fromDto(ProductDto productDto) {
        return Optional.ofNullable(productDto).map(p -> Product.builder()
                .id(p.getId())
                .name(p.getName()).image(Optional.ofNullable(p.getImage()).map(imageConverter::fromDto).orElse(null))
                .description(p.getDescription())
                .price(p.getPrice())
                .category(categoryRepository.findCategoryById(p.getCategoryId()).orElseThrow((() -> new NoSuchOrderException("Category not found. Id:", p.getCategoryId()))))
                .build()).orElse(null);
    }
}

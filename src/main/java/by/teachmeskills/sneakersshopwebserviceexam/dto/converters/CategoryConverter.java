package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Category;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Image;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.services.ImageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@Component
public class CategoryConverter {
    private final ImageConverter imageConverter;
    private final ProductConverter productConverter;
    private final ImageService imageService;

    // Здесь и в других конвертерах можно из более общего сразу получать дочерний конвертер
    @Autowired
    public CategoryConverter(ImageConverter imageConverter, ProductConverter productConverter, ImageService imageService) {
        this.imageConverter = imageConverter;
        this.productConverter = productConverter;
        this.imageService = imageService;
    }

    public CategoryDto toDto(Category category) {
        return Optional.ofNullable(category).map(c -> CategoryDto.builder()
                .id(c.getId())
                .name(c.getName()).image(Optional.ofNullable(c.getPrimeCategoryImage()).map(imageConverter::toDto).orElse(null))
                .sometext(c.getSometext())
                .productList(Optional.ofNullable(c.getProductList()).map(products -> products.stream().map(productConverter::toDto).toList()).orElse(List.of()))
                .build()).orElse(null);
    }

    public Category fromDto(CategoryDto categoryDto) {
        return Optional.ofNullable(categoryDto).map(c -> Category.builder()
                .id(c.getId())
                .name(c.getName()).images(new ArrayList<>(Collections.singletonList(imageConverter.fromDto(imageService.getImageById(c.getImage().getId())))))
                .sometext(c.getSometext())
                .productList(Optional.ofNullable(c.getProductList()).map(products -> products.stream().map(productConverter::fromDto).toList()).orElse(List.of()))
                .build()).orElse(null);
    }
}

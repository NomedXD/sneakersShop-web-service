package by.teachmeskills.sneakersshopwebserviceexam.services;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto) throws EntityOperationException;

    List<CategoryDto> read() throws EntityOperationException;

    CategoryDto update(CategoryDto categoryDto) throws EntityOperationException;

    void delete(Integer id) throws EntityOperationException;

    CategoryDto getCategoryById(Integer id) throws EntityOperationException;
}

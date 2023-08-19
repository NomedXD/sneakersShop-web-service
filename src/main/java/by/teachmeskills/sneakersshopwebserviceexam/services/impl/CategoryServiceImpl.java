package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.CategoryConverter;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.CategoryRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) throws EntityOperationException {
        return categoryConverter.toDto(categoryRepository.create(categoryConverter.fromDto(categoryDto)));
    }

    @Override
    public List<CategoryDto> read() throws EntityOperationException {
        return categoryRepository.read().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) throws EntityOperationException {
        return categoryConverter.toDto(categoryRepository.update(categoryConverter.fromDto(categoryDto)));
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        categoryRepository.delete(id);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws EntityOperationException {
        return categoryConverter.toDto(categoryRepository.getCategoryById(id));
    }
}

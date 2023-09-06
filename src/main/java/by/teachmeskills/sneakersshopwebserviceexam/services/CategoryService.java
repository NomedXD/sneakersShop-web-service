package by.teachmeskills.sneakersshopwebserviceexam.services;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    List<CategoryDto> read();

    CategoryDto update(CategoryDto categoryDto);

    void delete(Integer id);

    CategoryDto getCategoryById(Integer id);

    ResponseEntity<List<CategoryDto>> getPaginatedCategories(Integer currentPage, Integer pageSize);

    ResponseEntity<InputStreamResource> exportCategories() throws CSVExportException;

    ResponseEntity<List<CategoryDto>> importCategories(MultipartFile file) throws CSVImportException;
}

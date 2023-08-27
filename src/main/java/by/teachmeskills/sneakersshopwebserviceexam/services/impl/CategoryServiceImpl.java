package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.CategoryConverter;
import by.teachmeskills.sneakersshopwebserviceexam.enums.EshopConstants;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.CategoryRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    // Basic controllers reference
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

    @Override
    public ResponseEntity<String> exportCategories() throws CSVExportException {
        writeCsv();
        return new ResponseEntity<>(EshopConstants.successfulExportMessage, HttpStatus.OK);
    }

    private void writeCsv() throws CSVExportException {
        List<CategoryDto> categoryDtoList = categoryRepository.read().stream().map(categoryConverter::toDto).toList();
        try (Writer categoriesWriter = Files.newBufferedWriter(Paths.get("src/main/resources/categories.csv"))) {
            StatefulBeanToCsv<CategoryDto> productsSbc = new StatefulBeanToCsvBuilder<CategoryDto>(categoriesWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            productsSbc.write(categoryDtoList);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorCategoriesExportMessage);
        }
    }

    @Override
    public ResponseEntity<List<CategoryDto>> importCategories(MultipartFile file) throws CSVImportException {
        List<CategoryDto> categoryDtoList = parseCsv(file);
        if (Optional.ofNullable(categoryDtoList).isPresent()) {
            categoryDtoList.forEach(categoryDto -> {
                categoryDto.setId(0); // Чтобы создавался новый заказ, а не обновлялся этот же, если что - удалить
                categoryDto.setId(categoryRepository.create(categoryConverter.fromDto(categoryDto)).getId());
            });
            return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    private List<CategoryDto> parseCsv(MultipartFile file) throws CSVImportException {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader categoriesReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<CategoryDto> categoriesCtb = new CsvToBeanBuilder<CategoryDto>(categoriesReader)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withType(CategoryDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                return categoriesCtb.parse();
            } catch (IOException e) {
                throw new CSVImportException(EshopConstants.errorCategoriesImportMessage);
            }
        } else {
            throw new CSVImportException(EshopConstants.errorFileNullMessage);
        }
    }
}

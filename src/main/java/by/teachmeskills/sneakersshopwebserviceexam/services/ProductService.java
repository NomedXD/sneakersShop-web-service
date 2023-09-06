package by.teachmeskills.sneakersshopwebserviceexam.services;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Search;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.SearchResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto productDto);

    List<ProductDto> read();

    ProductDto update(ProductDto productDto);

    void delete(Integer id);

    List<ProductDto> getCategoryProducts(Integer categoryId);

    List<ProductDto> getOrderProducts(Integer orderId);

    ProductDto getProductById(Integer id);

    ResponseEntity<SearchResponseWrapperDto> getSearchedPaginatedProducts(SearchDto searchDto, Integer currentPage, Integer pageSize);

    ResponseEntity<List<ProductDto>> getPaginatedProductsByCategoryId(Integer categoryId, Integer currentPage, Integer pageSize);

    Long getCountOfAllProducts();

    Long getCountAppropriateProducts(Search search);

    ResponseEntity<InputStreamResource> exportCategoryProducts(Integer categoryId) throws CSVExportException;

    ResponseEntity<List<ProductDto>> importCategoryProducts(MultipartFile file) throws CSVImportException;

    //ModelAndView applyProductsQuantity(Cart cart, HttpServletRequest request);
}

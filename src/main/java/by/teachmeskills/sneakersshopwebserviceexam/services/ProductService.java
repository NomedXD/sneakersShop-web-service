package by.teachmeskills.sneakersshopwebserviceexam.services;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Search;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.SearchResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto productDto) throws EntityOperationException;

    List<ProductDto> read() throws EntityOperationException;

    ProductDto update(ProductDto productDto) throws EntityOperationException;

    void delete(Integer id) throws EntityOperationException;

    List<ProductDto> getCategoryProducts(Integer categoryId) throws EntityOperationException;

    List<ProductDto> getOrderProducts(Integer orderId) throws EntityOperationException;

    ProductDto getProductById(Integer id) throws EntityOperationException;

    ResponseEntity<SearchResponseWrapperDto> getPaginatedProducts(SearchDto searchDto, Integer currentPage) throws EntityOperationException;

    Long getCountOfAllProducts() throws EntityOperationException;

    Long getCountAppropriateProducts(Search search) throws EntityOperationException;

    ResponseEntity<InputStreamResource> exportCategoryProducts(Integer categoryId) throws CSVExportException;

    ResponseEntity<List<ProductDto>> importCategoryProducts(MultipartFile file) throws CSVImportException;

    //ModelAndView applyProductsQuantity(Cart cart, HttpServletRequest request);
}

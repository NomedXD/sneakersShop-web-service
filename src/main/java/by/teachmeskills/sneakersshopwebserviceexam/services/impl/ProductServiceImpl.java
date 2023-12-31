package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Product;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Search;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.SearchResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.ProductConverter;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.SearchConverter;
import by.teachmeskills.sneakersshopwebserviceexam.enums.EshopConstants;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.ProductRepository;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.ProductSearchSpecification;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SearchConverter searchConverter;
    private final ProductConverter productConverter;
    private final OrderService orderService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, @Lazy SearchConverter searchConverter,
                              @Lazy ProductConverter productConverter, @Lazy OrderService orderService) {
        this.productRepository = productRepository;
        this.searchConverter = searchConverter;
        this.productConverter = productConverter;
        this.orderService = orderService;
    }

    // Basic controllers reference
    @Override
    public ProductDto create(ProductDto productDto) {
        return productConverter.toDto(productRepository.save(productConverter.fromDto(productDto)));
    }

    @Override
    public List<ProductDto> read() {
        return productRepository.findAll().stream().map(productConverter::toDto).toList();
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        return productConverter.toDto(productRepository.save(productConverter.fromDto(productDto)));
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> getCategoryProducts(Integer categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> getOrderProducts(Integer orderId) {
        return orderService.getOrderById(orderId).getProductList();
    }

    @Override
    public ProductDto getProductById(Integer id) {
        return productConverter.toDto(productRepository.findProductById(id));
    }

    @Override
    public Long getCountOfAllProducts() {
        return productRepository.count();
    }

    @Override
    public Long getCountAppropriateProducts(Search search) {
        return productRepository.count(new ProductSearchSpecification(search));
    }

    @Override
    public ResponseEntity<SearchResponseWrapperDto> getSearchedPaginatedProducts(SearchDto searchDto, Integer currentPage, Integer pageSize) {
        if (Optional.ofNullable(currentPage).isEmpty() || Optional.ofNullable(pageSize).isEmpty()) {
            currentPage = 1;
            pageSize = EshopConstants.MIN_PAGE_SIZE;
        }
        Search search = Optional.ofNullable(searchDto).map(searchConverter::fromDto).orElse(null);
        Long count;
        List<Product> productList;
        ResponseEntity<SearchResponseWrapperDto> response = new ResponseEntity<>(new SearchResponseWrapperDto(), HttpStatus.OK);
        if ((search == null) || (search.getSearchString() == null)) {
            count = getCountOfAllProducts();
            Pageable pageable = PageRequest.of((currentPage - 1), pageSize,
                    Sort.by("name"));
            productList = productRepository.findAll(pageable).getContent();
        } else {
            count = getCountAppropriateProducts(search);
            Pageable pageable = PageRequest.of((currentPage - 1), pageSize, Sort.by("name"));
            productList = productRepository.findAll(new ProductSearchSpecification(search), pageable).getContent();
            Objects.requireNonNull(response.getBody()).setSearch(searchConverter.toDto(search));
        }
        Objects.requireNonNull(response.getBody()).setTotalSearchResults(count);
        Objects.requireNonNull(response.getBody()).setPageSize(pageSize);
        Objects.requireNonNull(response.getBody()).setCurrentPage(currentPage);
        Objects.requireNonNull(response.getBody()).setTotalPaginatedVisiblePages(EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        Objects.requireNonNull(response.getBody()).setLastPageNumber((int) Math.ceil(count / pageSize.doubleValue()));
        Objects.requireNonNull(response.getBody()).setProducts(productList.stream().map(productConverter::toDto).toList());
        return response;
    }

    @Override
    public ResponseEntity<List<ProductDto>> getPaginatedProductsByCategoryId(Integer categoryId, Integer currentPage, Integer pageSize) {
        if (Optional.ofNullable(currentPage).isEmpty() || Optional.ofNullable(pageSize).isEmpty()) {
            currentPage = 1;
            pageSize = EshopConstants.MIN_PAGE_SIZE;
        }
        Pageable pageable = PageRequest.of((currentPage - 1), pageSize);
        return new ResponseEntity<>(productRepository.findAllByCategoryIdOrderByName(categoryId, pageable).stream().map(productConverter::toDto).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InputStreamResource> exportCategoryProducts(Integer categoryId) throws CSVExportException {
        return writeCsv(categoryId);
    }

    private ResponseEntity<InputStreamResource> writeCsv(Integer categoryId) throws CSVExportException {
        List<ProductDto> productDtoList = productRepository.findAllByCategoryId(categoryId).stream().map(productConverter::toDto).toList();
        try (Writer productsWriter = Files.newBufferedWriter(Paths.get(EshopConstants.resourcesFilePath + "category_" + categoryId + "_products.csv"))) {
            StatefulBeanToCsv<ProductDto> productsSbc = new StatefulBeanToCsvBuilder<ProductDto>(productsWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            productsSbc.write(productDtoList);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(EshopConstants.resourcesFilePath + "category_" + categoryId + "_products.csv"));
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "category_" + categoryId + "_products.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(resource);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorProductsExportMessage);
        }
    }

    @Override
    public ResponseEntity<List<ProductDto>> importCategoryProducts(MultipartFile file) throws CSVImportException {
        List<ProductDto> productDtoList = parseCsv(file);
        if (Optional.ofNullable(productDtoList).isPresent()) {
            productDtoList.forEach(productDto -> {
                productDto.setId(0); // Чтобы создавался новый заказ, а не обновлялся этот же, если что - удалить
                productDto.setId(productRepository.save(productConverter.fromDto(productDto)).getId());
            });
            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    private List<ProductDto> parseCsv(MultipartFile file) throws CSVImportException {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader productsReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductDto> productsCtb = new CsvToBeanBuilder<ProductDto>(productsReader)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withType(ProductDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                return productsCtb.parse();
            } catch (IOException e) {
                throw new CSVImportException(EshopConstants.errorProductsImportMessage);
            }
        } else {
            throw new CSVImportException(EshopConstants.errorFileNullMessage);
        }
    }

    /*
    @Override
    public ModelAndView applyProductsQuantity(Cart cart, HttpServletRequest request) {
        for (Product product : cart.getProducts()) {
            String quantity = request.getParameter(product.getId() + "quantity");
            if (quantity != null) {
                Integer currentQuantity = cart.getProductQuantities().get(product.getId());
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * (Integer.parseInt(quantity) - currentQuantity));
                cart.getProductQuantities().replace(product.getId(), Integer.parseInt(quantity));
            }
        }
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath());
    }
     */
}
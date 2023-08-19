package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Cart;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Product;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Search;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.SearchResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.ProductConverter;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.SearchConverter;
import by.teachmeskills.sneakersshopwebserviceexam.enums.EshopConstants;
import by.teachmeskills.sneakersshopwebserviceexam.enums.PagesPathEnum;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.ProductRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SearchConverter searchConverter;
    private final ProductConverter productConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, @Lazy SearchConverter searchConverter, @Lazy ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.searchConverter = searchConverter;
        this.productConverter = productConverter;
    }

    // Basic controllers reference
    @Override
    public ProductDto create(ProductDto productDto) throws EntityOperationException {
        return productConverter.toDto(productRepository.create(productConverter.fromDto(productDto)));
    }

    @Override
    public List<ProductDto> read() throws EntityOperationException {
        return productRepository.read().stream().map(productConverter::toDto).toList();
    }

    @Override
    public ProductDto update(ProductDto productDto) throws EntityOperationException {
        return productConverter.toDto(productRepository.update(productConverter.fromDto(productDto)));
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        productRepository.delete(id);
    }

    @Override
    public List<ProductDto> getCategoryProducts(Integer categoryId) throws EntityOperationException {
        return productRepository.getCategoryProducts(categoryId).stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> getOrderProducts(Integer orderId) throws EntityOperationException {
        return productRepository.getOrderProducts(orderId).stream().map(productConverter::toDto).toList();
    }

    @Override
    public ProductDto getProductById(Integer id) throws EntityOperationException {
        return productConverter.toDto(productRepository.getProductById(id));
    }

    // Complex controllers reference
    @Override
    public Long getCountOfAllProducts() throws EntityOperationException {
        return productRepository.getCountOfAllProducts();
    }

    @Override
    public Long getCountAppropriateProducts(Search search) throws EntityOperationException {
        return productRepository.getCountAppropriateProducts(search);
    }

    @Override
    public ResponseEntity<SearchResponseWrapperDto> getPaginatedProducts(SearchDto searchDto, Integer currentPage) throws EntityOperationException {
        Search search = Optional.ofNullable(searchDto).map(searchConverter::fromDto).orElse(null);
        Long count;
        List<Product> productList;
        ResponseEntity<SearchResponseWrapperDto> response = new ResponseEntity<>(new SearchResponseWrapperDto(), HttpStatus.OK);
        if ((search == null) || (search.getSearchString() == null)) {
            count = getCountOfAllProducts();
            productList = productRepository.readOrderedByNameInRange((currentPage - 1) * EshopConstants.PAGE_SIZE, EshopConstants.PAGE_SIZE);
        } else {
            count = getCountAppropriateProducts(search);
            productList = productRepository.getSearchedProducts(search, (currentPage - 1) * EshopConstants.PAGE_SIZE, EshopConstants.PAGE_SIZE);
            Objects.requireNonNull(response.getBody()).setSearch(searchConverter.toDto(search));
        }
        Objects.requireNonNull(response.getBody()).setTotalSearchResults(count);
        Objects.requireNonNull(response.getBody()).setCurrentPage(currentPage);
        Objects.requireNonNull(response.getBody()).setTotalPaginatedVisiblePages(EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        Objects.requireNonNull(response.getBody()).setLastPageNumber((int) Math.ceil(count / EshopConstants.PAGE_SIZE.doubleValue()));
        Objects.requireNonNull(response.getBody()).setProducts(productList.stream().map(productConverter::toDto).toList());
        return response;
    }

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
}
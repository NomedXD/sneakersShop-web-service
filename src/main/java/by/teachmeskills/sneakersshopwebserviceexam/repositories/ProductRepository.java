package by.teachmeskills.sneakersshopwebserviceexam.repositories;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Product;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Search;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(Integer categoryId) throws EntityOperationException;

    List<Product> getOrderProducts(Integer orderId) throws EntityOperationException;

    Product getProductById(Integer id) throws EntityOperationException;

    List<Product> getSearchedProducts(Search search, Integer integer, Integer count) throws EntityOperationException;

    Long getCountOfAllProducts() throws EntityOperationException;
    Long getCountAppropriateProducts(Search search) throws EntityOperationException;

    List<Product> readOrderedByNameInRange(Integer first, Integer count) throws EntityOperationException;
}

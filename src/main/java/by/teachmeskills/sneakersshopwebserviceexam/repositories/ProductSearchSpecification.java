package by.teachmeskills.sneakersshopwebserviceexam.repositories;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Category;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Product;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Search;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductSearchSpecification implements Specification<Product> {
    private final Search search;

    public ProductSearchSpecification(Search search) {
        this.search = search;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Product> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Optional.ofNullable(search.getSearchString()).isPresent() && !search.getSearchString().isBlank()) {
            predicates.add(criteriaBuilder
                    .or(criteriaBuilder.like(root.get("name"), "%" + search.getSearchString() + "%"),
                            criteriaBuilder.like(root.get("description"), "%" + search.getSearchString() + "%")));
        }

        if (Optional.ofNullable(search.getPriceFrom()).isPresent() && search.getPriceFrom() > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), search.getPriceFrom()));
        }

        if (Optional.ofNullable(search.getPriceTo()).isPresent() && search.getPriceTo() > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), search.getPriceTo()));
        }

        if (Optional.ofNullable(search.getCategoryName()).isPresent()
                && !search.getCategoryName().isBlank()) {
            Join<Product, Category> productCategoryJoin = root.join("category");
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(productCategoryJoin.get("name"),
                    "%" + search.getCategoryName() + "%")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

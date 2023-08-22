package by.teachmeskills.sneakersshopwebserviceexam.repositories;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Category;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;

public interface CategoryRepository extends BaseRepository<Category>{
    Category getCategoryById(Integer id) throws EntityOperationException;
}

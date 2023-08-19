package by.teachmeskills.sneakersshopwebserviceexam.utils;

import jakarta.persistence.TypedQuery;

import java.util.List;

public class JPAResultHelper {
    public static <T> Object getSingleResultOrNull(TypedQuery<T> query){
        List<T> results = query.getResultList();
        if (results.size() != 1) {
            return null;
        } else {
            return results.get(0);
        }
    }
}

package by.teachmeskills.sneakersshopwebserviceexam.exception;

import jakarta.persistence.PersistenceException;
import lombok.Getter;
import org.hibernate.HibernateException;

@Getter
public class EntityOperationException extends HibernateException {
    private PersistenceException exception;

    public EntityOperationException(String message) {
        super(message);
    }

    public EntityOperationException(String message, PersistenceException exception) {
        super(message);
        this.exception = exception;
    }
}

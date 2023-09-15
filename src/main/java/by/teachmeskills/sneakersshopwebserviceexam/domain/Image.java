package by.teachmeskills.sneakersshopwebserviceexam.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image extends BaseEntity{

    @NotNull(message = "Field is null validation error")
    @Size(max = 45, message = "Out of validation bounds")
    @Column(name = "path")
    private String path;

    @Nullable
    @Column(name = "isPrime")
    private Boolean isPrime;

    public Image(Integer id, String path) {
        this.id = id;
        this.path = path;
    }
}

package by.teachmeskills.sneakersshopwebserviceexam.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @Column(name = "path")
    private String path;

    public Image(Integer id, String path) {
        this.id = id;
        this.path = path;
    }
}

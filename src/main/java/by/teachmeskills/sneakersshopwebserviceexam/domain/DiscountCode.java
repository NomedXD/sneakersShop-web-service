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
@Table(name = "discount_codes")
public class DiscountCode extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "discount")
    private Float discount;
}

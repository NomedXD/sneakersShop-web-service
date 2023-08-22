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
@Table(name = "time_statistic")
public class Statistic extends BaseEntity{

    @Column(name = "description")
    private String description;

    public Statistic(String description) {
        this.description = description;
    }
}

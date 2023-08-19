package by.teachmeskills.sneakersshopwebserviceexam.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "mail", unique = true)
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "balance")
    private Float currentBalance;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "street")
    private String street;

    @Column(name = "accommodation_number")
    private String accommodationNumber;

    @Column(name = "flat_number")
    private String flatNumber;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orders;
}

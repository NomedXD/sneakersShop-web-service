package by.teachmeskills.sneakersshopwebserviceexam.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "Field is null validation error")
    @Email(message = "Field does not satisfy regexp")
    @Column(name = "mail", unique = true)
    private String mail;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$", message = "Field does not satisfy regexp")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "Field is null validation error")
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

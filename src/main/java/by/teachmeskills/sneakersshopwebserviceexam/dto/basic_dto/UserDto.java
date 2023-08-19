package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "Id field in userDto is null")
    @Min(value = 1, message = "Id field in userDto less then 1")
    private Integer id;

    @NotNull(message = "Mail field in userDto is null")
    @Email(message = "Mail field in productDto does not satisfy regexp")
    private String mail;

    @NotNull(message = "Password field in userDto is null")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$", message = "Password field in productDto does not satisfy regexp")
    private String password;

    @NotNull(message = "Name field in userDto is null")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Name field in productDto does not satisfy regexp")
    private String name;

    @NotNull(message = "Surname field in userDto is null")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Surname field in productDto does not satisfy regexp")
    private String surname;

    @NotNull(message = "Date field in userDto is null")
    private LocalDate date;

    @NotNull(message = "Current balance field in userDto is null")
    @PositiveOrZero(message = "Current balance in userDto must be positive or zero")
    private Float currentBalance;

    private String mobile;

    private String street;

    private String accommodationNumber;

    private String flatNumber;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderDto> orders;
}

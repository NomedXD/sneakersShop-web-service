package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
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
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "Field is null validation error")
    private Integer id;

    @NotNull(message = "Field is null validation error")
    @Email(message = "Field does not satisfy regexp")
    private String mail;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$", message = "Field does not satisfy regexp")
    private String password;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    private String name;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    private String surname;

    @NotNull(message = "Field is null validation error")
    private LocalDate date;

    private Float currentBalance;

    private String mobile;

    private String street;

    private String accommodationNumber;

    private String flatNumber;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderDto> orders;
}

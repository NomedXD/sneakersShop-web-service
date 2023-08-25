package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
public class OrderDto {

    @NotNull(message = "Id field in orderDto is null")
    @Min(value = 1, message = "Id field in orderDto less then 1")
    @CsvBindByPosition(position = 0)
    private Integer id;

    @NotNull(message = "Price field in orderDto is null")
    @PositiveOrZero(message = "Price in orderDto must be positive or zero")
    @CsvBindByPosition(position = 1)
    private Float price;

    @NotNull(message = "Date field in orderDto is null")
    @CsvBindByPosition(position = 2)
    private LocalDate date;

    @NotNull(message = "User id field in orderDto is null")
    @Positive(message = "User id in orderDto must be positive")
    @CsvBindByPosition(position = 3)
    private Integer userId;

    @NotNull(message = "Products list field in orderDto is null")
    @Size(min = 1, max = 100, message = "Out of bounds orderDto product list")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @CsvBindByPosition(position = 4)
    private List<ProductDto> productList;

    @NotNull(message = "Credit card number field in orderDto is null")
    @Size(max = 19, message = "Out of bounds orderDto credit card number")
    @CsvBindByPosition(position = 5)
    private String creditCardNumber;

    @NotNull(message = "Shipping type field in orderDto is null")
    @Size(max = 45, message = "Out of bounds orderDto shipping type")
    @CsvBindByPosition(position = 6)
    private String shippingType;

    @NotNull(message = "Shipping cost field in orderDto is null")
    @PositiveOrZero(message = "Shipping cost in orderDto must be positive or zero")
    @CsvBindByPosition(position = 7)
    private Float shippingCost;

    @NotNull(message = "Code field in orderDto is null")
    @Size(max = 10, message = "Out of bounds orderDto code")
    @CsvBindByPosition(position = 8)
    private String code;

    @NotNull(message = "Address field in orderDto is null")
    @Size(max = 100, message = "Out of bounds orderDto address")
    @CsvBindByPosition(position = 9)
    private String address;

    @NotNull(message = "Customer notes field in orderDto is null")
    @Size(max = 1000, message = "Out of bounds orderDto customer notes")
    @CsvBindByPosition(position = 10)
    private String customerNotes;
}

package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import by.teachmeskills.sneakersshopwebserviceexam.domain.OrderDetails;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
    @CsvBindByName
    private Integer id;

    @NotNull(message = "Price field in orderDto is null")
    @PositiveOrZero(message = "Price in orderDto must be positive or zero")
    @CsvBindByName
    private Float price;

    @NotNull(message = "Date field in orderDto is null")
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName
    private LocalDate date;

    @NotNull(message = "User id field in orderDto is null")
    @Positive(message = "User id in orderDto must be positive")
    @CsvBindByName
    private Integer userId;

    @NotNull(message = "Products list field in orderDto is null")
    @Size(min = 1, max = 100, message = "Out of bounds orderDto product list")
    private List<ProductDto> productList;

    @NotNull(message = "Credit card number field in orderDto is null")
    @Size(max = 19, message = "Out of bounds orderDto credit card number")
    @CsvBindByName
    private String creditCardNumber;

    @NotNull(message = "Shipping type field in orderDto is null")
    @Size(max = 45, message = "Out of bounds orderDto shipping type")
    @CsvBindByName
    private String shippingType;

    @NotNull(message = "Shipping cost field in orderDto is null")
    @PositiveOrZero(message = "Shipping cost in orderDto must be positive or zero")
    @CsvBindByName
    private Float shippingCost;

    @Nullable
    @CsvBindByName
    private DiscountCodeDto discountCode;

    @NotNull(message = "Address field in orderDto is null")
    @Size(max = 100, message = "Out of bounds orderDto address")
    @CsvBindByName
    private String address;

    @NotNull(message = "Customer notes field in orderDto is null")
    @Size(max = 1000, message = "Out of bounds orderDto customer notes")
    @CsvBindByName
    private String customerNotes;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderDetailsDto> orderDetailsDtoList;
}

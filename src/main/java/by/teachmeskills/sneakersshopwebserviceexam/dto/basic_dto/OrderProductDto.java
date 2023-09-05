package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import by.teachmeskills.sneakersshopwebserviceexam.utils.ImageCsvConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
public class OrderProductDto {

    @CsvBindByName
    private Integer productId;

    @CsvBindByName
    private String productName;

    @CsvCustomBindByName(converter = ImageCsvConverter.class)
    private ImageDto productImage;

    @CsvBindByName
    private String productDescription;

    @CsvBindByName
    private Integer categoryId;

    @CsvBindByName
    private Float productPrice;

    @CsvBindByName
    private Integer orderId;

    @CsvBindByName
    private Float totalOrderPrice;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName
    private LocalDate orderDate;

    @CsvBindByName
    private Integer userId;

    @CsvBindByName
    private String creditCardNumber;

    @CsvBindByName
    private String shippingType;

    @CsvBindByName
    private Float shippingCost;

    @CsvBindByName
    private String code;

    @CsvBindByName
    private String address;

    @CsvBindByName
    private String customerNotes;
}

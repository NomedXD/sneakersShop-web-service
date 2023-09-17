package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.DiscountCode;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.DiscountCodeDto;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.DiscountCodeRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.DiscountCodeService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Data
@Component
public class DiscountCodeConverter {
    private final DiscountCodeService discountCodeService;
    private final DiscountCodeRepository discountCodeRepository;

    @Autowired
    public DiscountCodeConverter(DiscountCodeService discountCodeService, @Lazy DiscountCodeRepository discountCodeRepository) {
        this.discountCodeService = discountCodeService;
        this.discountCodeRepository = discountCodeRepository;
    }

    public DiscountCodeDto toDto(DiscountCode discountCode) {
        return DiscountCodeDto.builder().name(discountCode.getName()).
                discount(discountCode.getDiscount()).build();
    }

    public DiscountCode fromDto(DiscountCodeDto discountCodeDto) {
        return discountCodeRepository.findDiscountCodeByName(discountCodeDto.getName()).orElse(null);
    }
}

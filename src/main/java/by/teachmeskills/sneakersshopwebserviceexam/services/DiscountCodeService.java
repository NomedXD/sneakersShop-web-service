package by.teachmeskills.sneakersshopwebserviceexam.services;


import by.teachmeskills.sneakersshopwebserviceexam.domain.DiscountCode;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.DiscountCodeDto;

import java.util.List;
import java.util.Optional;

public interface DiscountCodeService {
    DiscountCodeDto create(DiscountCodeDto entity);
    List<DiscountCodeDto> read();
    DiscountCodeDto update(DiscountCodeDto entity);
    void delete(Integer id);
    DiscountCodeDto getDiscountCodeByName(String name);
    DiscountCodeDto getDiscountCodeById(Integer id);
}

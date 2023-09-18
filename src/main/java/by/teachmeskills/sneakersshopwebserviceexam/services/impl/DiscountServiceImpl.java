package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.DiscountCode;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.DiscountCodeDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.DiscountCodeConverter;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.DiscountCodeRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.DiscountCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountCodeService {
    private final DiscountCodeRepository discountCodeRepository;
    private final DiscountCodeConverter discountCodeConverter;

    @Autowired
    public DiscountServiceImpl(DiscountCodeRepository discountCodeRepository,@Lazy DiscountCodeConverter discountCodeConverter) {
        this.discountCodeRepository = discountCodeRepository;
        this.discountCodeConverter = discountCodeConverter;
    }

    @Override
    public DiscountCodeDto create(DiscountCodeDto entity) {
        return discountCodeConverter.toDto(discountCodeRepository.save(discountCodeConverter.fromDto(entity)));
    }

    @Override
    public List<DiscountCodeDto> read() {
        return discountCodeRepository.findAll().stream().map(discountCodeConverter::toDto).toList();
    }

    @Override
    public DiscountCodeDto update(DiscountCodeDto entity) {
        return discountCodeConverter.toDto(discountCodeRepository.save(discountCodeConverter.fromDto(entity)));
    }

    @Override
    public void delete(Integer id) {
        discountCodeRepository.deleteById(id);
    }

    @Override
    public DiscountCodeDto getDiscountCodeByName(String name) {
        return discountCodeConverter.toDto(discountCodeRepository.findDiscountCodeByName(name).orElse(new DiscountCode()));
    }

    @Override
    public DiscountCodeDto getDiscountCodeById(Integer id) {
        return discountCodeConverter.toDto(discountCodeRepository.findById(id).orElse(new DiscountCode()));
    }
}

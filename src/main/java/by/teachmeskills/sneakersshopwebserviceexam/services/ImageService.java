package by.teachmeskills.sneakersshopwebserviceexam.services;


import by.teachmeskills.sneakersshopwebserviceexam.domain.Image;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ImageDto;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    ImageDto create(ImageDto imageDto);

    List<ImageDto> read();

    ImageDto update(ImageDto imageDto);

    void delete(Integer id);

    ImageDto getImageById(Integer id);
}

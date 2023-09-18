package by.teachmeskills.sneakersshopwebserviceexam.services.impl;


import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ImageDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.ImageConverter;
import by.teachmeskills.sneakersshopwebserviceexam.exception.NoSuchImageException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.ImageRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageConverter imageConverter;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, ImageConverter imageConverter) {
        this.imageRepository = imageRepository;
        this.imageConverter = imageConverter;
    }

    @Override
    public ImageDto create(ImageDto imageDto) {
        return imageConverter.toDto(imageRepository.save(imageConverter.fromDto(imageDto)));
    }

    @Override
    public List<ImageDto> read() {
        return imageRepository.findAll().stream().map(imageConverter::toDto).toList();
    }

    @Override
    public ImageDto update(ImageDto imageDto) {
        return imageConverter.toDto(imageRepository.save(imageConverter.fromDto(imageDto)));
    }

    @Override
    public void delete(Integer id) {
        imageRepository.deleteById(id);
    }

    @Override
    public ImageDto getImageById(Integer id) {
        return imageConverter.toDto(imageRepository.findById(id).orElseThrow(() -> new NoSuchImageException("Image not found. Id:", id)));
    }
}

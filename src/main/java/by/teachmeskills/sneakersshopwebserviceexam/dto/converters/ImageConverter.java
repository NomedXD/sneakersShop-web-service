package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Image;
import by.teachmeskills.sneakersshopwebserviceexam.dto.ImageDto;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ImageConverter {
    public ImageDto toDto(Image image) {
        return  ImageDto.builder().id(image.getId()).path(image.getPath()).build();
    }

    public Image fromDto(ImageDto imageDto){
        return Image.builder().id(imageDto.getId()).path(imageDto.getPath()).build();
    }
}

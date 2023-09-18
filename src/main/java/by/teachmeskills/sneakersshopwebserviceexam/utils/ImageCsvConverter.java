package by.teachmeskills.sneakersshopwebserviceexam.utils;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ImageDto;
import com.opencsv.bean.AbstractBeanField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageCsvConverter extends AbstractBeanField<ImageDto, String> {
    @Override
    protected ImageDto convert(String s) {
        Pattern pattern = Pattern.compile("id=(.*?),");
        Matcher matcherId = pattern.matcher(s);
        pattern = Pattern.compile("path=(.*?)\\Q)\\E");
        Matcher matcherPath = pattern.matcher(s);
        if (matcherId.find() && matcherPath.find())
        {
            return new ImageDto();
        }
       return null;
    }
}

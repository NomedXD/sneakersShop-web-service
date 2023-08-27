package by.teachmeskills.sneakersshopwebserviceexam.utils;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCsvConverter extends AbstractBeanField<LocalDate, String> {

    @Override
    protected LocalDate convert(String s) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(s, pattern);
    }
}

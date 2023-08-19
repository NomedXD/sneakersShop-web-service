package by.teachmeskills.sneakersshopwebserviceexam.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {

    }

    public static Date parseDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = format.parse(date);
            return parsedDate;
        } catch (ParseException e) {
            logger.error("Error while parse date. Most likely format is wrong");
            return parsedDate;
        }
    }
}

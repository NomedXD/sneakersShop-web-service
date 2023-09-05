package by.teachmeskills.sneakersshopwebserviceexam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@Getter
@AllArgsConstructor
public class CSVExportException extends IOException {
    public CSVExportException(String message) {
        super(message);
    }
}

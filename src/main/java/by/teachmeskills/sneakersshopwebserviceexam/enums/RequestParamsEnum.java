package by.teachmeskills.sneakersshopwebserviceexam.enums;

import lombok.Getter;

@Getter
public enum RequestParamsEnum {
    MOBILE("mobile"),
    STREET("street"),
    ACCOMMODATION_NUMBER("accommodationNumber"),
    FLAT_NUMBER("flatNumber");
    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

}


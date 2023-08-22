package by.teachmeskills.sneakersshopwebserviceexam.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderShippingEnum {
    SELF_PICK_UP("Self pick up"),
    DELIVERY_BY_COURIER("Delivery by courier");

    private final String shipping;
}

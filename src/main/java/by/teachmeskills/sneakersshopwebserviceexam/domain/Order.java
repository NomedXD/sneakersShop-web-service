package by.teachmeskills.sneakersshopwebserviceexam.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Column(name = "price")
    private Float price;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_products", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> productList;

    @Column(name = "cc_number")
    private String creditCardNumber;

    @Column(name = "shipping_type")
    private String shippingType;

    @Column(name = "shipping_cost")
    private Float shippingCost;

    @Column(name = "code")
    private String code;

    @Column(name = "address")
    private String address;

    @Column(name = "customer_notes")
    private String customerNotes;
}

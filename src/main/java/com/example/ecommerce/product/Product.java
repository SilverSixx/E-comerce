package com.example.ecommerce.product;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {
    @SequenceGenerator(
            name="pc_sequence",
            sequenceName = "pc_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator ="pc_sequence"
    )
    private Long id;
    private String name;
    private String manufacturer;
    private String description;
    @Lob
    private byte[] image;
    private Double price;
    private Integer inStock;

    public Product(String name, String manufacturer, String description, byte[] image, Double price, Integer inStock) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.description = description;
        this.image = image;
        this.price = price;
        this.inStock = inStock;
    }
}

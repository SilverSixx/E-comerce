package com.example.ecommerce.product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PCRequest {
    private String name;
    private String manufacturer;
    private String model;
    private byte[] image;
    private Double price;
    private Integer inStock;

}

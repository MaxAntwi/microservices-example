package org.example.productservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class ProductRequest {
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
}

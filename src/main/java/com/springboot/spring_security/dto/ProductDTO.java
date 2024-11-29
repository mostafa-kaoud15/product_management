package com.springboot.spring_security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;

    
}

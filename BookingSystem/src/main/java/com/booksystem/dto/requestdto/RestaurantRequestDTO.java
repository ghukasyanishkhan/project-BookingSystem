package com.booksystem.dto.requestdto;

import lombok.Data;

@Data
public class RestaurantRequestDTO {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private Integer tables;
}

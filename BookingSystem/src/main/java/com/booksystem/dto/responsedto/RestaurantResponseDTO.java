package com.booksystem.dto.responsedto;

import com.booksystem.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonPropertyOrder({

})
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    @JsonIgnore
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("tables")
    private Integer tables;
    @JsonProperty("reservedTables")
    private Integer reservedTables;
    @JsonProperty("userEntity")
    private UserResponseDTO userResponseDTO;
}

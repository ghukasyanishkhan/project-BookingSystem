package com.booksystem.dto.responsedto;

import com.booksystem.enums.Role;
import com.booksystem.enums.Status;
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
public class UserResponseDTO {
    @JsonIgnore
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surName")

    private String surName;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("verifyCode")

    private String verifyCode;
    @JsonProperty("status")

    private Status status;
    @JsonProperty("role")

    private Role role;
    @JsonProperty("resetToken")
    private String resetToken;
}

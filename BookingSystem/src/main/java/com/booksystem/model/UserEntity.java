package com.booksystem.model;

import com.booksystem.dto.requestdto.UserRequestDTO;
import com.booksystem.enums.Role;
import com.booksystem.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    private Integer id;
    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String surName;
    private Integer year;
    private String email;
    private String password;
    @Column(name = "verification_code")
    private String verifyCode;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "reset_token")
    private String resetToken;
}

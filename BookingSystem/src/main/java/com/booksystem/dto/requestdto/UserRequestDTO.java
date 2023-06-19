package com.booksystem.dto.requestdto;

import com.booksystem.enums.Role;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UserRequestDTO {
    private Integer id;
    private String requestName;
    private String requestSurName;
    private Integer requestYear;
    private String requestEmail;
    private String requestPassword;
    @Enumerated(EnumType.STRING)
    private Role requestRole;

}

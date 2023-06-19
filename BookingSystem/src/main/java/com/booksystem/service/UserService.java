package com.booksystem.service;

import com.booksystem.dto.requestdto.UserRequestDTO;
import com.booksystem.exceptions.ApiException;
import com.booksystem.model.RestaurantEntity;
import com.booksystem.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity save(UserRequestDTO dto) throws ApiException;
    UserEntity saveAdmin(UserRequestDTO dto) throws ApiException;
    List<UserEntity> getByUserName(String email);
    UserEntity verifyUser(String email, String code);
    UserEntity changePassword(String oldPassword, String newPassword,String ConfirmPassword, String email) throws ApiException;
    void deleteUser(Integer id) throws ApiException;
    UserEntity resetToken(String email) throws ApiException;
    Boolean verifyResetToken(String email,String resetToken) throws ApiException;
    void forgotPassword(String email, String newPassword, String confirmPassword) throws ApiException;
    UserEntity update(UserRequestDTO dto) throws ApiException;

}

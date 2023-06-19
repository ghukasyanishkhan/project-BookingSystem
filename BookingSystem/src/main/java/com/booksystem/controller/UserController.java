package com.booksystem.controller;

import com.booksystem.dto.requestdto.UserRequestDTO;
import com.booksystem.exceptions.ApiException;
import com.booksystem.model.RestaurantEntity;
import com.booksystem.model.UserEntity;
import com.booksystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/save-user")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody UserEntity saveUser(@RequestBody UserRequestDTO requestDTO) throws ApiException {
        return userService.save(requestDTO);
    }

    @PostMapping("/save-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody UserEntity saveAdmin(@RequestBody UserRequestDTO requestDTO) throws ApiException {
        return userService.saveAdmin(requestDTO);
    }

    @GetMapping("/get-by-username")
    public List<UserEntity> getByUserName(@RequestParam String email) {
        return userService.getByUserName(email);
    }

    @PatchMapping("/verify")
    public UserEntity verifyUser(@RequestParam String email, @RequestParam String verifyCode) {
        return userService.verifyUser(email, verifyCode);
    }

    @PatchMapping("/change-password")
    public UserEntity changePassword(@RequestParam String oldPassword,
                                     @RequestParam String newPassword,
                                     @RequestParam String confirmPassword,
                                     Principal principal) throws ApiException {
        String email = principal.getName();
        return userService.changePassword(oldPassword, newPassword, confirmPassword, email);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws ApiException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/reset-token")
    public UserEntity resetToken(@RequestParam String email) throws ApiException {
        return userService.resetToken(email);
    }
    @GetMapping("/verify-reset-token")
    public Boolean verifyResetToken(@RequestParam String email, @RequestParam String resetToken) throws ApiException {
       return userService.verifyResetToken(email,resetToken);
    }
    @PatchMapping("/forgot-password")
    public void forgotPassword(@RequestParam String email,@RequestParam String newPassword,@RequestParam String confirmPassword) throws ApiException {
         userService.forgotPassword(email,newPassword,confirmPassword);
    }
    @PutMapping("/update")
    public UserEntity update(@RequestBody UserRequestDTO dto) throws ApiException {
       return userService.update(dto);
    }

}
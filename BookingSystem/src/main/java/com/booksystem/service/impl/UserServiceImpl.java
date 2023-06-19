package com.booksystem.service.impl;

import com.booksystem.dto.requestdto.UserRequestDTO;
import com.booksystem.enums.Role;
import com.booksystem.enums.Status;
import com.booksystem.exceptions.ApiException;
import com.booksystem.exceptions.ErrorMessages;
import com.booksystem.exceptions.userexceptions.UserAlreadyExistException;
import com.booksystem.exceptions.userexceptions.UserNotFoundException;
import com.booksystem.exceptions.userexceptions.UserValidationException;
import com.booksystem.model.UserEntity;
import com.booksystem.repository.UserRepository;
import com.booksystem.service.UserService;
import com.booksystem.util.BookingMailSender;
import com.booksystem.util.TokenGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    BookingMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserEntity save(UserRequestDTO dto) throws ApiException {
        validateDuplicate(dto);
        validateFields(dto);
        validatePassword(dto.getRequestPassword());

        String verifyCode = TokenGenerate.generateVerifyCode();
        UserEntity userEntity = new UserEntity();

        userEntity.setId(0);
        userEntity.setName(dto.getRequestName());
        userEntity.setSurName(dto.getRequestSurName());
        userEntity.setYear(dto.getRequestYear());
        userEntity.setEmail(dto.getRequestEmail());
        userEntity.setPassword(passwordEncoder.encode(dto.getRequestPassword()));
        userEntity.setVerifyCode(verifyCode);
        userEntity.setStatus(Status.INACTIVE);
        userEntity.setRole(dto.getRequestRole());

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new ApiException("problem during saving user");
        }

        mailSender.sendEmail(dto.getRequestEmail(),
                "Your verify code", "Your verify code " + verifyCode);
        return userEntity;
    }

    @Override
    public UserEntity saveAdmin(UserRequestDTO dto) throws ApiException {
        validateFields(dto);
        validatePassword(dto.getRequestPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(0);
        userEntity.setName(dto.getRequestName());
        userEntity.setSurName(dto.getRequestSurName());
        userEntity.setYear(dto.getRequestYear());
        userEntity.setEmail(dto.getRequestEmail());
        userEntity.setPassword(dto.getRequestPassword());
        userEntity.setStatus(Status.ACTIVE);
        userEntity.setRole(Role.ADMIN);

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new ApiException("problem during saving user");
        }

        return userEntity;
    }

    @Override
    public List<UserEntity> getByUserName(String email) {
        List<UserEntity> userEntityList = userRepository.getByEmail(email);

        if (userEntityList.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_EMAIL);
        }

        return userEntityList;
    }

    @Override
    public UserEntity verifyUser(String email, String code) {
        UserEntity userEntity = null;

        try {
            List<UserEntity> userEntityList = userRepository.getByEmail(email);

            userEntity = userEntityList.get(0);
            String verifyCodeDB = userEntity.getVerifyCode();

            if (!verifyCodeDB.equals(code)) {
                throw new ApiException("wrong verify code");
            }

            userEntity.setStatus(Status.ACTIVE);
            userEntity.setVerifyCode(null);
            userRepository.save(userEntity);

        } catch (Exception e) {
            throw new UserNotFoundException("wrong email");
        }

        return userEntity;
    }

    @Override
    public UserEntity changePassword(String oldPassword, String newPassword, String confirmPassword, String email) throws ApiException {
        UserEntity userEntity = null;
        validatePassword(newPassword);
        if (!newPassword.equals(confirmPassword)) {
            throw new UserValidationException("Confirm and new passwords dont match");
        }

        try {
            List<UserEntity> userEntityList = userRepository.getByEmail(email);
            userEntity = userEntityList.get(0);
        } catch (Exception e) {
            throw new ApiException("Problem during getting user");
        }

        if (!passwordEncoder.encode(oldPassword).equals(userEntity.getPassword())) {
            throw new UserValidationException("Wrong old password");
        }

        userEntity.setPassword(passwordEncoder.encode(newPassword));

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new ApiException("Problem during updating user password");
        }

        return userEntity;
    }

    @Override
    public void deleteUser(Integer id) throws ApiException {
        Optional<UserEntity> userEntity = null;
        try {
            userEntity = userRepository.findById(id);
            if (userEntity.isEmpty()) {
                throw new UserNotFoundException("User not found with the given id");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiException("Problem during deleting user");
        }
    }

    @Override
    public UserEntity resetToken(String email) throws ApiException {
        UserEntity userEntity = null;
        try {
            List<UserEntity> userEntityList = userRepository.getByEmail(email);
            if (userEntityList.isEmpty()) {
                throw new UserNotFoundException("Wrong email " + email);
            }
            userEntity = userEntityList.get(0);
            String resetToken = TokenGenerate.generateResetToken();
            userEntity.setResetToken(resetToken);
            userRepository.save(userEntity);
            mailSender.sendEmail(email, "Your password change token", resetToken);
        } catch (Exception e) {
            throw new ApiException("Problem during sending email");
        }
        return userEntity;
    }

    @Override
    public Boolean verifyResetToken(String email, String resetToken) throws ApiException {
        try {
            UserEntity userEntity = userRepository.getByEmailAndResetToken(email, resetToken);
            if (userEntity == null) {
                throw new UserValidationException("wrong reset token " + resetToken);
            }
        } catch (Exception e) {
            throw new ApiException("problem during verifying reset token");
        }
        return true;
    }

    @Override
    public void forgotPassword(String email, String newPassword, String confirmPassword) throws ApiException {

        if (!newPassword.equals(confirmPassword)) {
            throw new UserValidationException("passwords dont match");
        }
        validatePassword(newPassword);
        try {
            userRepository.update(email, passwordEncoder.encode(newPassword));
        } catch (Exception e) {
            throw new ApiException("Problem during updating password");
        }
    }

    @Override
    public UserEntity update(UserRequestDTO dto) throws ApiException {
        validateDuplicate(dto);
        Optional<UserEntity> optionalUser = userRepository.findById(dto.getId());
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found with given id");
        }
        UserEntity userEntity = optionalUser.get();
        userEntity.setName(dto.getRequestName()==null?userEntity.getName(): dto.getRequestName());
        userEntity.setSurName(dto.getRequestSurName()==null?userEntity.getSurName(): dto.getRequestSurName());
        userEntity.setYear(dto.getRequestYear()==null?userEntity.getYear(): dto.getRequestYear());
        userEntity.setEmail(dto.getRequestEmail());
        try {
            userRepository.save(userEntity);
        }catch (Exception e){
            throw new ApiException("problem during updating user");
        }
        return userEntity;
    }


    private void validateFields(UserRequestDTO dto) {
        if (dto.getRequestName() == null || dto.getRequestName().isBlank()) {
            throw new UserValidationException(("user name can't be empty"));
        }
        if (dto.getRequestSurName() == null || dto.getRequestSurName().isBlank()) {
            throw new UserValidationException(("user surname can't be empty"));
        }
        if (dto.getRequestYear() < 1910 || dto.getRequestYear() > 2020) {
            throw new UserValidationException("Birth year should be between 1910 - 2020");
        }
    }

    private void validatePassword(String requestPassword) {
        int countOfDigit = 0;
        int countOfUppercase = 0;

        if (requestPassword.length() < 6) {
            throw new UserValidationException("Password length should be more than 5");
        }

        for (int i = 0; i < requestPassword.length(); i++) {
            char c = requestPassword.charAt(i);
            if (Character.isDigit(c)) {
                countOfDigit++;
            } else if (Character.isUpperCase(c)) {
                countOfUppercase++;
            }
        }

        if (countOfDigit < 2) {
            throw new UserValidationException("password should contain min 2 digits");
        }
        if (countOfUppercase < 1) {
            throw new UserValidationException("password should contain min 1 uppercase");
        }
    }
    private void validateDuplicate(UserRequestDTO dto){
        Optional<UserEntity> userEntity=null;
       //post
        if (dto.getId()==null){
             userEntity = userRepository.findByEmail(dto.getRequestEmail());
            if (userEntity.isPresent()){
                throw new UserAlreadyExistException("User already exists");
            }
        }
        //put
        else {
             userEntity = userRepository.findByEmailAndIdNot(dto.getRequestEmail(), dto.getId());
            if (userEntity.isPresent()){
                throw new UserAlreadyExistException("User already exists");
            }
        }
    }
}

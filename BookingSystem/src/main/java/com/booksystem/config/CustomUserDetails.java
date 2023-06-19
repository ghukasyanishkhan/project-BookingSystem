package com.booksystem.config;

import com.booksystem.exceptions.userexceptions.UserNotFoundException;
import com.booksystem.model.UserEntity;
import com.booksystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Configuration
public class CustomUserDetails implements UserDetailsService {
@Autowired
private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity=null;
        try {
            List<UserEntity> userEntityList = userService.getByUserName(s);
            userEntity=userEntityList.get(0);
        }
        catch (Exception e){
            throw  new UserNotFoundException("wrong email "+s);
        }
        Collection<GrantedAuthority>grantedAuthorities=new ArrayList<>();

        return new User(userEntity.getEmail(),userEntity.getPassword(),grantedAuthorities);
    }
}

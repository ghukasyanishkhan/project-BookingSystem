package com.booksystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetails customUserDetails;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetails)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.POST,"/user/save-user","user/save-admin")
                .antMatchers(HttpMethod.PATCH,"/user/verify","/forgot-password","/user/reset-token")
                .antMatchers(HttpMethod.GET,"/user/verify-reset-token","/restaurant/get-all");
    }

}

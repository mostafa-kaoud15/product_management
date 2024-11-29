package com.springboot.spring_security.exceptionhandler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@Order(1)
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String badCredentialExceptionHandler(BadCredentialsException ex) {
        System.out.println("bad credential handler");
        return "the user name or password is not correct : " + ex;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String userNotFoundExceptionHandler(UsernameNotFoundException ex) {
        return "the user is not found : " + ex;
    }
    
}

package com.eltonmessias.Restaurant_Reservation_API.controller;

import com.eltonmessias.Restaurant_Reservation_API.dto.LoginDTO;
import com.eltonmessias.Restaurant_Reservation_API.dto.UserDTO;
import com.eltonmessias.Restaurant_Reservation_API.model.User;
import com.eltonmessias.Restaurant_Reservation_API.repository.UserRepository;
import com.eltonmessias.Restaurant_Reservation_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpMessageConverters messageConverters;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private View error;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO, BindingResult bindingResult) {
        try {
            UserDTO authenticatedUser = userService.loginUser(loginDTO);
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }



    }
   
}

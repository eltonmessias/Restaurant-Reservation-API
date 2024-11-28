package com.eltonmessias.Restaurant_Reservation_API.service;

import com.eltonmessias.Restaurant_Reservation_API.dto.UserDTO;
import com.eltonmessias.Restaurant_Reservation_API.model.User;
import com.eltonmessias.Restaurant_Reservation_API.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserService {

    private static UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
    private static User convertToEntity(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    @Autowired
    private UserRepository userRepository;
    public UserDTO registerUser( UserDTO userDTO) {
            if (userRepository.existsByEmail(userDTO.email())) {
                throw new IllegalArgumentException("Email already exists");
            }
            User newUser = new User();
            newUser.setName(userDTO.name());
            newUser.setEmail(userDTO.email());
            newUser.setPassword(userDTO.password());
            newUser.setRole(userDTO.role());
            userRepository.save(newUser);
            return convertToDTO(newUser);

    }
}

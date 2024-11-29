package com.eltonmessias.Restaurant_Reservation_API.service;

import com.eltonmessias.Restaurant_Reservation_API.dto.LoginDTO;
import com.eltonmessias.Restaurant_Reservation_API.dto.UserDTO;
import com.eltonmessias.Restaurant_Reservation_API.model.User;
import com.eltonmessias.Restaurant_Reservation_API.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser( UserDTO userDTO) {
            if (userRepository.existsByEmail(userDTO.email())) {
                throw new IllegalArgumentException("Email already exists");
            }
            User newUser = new User();
            newUser.setName(userDTO.name());
            newUser.setEmail(userDTO.email());
            newUser.setPassword(encoder.encode(userDTO.password()));
            newUser.setRole(userDTO.role());
            userRepository.save(newUser);
            return convertToDTO(newUser);

    }

    public UserDTO loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.email());
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if(!(encoder.matches(loginDTO.password(), user.getPassword()))) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

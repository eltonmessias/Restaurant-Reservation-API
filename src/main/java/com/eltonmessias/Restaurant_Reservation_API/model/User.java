package com.eltonmessias.Restaurant_Reservation_API.model;

import com.eltonmessias.Restaurant_Reservation_API.enums.ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "The name is necessary")
    @NotNull(message = "Name missing")
    private String name;
    @NotNull
    @Email(message = "Enter a valid email")
    private String email;
    private String password;
    private ROLE role;


}

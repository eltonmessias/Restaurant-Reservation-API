package com.eltonmessias.Restaurant_Reservation_API.model;

import com.eltonmessias.Restaurant_Reservation_API.enums.ROLE;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private ROLE role;

}

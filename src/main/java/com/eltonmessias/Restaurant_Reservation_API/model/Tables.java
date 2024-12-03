package com.eltonmessias.Restaurant_Reservation_API.model;

import com.eltonmessias.Restaurant_Reservation_API.enums.TABLE_STATUS;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int capacity;
    private TABLE_STATUS status;
}

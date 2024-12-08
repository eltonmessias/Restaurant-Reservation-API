package com.eltonmessias.Restaurant_Reservation_API.model;

import com.eltonmessias.Restaurant_Reservation_API.enums.RESERVATION_STATUS;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Tables table;
    private Date reservation_date;
    private RESERVATION_STATUS status;
}

package com.eltonmessias.Restaurant_Reservation_API.model;

import com.eltonmessias.Restaurant_Reservation_API.enums.RESERVATION_STATUS;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
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
    @Enumerated(EnumType.STRING)
    private RESERVATION_STATUS status;
    @Column(nullable = false)
    private int numberOfPeople;
    @Column(nullable = false)
    private LocalDateTime checkinDate;
    @Column(nullable = false)
    private LocalDateTime checkoutDate;




}

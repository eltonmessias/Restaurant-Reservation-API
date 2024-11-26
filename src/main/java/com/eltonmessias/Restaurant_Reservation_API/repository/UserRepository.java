package com.eltonmessias.Restaurant_Reservation_API.repository;

import com.eltonmessias.Restaurant_Reservation_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
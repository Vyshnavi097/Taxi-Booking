package com.edstem.repository;

import com.edstem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BookingRepository extends JpaRepository<Booking,Long> {
}

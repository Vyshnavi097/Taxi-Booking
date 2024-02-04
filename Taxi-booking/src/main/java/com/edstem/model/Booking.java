package com.edstem.model;

import com.edstem.constant.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private Users user;

    @ManyToOne
    @JoinColumn(name="taxiId")
    private Taxi taxi;

     private String pickupLocation;

    private String dropoffLocation;

     private double fare;

     private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private Status status;

}

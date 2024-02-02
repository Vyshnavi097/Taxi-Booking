package com.edstem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String driverName;
    private String licenseNumber;
    private String currentLocation;

}

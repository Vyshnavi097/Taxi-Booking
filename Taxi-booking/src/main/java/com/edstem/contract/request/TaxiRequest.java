package com.edstem.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TaxiRequest {
    private String driverName;
    private String licenseNumber;
    private String currentLocation;
}

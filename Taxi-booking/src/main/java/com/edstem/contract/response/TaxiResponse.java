package com.edstem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiResponse {
    private long id;
    private String driverName;
    private String licenseNumber;
    private String currentLocation;
}

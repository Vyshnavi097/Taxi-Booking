package com.edstem.contract.request;

import com.edstem.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private String pickupLocation;
    private String dropoffLocation;
    private Status status;
}

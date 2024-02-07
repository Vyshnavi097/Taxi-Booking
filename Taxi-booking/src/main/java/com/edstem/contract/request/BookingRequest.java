package com.edstem.contract.request;

import com.edstem.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BookingRequest {
    private String pickupLocation;
    private String dropoffLocation;
    private Status status;
}

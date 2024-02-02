package com.edstem.contract.response;

import com.edstem.constant.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelBookingResponse {
    @Enumerated(EnumType.STRING)
    private Status status;
}

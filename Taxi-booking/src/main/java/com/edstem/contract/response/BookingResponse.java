package com.edstem.contract.response;

import com.edstem.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BookingResponse {
    private long id;
    private double fare;
    private Status status;

}

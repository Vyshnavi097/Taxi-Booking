package com.edstem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Getter
@Builder
public class LoginResponse {
    private String token;
}

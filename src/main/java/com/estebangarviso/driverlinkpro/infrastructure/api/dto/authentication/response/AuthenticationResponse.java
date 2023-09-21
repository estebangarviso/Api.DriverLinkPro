package com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponse {

    @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;
    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String refreshToken;
}

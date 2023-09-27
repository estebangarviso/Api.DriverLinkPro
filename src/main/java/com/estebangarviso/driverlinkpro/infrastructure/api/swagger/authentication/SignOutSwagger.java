package com.estebangarviso.driverlinkpro.infrastructure.api.swagger.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Tag(name = "Authentication", description = "Authentication API")
public interface SignOutSwagger extends LogoutHandler {
    @Operation(summary = "Sign out", description = "It invalidates the access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    );
}

package com.estebangarviso.driverlinkpro.infrastructure.api.swagger.authentication;

import java.io.IOException;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Authentication", description = "Authentication API")
public interface AuthenticationSwagger {
        @Operation(summary = "Sign in", description = "Get the tokens to access the application")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        AuthenticationResponse signIn(SignInRequestBodyDto signInRequestBodyDto);

        @Operation(summary = "Sign up", description = "Add a new user to the application, send an email to confirm the account and get the tokens to access the application")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        AuthenticationResponse signUp(SignUpRequestBodyDto signUpRequestBodyDto);

        @Operation(summary = "Refresh token", description = "It refreshes the access token with the refresh token")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

        @Operation(summary = "Confirm email", description = "It enables the user's account")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        void confirmEmail(String securityToken);
}

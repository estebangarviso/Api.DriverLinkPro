package com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.request.DriverRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class DriverResponseDto extends DriverRequestDto {
    @Schema(description = "Driver's id", example = "1")
    private Long id;

    @Schema(description = "Driver's code", example = "a57a9c2ec5456cf09d9c2a910a3a537262a3af62b067d835ecfcb3d6f72a6037")
    private String code;

    @Schema(description = "Driver's creation date", example = "2021-08-01T00:00:00.000Z")
    private LocalDateTime createdAt;
}

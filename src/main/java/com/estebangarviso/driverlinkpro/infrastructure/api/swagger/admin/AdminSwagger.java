package com.estebangarviso.driverlinkpro.infrastructure.api.swagger.admin;

import com.estebangarviso.driverlinkpro.domain.exception.general.BadRequestException;
import com.estebangarviso.driverlinkpro.domain.exception.general.ForbiddenException;
import com.estebangarviso.driverlinkpro.domain.model.user.UserModel;
import com.estebangarviso.driverlinkpro.domain.model.user.UserSortFields;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Tag(name = "Admin", description = "Admin API")
public interface AdminSwagger {
    @Operation(summary = "Get all users", description = "Get all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = BadRequestException.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ForbiddenException.class)))
    })
    Page<UserModel> getAllUsers(Integer page, Integer size, UserSortFields sortField, Sort.Direction sortDirection);
}

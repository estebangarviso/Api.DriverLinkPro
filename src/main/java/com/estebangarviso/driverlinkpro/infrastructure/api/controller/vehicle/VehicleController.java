package com.estebangarviso.driverlinkpro.infrastructure.api.controller.vehicle;

import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.vehicle.VehicleSwagger;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.estebangarviso.driverlinkpro.application.usecase.vehicle.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.response.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.mapper.vehicle.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/vehicle")
@Validated
public class VehicleController implements VehicleSwagger {
    private final CreateVehicleUseCase createVehicleUseCase;
    private final GetVehicleUseCase getVehicleUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;
    private final VehicleApiMapper vehicleApiMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Vehicle created")
    public VehicleResponse createVehicle(@RequestBody VehicleRequestDto vehicleRequestDto) {
        var vehicleModel = vehicleApiMapper.toDomain(vehicleRequestDto);
        var createdVehicleModel = createVehicleUseCase.createVehicle(vehicleModel);
        return vehicleApiMapper.toResponse(createdVehicleModel);
    }

    @GetMapping("/{vehicleId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "Vehicle found")
    public VehicleResponse getVehicle(@PathVariable Long vehicleId) {
        var vehicleModel = getVehicleUseCase.getVehicle(vehicleId);
        return vehicleApiMapper.toResponse(vehicleModel);
    }

    @PutMapping("/{vehicleId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "Vehicle updated")
    public VehicleResponse updateVehicle(@PathVariable Long vehicleId, @RequestBody VehicleRequestDto vehicleRequestDto) {
        var vehicleModel = vehicleApiMapper.toDomain(vehicleRequestDto);
        var updatedVehicleModel = updateVehicleUseCase.updateVehicle(vehicleId, vehicleModel);
        return vehicleApiMapper.toResponse(updatedVehicleModel);
    }

    @DeleteMapping("/{vehicleId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Vehicle deleted")
    public void deleteVehicle(@PathVariable Long vehicleId) {
        deleteVehicleUseCase.deleteVehicle(vehicleId);
    }
}

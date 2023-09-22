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
    public VehicleResponse createVehicle(@RequestBody VehicleRequestBodyDto vehicleRequestBodyDto) {
        var vehicleModel = vehicleApiMapper.toDomain(vehicleRequestBodyDto);
        var createdVehicleModel = createVehicleUseCase.createVehicle(vehicleModel);
        return vehicleApiMapper.toResponse(createdVehicleModel);
    }

    @GetMapping("/{idVehicle}")
    @ResponseStatus(code = HttpStatus.OK, reason = "Vehicle found")
    public VehicleResponse getVehicle(@PathVariable Long idVehicle) {
        var vehicleModel = getVehicleUseCase.getVehicle(idVehicle);
        return vehicleApiMapper.toResponse(vehicleModel);
    }

    @PutMapping("/{idVehicle}")
    @ResponseStatus(code = HttpStatus.OK, reason = "Vehicle updated")
    public VehicleResponse updateVehicle(@PathVariable Long idVehicle, @RequestBody VehicleRequestBodyDto vehicleRequestBodyDto) {
        var vehicleModel = vehicleApiMapper.toDomain(vehicleRequestBodyDto);
        var updatedVehicleModel = updateVehicleUseCase.updateVehicle(idVehicle, vehicleModel);
        return vehicleApiMapper.toResponse(updatedVehicleModel);
    }

    @DeleteMapping("/{idVehicle}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Vehicle deleted")
    public void deleteVehicle(@PathVariable Long idVehicle) {
        deleteVehicleUseCase.deleteVehicle(idVehicle);
    }
}

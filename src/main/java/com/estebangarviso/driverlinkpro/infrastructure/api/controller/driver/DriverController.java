package com.estebangarviso.driverlinkpro.infrastructure.api.controller.driver;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverCheckResponse;
import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.driver.DriverSwagger;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.estebangarviso.driverlinkpro.application.usecase.driver.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.request.DriverRequestBodyDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverResponse;
import com.estebangarviso.driverlinkpro.infrastructure.api.mapper.driver.DriverApiMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/driver")
@Validated
public class DriverController implements DriverSwagger {
    private final CreateDriverUseCase createDriverUseCase;
    private final GetDriverUseCase getDriverUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final DeleteDriverUseCase deleteDriverUseCase;
    private final CheckAllDriverParcelsUseCase checkAllDriverParcelsUseCase;
    private final DriverApiMapper driverApiMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Driver created")
    public DriverResponse createDriver(@RequestBody DriverRequestBodyDto driverRequestBodyDto) {
        var driverModel = driverApiMapper.toDomain(driverRequestBodyDto);
        var createdDriverModel = createDriverUseCase.createDriver(driverModel);
        return driverApiMapper.toResponse(createdDriverModel);
    }

    @GetMapping("/{driverId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "Driver found")
    public DriverResponse getDriver(@PathVariable Long driverId) {
        var driverModel = getDriverUseCase.getDriver(driverId);
        return driverApiMapper.toResponse(driverModel);
    }

    @PutMapping("/{driverId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "Driver updated")
    public DriverResponse updateDriver(@PathVariable Long driverId, @RequestBody DriverRequestBodyDto driverRequestBodyDto) {
        var driverModel = driverApiMapper.toDomain(driverRequestBodyDto);
        var updatedDriverModel = updateDriverUseCase.updateDriver(driverId, driverModel);
        return driverApiMapper.toResponse(updatedDriverModel);
    }

    @DeleteMapping("/{driverId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Driver deleted")
    public void deleteDriver(@PathVariable Long driverId) {
        deleteDriverUseCase.deleteDriver(driverId);
    }

    @GetMapping("/check/{driverId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "All driver parcels checked")
    public DriverCheckResponse checkAllDriverParcels(@PathVariable Long driverId) {
        return checkAllDriverParcelsUseCase.checkAllDriverParcels(driverId);
    }

}

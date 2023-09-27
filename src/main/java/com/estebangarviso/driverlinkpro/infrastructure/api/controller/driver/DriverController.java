package com.estebangarviso.driverlinkpro.infrastructure.api.controller.driver;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverCheckResponse;
import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.driver.DriverSwagger;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.estebangarviso.driverlinkpro.application.usecase.driver.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.request.DriverRequestBodyDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverResponse;
import com.estebangarviso.driverlinkpro.infrastructure.api.mapper.driver.DriverApiMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/driver")
public class DriverController implements DriverSwagger {
    private final CreateDriverUseCase createDriverUseCase;
    private final GetDriverUseCase getDriverUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final DeleteDriverUseCase deleteDriverUseCase;
    private final CheckAllDriverParcelsUseCase checkAllDriverParcelsUseCase;
    private final DriverApiMapper driverApiMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponse createDriver(@RequestBody @Valid DriverRequestBodyDto driverRequestBodyDto) {
        var driverModel = driverApiMapper.toDomain(driverRequestBodyDto);
        var createdDriverModel = createDriverUseCase.createDriver(driverModel);
        return driverApiMapper.toResponse(createdDriverModel);
    }

    @GetMapping("/{idDriver}")
    public DriverResponse getDriver(@PathVariable Long idDriver) {
        var driverModel = getDriverUseCase.getDriver(idDriver);
        return driverApiMapper.toResponse(driverModel);
    }

    @PutMapping("/{idDriver}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverResponse updateDriver(@PathVariable Long idDriver, @RequestBody @Valid DriverRequestBodyDto driverRequestBodyDto) {
        var driverModel = driverApiMapper.toDomain(driverRequestBodyDto);
        var updatedDriverModel = updateDriverUseCase.updateDriver(idDriver, driverModel);
        return driverApiMapper.toResponse(updatedDriverModel);
    }

    @DeleteMapping("/{idDriver}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Driver deleted")
    public void deleteDriver(@PathVariable Long idDriver) {
        deleteDriverUseCase.deleteDriver(idDriver);
    }

    @GetMapping("/check/{idDriver}")
    public DriverCheckResponse checkAllDriverParcels(@PathVariable Long idDriver) {
        return checkAllDriverParcelsUseCase.checkAllDriverParcels(idDriver);
    }

}

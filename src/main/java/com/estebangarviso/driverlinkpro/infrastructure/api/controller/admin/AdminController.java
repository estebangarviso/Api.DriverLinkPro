package com.estebangarviso.driverlinkpro.infrastructure.api.controller.admin;


import com.estebangarviso.driverlinkpro.domain.model.user.UserModel;
import com.estebangarviso.driverlinkpro.domain.model.user.UserSortFields;
import com.estebangarviso.driverlinkpro.infrastructure.admin.admin_provider.AdminProvider;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.admin.AdminSwagger;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController implements AdminSwagger {

    private final AdminProvider adminProvider;
    @GetMapping("/users")
    public Page<UserModel> getAllUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(defaultValue = "ID") UserSortFields sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        PageRequest pageable = PageRequest.of(page, size, sortDirection, sortField.getField());
        return adminProvider.findAll(pageable);
    }
}

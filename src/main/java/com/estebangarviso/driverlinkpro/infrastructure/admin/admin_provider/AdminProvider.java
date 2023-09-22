package com.estebangarviso.driverlinkpro.infrastructure.admin.admin_provider;


import com.estebangarviso.driverlinkpro.domain.model.user.UserModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.admin.AdminMapper;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.AuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class AdminProvider {

        private final AuthenticationRepository authenticationRepository;
        private final AdminMapper adminMapper;

        public Page<UserModel> findAll(Pageable pageable) {
                return authenticationRepository.findAll(pageable)
                        .map(adminMapper::toDomain);
        }
}

package com.estebangarviso.driverlinkpro.infrastructure.authentication.user_provider;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.estebangarviso.driverlinkpro.domain.exception.general.NotFoundException;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.AuthenticationRepository;

@AllArgsConstructor
@Component
public class UserProvider {

    private AuthenticationRepository authenticationRepository;

    public UserDetailsService getUserDetailsService() {

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
                return authenticationRepository.findByEmail(userEmail)
                        .orElseThrow(NotFoundException::userNotFound);
            }
        };
    }
}

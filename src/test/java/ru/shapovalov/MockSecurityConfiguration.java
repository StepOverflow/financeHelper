package ru.shapovalov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.shapovalov.security.CustomGrantedAuthority;
import ru.shapovalov.security.CustomUserDetails;
import ru.shapovalov.security.UserRole;

import static java.util.Collections.singleton;

@Configuration
public class MockSecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {
        return s -> {
            CustomGrantedAuthority grantedAuthority = new CustomGrantedAuthority(UserRole.USER);
            return new CustomUserDetails(
                    1L,
                    "user1@example.com",
                    "pass1",
                    singleton(grantedAuthority)
            );
        };
    }
}
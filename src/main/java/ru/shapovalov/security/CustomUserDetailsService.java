package ru.dzhenenko.secuity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dzhenenko.entity.User;
import ru.dzhenenko.repository.ServiceUserRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ServiceUserRepository serviceUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = serviceUserRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Can't find user with email" + email);
        }

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(CustomGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}

package vn.iotstar.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import vn.iotstar.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Username không tồn tại"
                        )
                );
    }
}
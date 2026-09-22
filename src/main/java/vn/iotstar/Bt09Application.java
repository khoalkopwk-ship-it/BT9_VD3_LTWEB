package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@SpringBootApplication
public class Bt09Application {

    public static void main(String[] args) {
        SpringApplication.run(Bt09Application.class, args);
    }

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            Role userRole =
                    roleRepository.findByName("ROLE_USER")
                            .orElseGet(() ->
                                    roleRepository.save(
                                            Role.builder()
                                                    .name("ROLE_USER")
                                                    .build()
                                    )
                            );

            Role adminRole =
                    roleRepository.findByName("ROLE_ADMIN")
                            .orElseGet(() ->
                                    roleRepository.save(
                                            Role.builder()
                                                    .name("ROLE_ADMIN")
                                                    .build()
                                    )
                            );

            if (!userRepository.existsByUsername("admin")) {

                User admin = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(
                                passwordEncoder.encode("123456")
                        )
                        .fullName("Administrator")
                        .enabled(true)
                        .role(adminRole)
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
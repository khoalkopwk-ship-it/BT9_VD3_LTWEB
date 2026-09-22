package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dto.RegisterDTO;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.AuthService;
import vn.iotstar.service.OtpService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Override
    @Transactional
    public void register(
            RegisterDTO dto
    ) {

        if (userRepository
                .existsByUsername(
                        dto.getUsername()
                )) {

            throw new IllegalArgumentException(
                    "Username đã tồn tại"
            );
        }

        if (userRepository
                .existsByEmail(
                        dto.getEmail()
                )) {

            throw new IllegalArgumentException(
                    "Email đã tồn tại"
            );
        }

        if (!dto.getPassword()
                .equals(
                        dto.getConfirmPassword()
                )) {

            throw new IllegalArgumentException(
                    "Mật khẩu xác nhận không đúng"
            );
        }

        Role role =
                roleRepository
                        .findByName(
                                "ROLE_USER"
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Chưa có ROLE_USER"
                                )
                        );

        User user =
                User.builder()
                        .username(
                                dto.getUsername()
                        )
                        .email(
                                dto.getEmail()
                        )
                        .password(
                                passwordEncoder.encode(
                                        dto.getPassword()
                                )
                        )
                        .fullName(
                                dto.getFullName()
                        )
                        .enabled(false)
                        .role(role)
                        .build();

        userRepository.save(user);

        otpService.sendRegisterOtp(
                dto.getEmail()
        );
    }

    @Override
    @Transactional
    public boolean verifyRegister(
            String email,
            String otp
    ) {

        boolean ok =
                otpService
                        .verifyRegisterOtp(
                                email,
                                otp
                        );

        if (!ok) {
            return false;
        }

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User không tồn tại"
                                )
                        );

        user.setEnabled(true);

        return true;
    }

    @Override
    @Transactional
    public void forgotPassword(
            String email
    ) {

        if (!userRepository
                .existsByEmail(email)) {

            throw new IllegalArgumentException(
                    "Email không tồn tại"
            );
        }

        otpService
                .sendResetPasswordOtp(
                        email
                );
    }

    @Override
    public boolean verifyResetOtp(
            String email,
            String otp
    ) {

        return otpService
                .verifyResetPasswordOtp(
                        email,
                        otp
                );
    }

    @Override
    @Transactional
    public void resetPassword(
            String email,
            String password
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Email không tồn tại"
                                )
                        );

        user.setPassword(
                passwordEncoder.encode(
                        password
                )
        );
    }
}
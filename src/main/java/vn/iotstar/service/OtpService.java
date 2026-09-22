package vn.iotstar.service;

public interface OtpService {

    void sendRegisterOtp(String email);

    boolean verifyRegisterOtp(
            String email,
            String otp
    );

    void sendResetPasswordOtp(
            String email
    );

    boolean verifyResetPasswordOtp(
            String email,
            String otp
    );
}
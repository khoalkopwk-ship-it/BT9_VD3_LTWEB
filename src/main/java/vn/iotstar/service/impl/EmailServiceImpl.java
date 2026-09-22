package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.iotstar.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(
            String email,
            String otp,
            String subject
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(subject);

        message.setText("""
                Xin chào,

                Mã OTP của bạn là: %s

                OTP có hiệu lực trong 5 phút và chỉ sử dụng một lần.

                Không chia sẻ mã này cho người khác.
                """.formatted(otp));

        mailSender.send(message);
    }
}
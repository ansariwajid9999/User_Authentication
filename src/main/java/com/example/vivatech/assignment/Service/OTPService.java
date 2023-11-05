package com.example.vivatech.assignment.Service;

import com.example.vivatech.assignment.Exceptions.OTPException;
import com.example.vivatech.assignment.Exceptions.UserProfileNotFoundException;
import com.example.vivatech.assignment.Models.OTP;
import com.example.vivatech.assignment.Models.User;
import com.example.vivatech.assignment.Repository.OTPRepository;
import com.example.vivatech.assignment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OTPService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    public String sendOTP(String username) throws OTPException, UserProfileNotFoundException {

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UserProfileNotFoundException("Invalid userName");
        }

        User user = optionalUser.get();

        String otp = generateRandomOTP();

        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(20);
        OTP otpEntity = new OTP();
        otpEntity.setUsername(username);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(expiryTime);
        otpRepository.save(otpEntity);

        SimpleMailMessage simpleMessageMail = new SimpleMailMessage();

        String body = "Hi "+user.getUsername()+" \n"+
                "Your otp for Authentication is "+
                otp +" and is valid for 20 minutes only.";

        simpleMessageMail.setSubject("OTP Validation");
        simpleMessageMail.setFrom("testingapi493@gmail.com");
        simpleMessageMail.setText(body);
        simpleMessageMail.setTo(user.getEmail());

        emailSender.send(simpleMessageMail);

        return "OTP Sent successfully";
    }

    private String generateRandomOTP() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public String validateOTP(String username, String otp) throws OTPException {
        Optional<OTP> otpRecord = otpRepository.findByUsernameAndOtp(username, otp);

        if (otpRecord.isPresent()) {
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isAfter(otpRecord.get().getExpiryTime())) {
                throw new OTPException("OTP has expired");
            }
            otpRepository.delete(otpRecord.get());

            return "OTP validated successfully...";
        } else {
            throw new OTPException("Invalid OTP");
        }
    }

}

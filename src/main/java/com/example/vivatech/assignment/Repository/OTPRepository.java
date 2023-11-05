package com.example.vivatech.assignment.Repository;

import com.example.vivatech.assignment.Models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByUsernameAndOtp(String username, String otp);// Doing by JPA functions
}

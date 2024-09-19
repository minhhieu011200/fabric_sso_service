package com.example.sso_service.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPService {
    private static final int OTP_LENGTH = 6;
    private static final int EXPIRE_MINUTES = 5;
    private Map<String, String> otpStorage = new HashMap<>();
    private Random random = new SecureRandom();

    // Generate OTP
    public String generateOtp(String userIdentifier) {
        String otp = String.format("%06d", random.nextInt(999999));
        otpStorage.put(userIdentifier, otp);
        // You can add a mechanism to expire the OTP after a certain time here
        return otp;
    }

    // Verify OTP
    public boolean verifyOtp(String userIdentifier, String inputOtp) {
        String storedOtp = otpStorage.get(userIdentifier);
        if (storedOtp != null && storedOtp.equals(inputOtp)) {
            otpStorage.remove(userIdentifier); // Clear OTP after verification
            return true;
        }
        return false;
    }
}

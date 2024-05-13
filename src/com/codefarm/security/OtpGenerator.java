package com.codefarm.security;

import java.security.SecureRandom;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class OtpGenerator {
    static char[] getOtp(int len) {
        // Using numeric values
        String numbers = "0123456789";

        // Using random method
//        Random random = new Random();
        SecureRandom random = new SecureRandom();
        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

    public static void main(String[] args) {
        System.out.print("You OTP is : ");
        for (char c : getOtp(6)) {
            System.out.print(c);
        }
    }
}

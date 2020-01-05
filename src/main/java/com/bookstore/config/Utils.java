package com.bookstore.config;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Marieta
 *
 */
@Component
public class Utils {
	
	 private static final String SALT = "salt";

	    @Bean
	    public static PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	    }
	    

	    @Bean
	    public static String randomPassword() {
	        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        StringBuilder salt = new StringBuilder();
	        Random random = new Random();
	        while (salt.length() < 18) {
	            int index = (int) (random.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        return salt.toString();
	    }

}

package com.green.webstoreadmin.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordManager {
	public static String getBCrypPassword(String rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode(rawPassword);
		System.out.println("Encoded password : " + encoded);
		return encoded;
	}

}

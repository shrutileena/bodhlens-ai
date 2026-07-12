package com.documind.documind_ai.util;

import java.security.SecureRandom;

import io.jsonwebtoken.io.Encoders;

public class JwtSecretGenerator {
	
	public static void main(String[] args) {
		
		byte[] key = new byte[32];	// 32 bytes = 256 bits
		new SecureRandom().nextBytes(key);
		
		String secret = Encoders.BASE64.encode(key);
		System.out.println("Secret - " + secret);
	}
}

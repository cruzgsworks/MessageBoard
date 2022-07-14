package com.revature.messageboard.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class AuthService {
	private String internalSeed = "REVATURE2022";

	public String doEncrypt(String subject) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(this.internalSeed);
		return encryptor.encrypt(subject);
	}

	public boolean comparePasswords(String input, String storedPass) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(this.internalSeed);
		// System.out.println(encryptor.decrypt(storedPass) + " == " + input);
		if (encryptor.decrypt(storedPass).equals(input)) {
			return true;
		}
		return false;
	}

	public String createAuthToken() {
		String generatedString = RandomStringUtils.randomAlphanumeric(32);
		return generatedString;
	}
}

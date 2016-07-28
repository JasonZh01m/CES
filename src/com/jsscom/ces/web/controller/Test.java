package com.jsscom.ces.web.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
	public static void main(String[] args) {
	}

	public String genHashPassword(String loginId, String password) {
		String algorithm = "MD5";
		try {
			MessageDigest mdAlgorithm = MessageDigest.getInstance(algorithm);

			mdAlgorithm.update(loginId.getBytes());
			// Modified By JasonZh 0728
			// mdAlgorithm.update(45);
			mdAlgorithm.update(password.getBytes());

			byte[] digest = mdAlgorithm.digest();
			StringBuffer hexString = new StringBuffer();

			for (byte aDigest : digest) {
				String plainText = Integer.toHexString(0xFF & aDigest);

				if (plainText.length() < 2) {
					plainText = "0" + plainText;
				}

				hexString.append(plainText);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		throw new RuntimeException("algorithm " + algorithm
				+ " not supported when hashing unhashedPassword");
	}
}

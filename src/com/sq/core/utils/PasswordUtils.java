 package com.sq.core.utils;
 
 import java.io.PrintStream;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.security.NoSuchProviderException;
 import java.security.SecureRandom;
 
 public class PasswordUtils
 {
   private static final String SHA_256 = "SHA-256";
 
   public static void main(String[] args)
     throws NoSuchAlgorithmException, NoSuchProviderException
   {
     String passw0rd = "123456";
     String saltCode = getSalt();
     System.out.println("盐码:" + saltCode);
     String securePassword = getShaSecurePassword(passw0rd, saltCode);
     System.out.println("加密后的密码:" + securePassword);
   }
 
   public static String getSalt()
     throws NoSuchAlgorithmException, NoSuchProviderException
   {
     SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
 
     byte[] salt = new byte[16];
 
     sr.nextBytes(salt);
     return bytesToHexString(salt);
   }
 
   public static String getShaSecurePassword(String password, String salt)
   {
     String generatedPassword = null;
     try {
       MessageDigest md = MessageDigest.getInstance(SHA_256);
       md.update(salt.getBytes());
       byte[] bytes = md.digest(password.getBytes());
       StringBuilder sb = new StringBuilder(64);
       for (int i = 0; i < bytes.length; i++)
       {
         sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1));
       }
       generatedPassword = sb.toString();
     }
     catch (NoSuchAlgorithmException e)
     {
       e.printStackTrace();
     }
     return generatedPassword;
   }
 
   private static String bytesToHexString(byte[] src) {
     StringBuilder stringBuilder = new StringBuilder(32);
     if ((src == null) || (src.length <= 0)) {
       return null;
     }
     for (int i = 0; i < src.length; i++) {
       int v = src[i] & 0xFF;
       String hv = Integer.toHexString(v);
       if (hv.length() < 2) {
         stringBuilder.append(0);
       }
       stringBuilder.append(hv);
     }
     return stringBuilder.toString();
   }
 }


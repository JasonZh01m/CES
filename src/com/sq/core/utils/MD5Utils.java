 package com.sq.core.utils;
 
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 
 public class MD5Utils
 {
   private static final String algorithm = "MD5";
 
   public static String getMD5Code(String args, String key) throws UnsupportedEncodingException
   {
     StringBuilder hexString = new StringBuilder(32);
     try
     {
       MessageDigest mdAlgorithm = MessageDigest.getInstance(algorithm);
       mdAlgorithm.update(args.getBytes("UTF-8".intern()));
       mdAlgorithm.update(key.getBytes());
 
       byte[] digest = mdAlgorithm.digest();
 
       for (int i = 0; i < digest.length; i++) {
         String plainText = Integer.toHexString(0xFF & digest[i]);
         if (plainText.length() < 2) {
           plainText = "0".intern() + plainText;
         }
         hexString.append(plainText);
       }
     } catch (NoSuchAlgorithmException e) {
       throw new RuntimeException("algorithm " + algorithm + 
         " not supported when hashing unhashedPassword");
     }
     return hexString.toString();
   }
 
   public static void main(String[] args)
     throws UnsupportedEncodingException
   {
     String dd = "中文";
     byte[] b1 = dd.getBytes();
     byte[] b2 = dd.getBytes("UTF-8");
 
     for (int i = 0; i < b1.length; i++) {
       System.out.print(b1[i] + ",");
     }
 
     for (int i = 0; i < b2.length; i++)
       System.out.print(b2[i] + ",");
   }
 }


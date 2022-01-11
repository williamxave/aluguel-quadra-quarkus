package br.com.william.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypt {
    public static String encryptPassword(String password)  {
        MessageDigest algorithm;
        StringBuilder encryptPassword = null;
        try {
            algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigestSenhaAdmin[] =
                    algorithm.digest(password.getBytes(StandardCharsets.UTF_8));
             encryptPassword = new StringBuilder();
            for (byte b : messageDigestSenhaAdmin) {
                encryptPassword.append(String.format("%02X", 0xFF & b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        }
        return encryptPassword.toString();
    }
}

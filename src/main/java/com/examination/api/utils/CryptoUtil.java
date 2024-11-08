package com.examination.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CryptoUtil {

    private static final String ALGORITHM = "AES";
    private static String secretKey;
    private static String iv;

    @Value("${spring.secret-key}")
    public void setSecretKey(String secretKey) {
        CryptoUtil.secretKey = secretKey;
        AES256Util();
    }

    private static void AES256Util() {
        CryptoUtil.iv = secretKey.substring(0, 16);
    }

    public static String encrypt(String data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decrypted);
    }
}

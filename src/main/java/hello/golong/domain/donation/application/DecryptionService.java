package hello.golong.domain.donation.application;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class DecryptionService {

    //AES 알고리즘, CBC모드, PKCS5패딩 사용
    public static String algCBC = "AES/CBC/PKCS5Padding";//사용할 알고리즘 정보
    private String iv = "0123456789abcdef";//초기화 벡터, 16Byte == 128bit

    public static String algECB = "AES/ECB/PKCS5Padding";//사용할 알고리즘 정보



    public String decryptCBC(String cipherText, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(algCBC);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");

    }

    public String decrypt(String cipherText, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(algECB);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);//String -> Base64
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }

    public String encrypt(String plainText, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(algECB);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        byte[] encrypted = Base64.getEncoder().encode(encryptedBytes);
        return new String(encrypted, "UTF-8");
    }

    public boolean isCorrectKey(String decryptedText, String plainText) {
        if(decryptedText.equalsIgnoreCase(plainText)) return true;
        else return false;
    }



    /*

	    String alg = "AES/ECB/PKCS5Padding";//사용할 알고리즘 정보
	    String key = "aeskey1234567898";//16byte == 128bit
	    String plainText = "AesPlainText";//12byte

	    System.out.println("plainText = " + plainText);

	    Cipher cipher = Cipher.getInstance(alg);
	    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

	    byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
	    String cipherText = Base64.getEncoder().encodeToString(encrypted);//Base64 -> String

	    System.out.println("cipherText = " + cipherText + "END");//RrhbW1Fy0dA4Tf+XO3J34Q== 24byte


        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);//String -> Base64
        byte[] decrypted = cipher.doFinal(decodedBytes);
        String textDecryped = new String(decrypted, "UTF-8");

        System.out.println("textDecryped = " + textDecryped);
    *
    *
    *
    *
    * */
}

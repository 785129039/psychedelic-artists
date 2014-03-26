package com.nex.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	
	private long created;
	
	private final static String HEX = "0123456789ABCDEF";
	SecretKeySpec key;
	Cipher aes;
	
	private String toHex(byte[] stringBytes) {
        StringBuffer result = new StringBuffer(2*stringBytes.length);

        for (int i = 0; i < stringBytes.length; i++) {
            result.append(HEX.charAt((stringBytes[i]>>4)&0x0f)).append(HEX.charAt(stringBytes[i]&0x0f));
        }

        return result.toString();
    }
	
	private byte[] toByte(String hexString) {
        int len = hexString.length()/2;

        byte[] result = new byte[len];

        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }
	public Encryption() throws Exception {
		this("3ncryppt1ionp655w0rd");
	}
	public Encryption(String password) throws Exception {
		this.created = System.currentTimeMillis();
		String passphrase = password;
		byte[] salt = "1234".getBytes();
		int iterations = 10000;
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey tmp = factory.generateSecret(new PBEKeySpec(passphrase.toCharArray(), salt, iterations, 128));
		key = new SecretKeySpec(tmp.getEncoded(), "AES");
		aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	}
	
	public String cipher(String text) throws Exception {
		aes.init(Cipher.ENCRYPT_MODE, key);
		byte[] ciphertext = aes.doFinal(text.getBytes());
		return toHex(ciphertext);
	}
	
	public String decipher(String code) throws Exception {
		aes.init(Cipher.DECRYPT_MODE, key);
		byte[] bytes = toByte(code);
		return new String(aes.doFinal(bytes));
	}
	public long getCreated() {
		return created;
	}
}

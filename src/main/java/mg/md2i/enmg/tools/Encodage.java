package mg.md2i.enmg.tools;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Encodage {
    
    // Derived 8-byte XOR key based on '1' -> {-21,54,25,-91,-44,-16,41,1}
    private static final byte[] XOR_KEY = new byte[]{(byte)-38, 54, 25, (byte)-91, (byte)-44, (byte)-16, 41, 1};

    // Simple encryption/decryption for existing password format
    public byte[] encrypt(String plainText) throws UnsupportedEncodingException {
        if (plainText == null || plainText.isEmpty()) {
            return new byte[8]; // Return fixed-size empty array
        }
        
        byte[] bytes = plainText.getBytes("UTF-8"); // Specify UTF-8
        byte[] paddedBytes = Arrays.copyOf(bytes, 8); // Pad to 8 bytes with zeros
        byte[] encrypted = new byte[8];
        
        // XOR encryption with fixed 8-byte key
        for (int i = 0; i < 8; i++) {
            encrypted[i] = (byte) (paddedBytes[i] ^ XOR_KEY[i]);
        }
        
        return encrypted;
    }
    
    public String decrypt(byte[] encryptedBytes) throws UnsupportedEncodingException {
        if (encryptedBytes == null || encryptedBytes.length != 8) {
            return ""; // Expect 8 bytes for decryption
        }
        
        byte[] decrypted = new byte[8];
        
        // XOR decryption with fixed 8-byte key
        for (int i = 0; i < 8; i++) {
            decrypted[i] = (byte) (encryptedBytes[i] ^ XOR_KEY[i]);
        }
        
        // Trim null bytes (padding) and convert to string
        int actualLength = 0;
        for (int i = 0; i < decrypted.length; i++) {
            if (decrypted[i] == 0) {
                break;
            }
            actualLength++;
        }
        return new String(decrypted, 0, actualLength, "UTF-8");
    }
    
    // Helper methods to convert between byte array and string representation
    public String byteToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(bytes[i]);
        }
        return sb.toString();
    }
    
    public byte[] stringToByte(String str) {
        if (str == null || str.isEmpty()) {
            return new byte[0];
        }
        
        String[] parts = str.split(",");
        byte[] bytes = new byte[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            bytes[i] = Byte.parseByte(parts[i].trim());
        }
        
        return bytes;
    }
}

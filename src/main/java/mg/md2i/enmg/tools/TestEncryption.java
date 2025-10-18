package mg.md2i.enmg.tools;

import java.io.UnsupportedEncodingException;

public class TestEncryption {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Encodage enc = new Encodage();
        
        // Test with the stored password from database
        String storedPassword = "-21,54,25,-91,-44,-16,41,1";
        byte[] storedBytes = enc.stringToByte(storedPassword);
        String decryptedPassword = enc.decrypt(storedBytes);
        
        System.out.println("Stored password: " + storedPassword);
        System.out.println("Decrypted password: '" + decryptedPassword + "'");
        
        // Test if "1" encrypts to the same thing
        String testPassword = "1";
        byte[] testEncrypted = enc.encrypt(testPassword);
        String testEncoded = enc.byteToString(testEncrypted);
        
        System.out.println("Test password '1' encrypts to: " + testEncoded);
        System.out.println("Matches stored? " + testEncoded.equals(storedPassword));
    }
}

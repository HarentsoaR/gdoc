package mg.md2i.enmg.tools;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class EncodageTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Encodage enc = new Encodage();

        String rawPassword = "1"; // The known raw password
        String storedEncodedPassword = "-21,54,25,-91,-44,-16,41,1"; // The exact string from your DB

        System.out.println("--- Encodage Test ---");
        System.out.println("Raw Input Password: \"" + rawPassword + "\"");
        System.out.println("Stored Encoded Password: \"" + storedEncodedPassword + "\"");

        // 1. Encrypt the raw input password using our Encodage class
        byte[] encryptedBytes = enc.encrypt(rawPassword);
        String encryptedString = enc.byteToString(encryptedBytes);

        System.out.println("\nOur Encrypted String (from raw input): \"" + encryptedString + "\"");
        System.out.println("Matches Stored Encoded Password? " + encryptedString.equals(storedEncodedPassword));

        // 2. Decrypt the stored encoded password using our Encodage class
        byte[] storedBytes = enc.stringToByte(storedEncodedPassword);
        String decryptedStoredPassword = enc.decrypt(storedBytes);

        System.out.println("\nDecrypted Stored Password (using our decoder): \"" + decryptedStoredPassword + "\"");
        System.out.println("Matches Raw Input Password? " + decryptedStoredPassword.equals(rawPassword));

        // 3. Verify the individual bytes
        System.out.println("\nComparing byte arrays:");
        System.out.println("  Raw input bytes (UTF-8):   " + Arrays.toString(rawPassword.getBytes("UTF-8")));
        System.out.println("  Our encrypted bytes:       " + Arrays.toString(encryptedBytes));
        System.out.println("  Stored encoded bytes:      " + Arrays.toString(storedBytes));
    }
}

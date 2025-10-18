package mg.md2i.enmg.tools;

public class PasswordAnalyzer {
    public static void main(String[] args) {
        // Known password: "1" -> "-21,54,25,-91,-44,-16,41,1"
        String knownPassword = "1";
        String knownEncrypted = "-21,54,25,-91,-44,-16,41,1";
        
        System.out.println("Analyzing password encryption...");
        System.out.println("Known: password='" + knownPassword + "' -> encrypted='" + knownEncrypted + "'");
        
        // Convert encrypted string to bytes
        String[] parts = knownEncrypted.split(",");
        byte[] encryptedBytes = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            encryptedBytes[i] = Byte.parseByte(parts[i].trim());
        }
        
        System.out.println("Encrypted bytes: " + java.util.Arrays.toString(encryptedBytes));
        
        // Try to reverse engineer the algorithm
        byte[] passwordBytes = knownPassword.getBytes();
        System.out.println("Password bytes: " + java.util.Arrays.toString(passwordBytes));
        
        // Try different algorithms
        System.out.println("\nTrying different algorithms:");
        
        // Algorithm 1: Simple XOR
        System.out.println("Algorithm 1 - Simple XOR:");
        for (int key = 0; key < 256; key++) {
            boolean matches = true;
            for (int i = 0; i < passwordBytes.length && i < encryptedBytes.length; i++) {
                if ((passwordBytes[i] ^ key) != encryptedBytes[i]) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                System.out.println("  Key " + key + " works!");
                break;
            }
        }
        
        // Algorithm 2: Position-based XOR
        System.out.println("Algorithm 2 - Position-based XOR:");
        for (int i = 0; i < passwordBytes.length && i < encryptedBytes.length; i++) {
            System.out.println("  Position " + i + ": " + passwordBytes[i] + " ^ " + encryptedBytes[i] + " = " + (passwordBytes[i] ^ encryptedBytes[i]));
        }
        
        // Algorithm 3: Try to find pattern
        System.out.println("Algorithm 3 - Pattern analysis:");
        for (int i = 0; i < passwordBytes.length && i < encryptedBytes.length; i++) {
            int diff = encryptedBytes[i] - passwordBytes[i];
            System.out.println("  Position " + i + ": " + passwordBytes[i] + " + " + diff + " = " + encryptedBytes[i]);
        }
    }
}

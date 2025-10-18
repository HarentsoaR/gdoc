package mg.md2i.enmg.tools;

import java.util.Arrays;

public class MainEncodage {
    public static void main(String[] args) throws Exception {
        Encodage enc = new Encodage();

        // 1) Vérifier que "test" -> ton tableau signé
        String plain = "test";
        byte[] cipher = enc.encrypt(plain);
        System.out.println("test -> bytes signés : " + Arrays.toString(cipher));
        System.out.println("test -> hex          : " + toHex(cipher));

        // 2) Reprendre TA valeur chiffrée lue à l'écran pour "test"
        byte[] fromLoginTest = new byte[]{-65,93,-109,109,108,52,-38,-16};
        String dec1 = enc.decrypt(fromLoginTest);
        System.out.println("decrypt([-65,93,-109,109,108,52,-38,-16]) = " + dec1);

        // 3) Déchiffrer l'utilisateur existant de la BDD
        byte[] userStored = new byte[]{-21,54,25,-91,-44,-16,41,1};
        String dec2 = enc.decrypt(userStored);
        System.out.println("decrypt([-21,54,25,-91,-44,-16,41,1]) = " + dec2);

        // 4) Démonstration des helpers de la classe
        String storedString = enc.byteToString(cipher); // ce que vous stockez en BDD
        System.out.println("byteToString(cipher) = " + storedString);
        System.out.println("decrypt(stringToByte(...)) = " + enc.decrypt(enc.stringToByte(storedString)));
        
        // 5) Test avec l'utilisateur de la BDD
        String storedPassword = "-21,54,25,-91,-44,-16,41,1";
        String decryptedPassword = enc.decrypt(enc.stringToByte(storedPassword));
        System.out.println("Password for user '1': " + decryptedPassword);
    }

    private static String toHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02X", x));
        return sb.toString();
    }
}

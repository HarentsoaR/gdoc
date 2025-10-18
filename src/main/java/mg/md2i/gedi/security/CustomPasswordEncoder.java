package mg.md2i.gedi.security;

import mg.md2i.enmg.tools.Encodage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    private static final Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);
    private final Encodage encodage = new Encodage();
    
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            byte[] encrypted = encodage.encrypt(rawPassword.toString());
            return encodage.byteToString(encrypted);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }
    
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        logger.debug("Attempting to match password. Raw password length: {}, Encoded password: '{}'", rawPassword.length(), encodedPassword);
        try {
            // Encrypt the raw password from the user's input
            String encryptedRawPasswordString = encodage.byteToString(encodage.encrypt(rawPassword.toString()));

            // Compare the newly encrypted raw password with the encoded password from the database
            boolean isMatch = encryptedRawPasswordString.equals(encodedPassword);

            if (isMatch) {
                logger.info("Password match successful for encoded password '{}'", encodedPassword);
            } else {
                logger.warn("Password match failed. Encoded input: '{}', Stored: '{}'", encryptedRawPasswordString, encodedPassword);
            }

            return isMatch;
        } catch (UnsupportedEncodingException e) {
            logger.error("Error encoding password during match check.", e);
            return false;
        }
    }
}
package extclasses.final_project_spring.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    PasswordEncoder bcrypt = new BCryptPasswordEncoder(8);

    @Override
    public String encode(CharSequence charSequence) {
        String sha1 = toSHA1(charSequence.toString().getBytes(StandardCharsets.UTF_8));

        return bcrypt.encode(sha1);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return bcrypt.matches(toSHA1(charSequence.toString().getBytes(StandardCharsets.UTF_8)), s);
    }

    public static String toSHA1(byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(md.digest(convertme));
    }
}

package extclasses.final_project_spring.config;

import extclasses.final_project_spring.util.AesGcmEncryption;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Base64;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

    private final byte[] KEY = System.getenv("secret.key").getBytes();
    AesGcmEncryption encryption = new AesGcmEncryption();

    @Override
    public String convertToDatabaseColumn(String ccNumber) {
        return Base64.getEncoder().encodeToString(encryption.encrypt(KEY, ccNumber.getBytes(), null));
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return new String(encryption.decrypt(KEY, Base64.getDecoder().decode(dbData.getBytes()), null));
    }
}

package extclasses.final_project_spring.util;

import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.exception.IncorrectDataException;

import java.util.regex.Pattern;

public class Validator {
    public static void checkRegistration(UserDTO userDTO) {
        if (Pattern.matches(ValidationRegex.usernameRegex, userDTO.getUsername())
                && Pattern.matches(ValidationRegex.passwordRegex, userDTO.getPassword())
                && Pattern.matches(ValidationRegex.phoneRegex, userDTO.getPhone())
                && Pattern.matches(ValidationRegex.emailRegex, userDTO.getEmail())) {
            return;
        }
        throw new IncorrectDataException("bad credentials");
    }
}